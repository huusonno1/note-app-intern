package internsafegate.noteapp.service.auth;

import internsafegate.noteapp.dto.request.auth.AuthDTO;
import internsafegate.noteapp.dto.request.auth.LoginDTO;
import internsafegate.noteapp.dto.request.auth.LogoutDTO;
import internsafegate.noteapp.dto.response.auth.AuthResponse;
import internsafegate.noteapp.dto.response.auth.GoogleUser;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.exception.UsernameAlreadyExistsException;
import internsafegate.noteapp.model.*;
import internsafegate.noteapp.repository.*;
import internsafegate.noteapp.security.JwtService;
import internsafegate.noteapp.service.email.EmailService;
import internsafegate.noteapp.service.role.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;
    private final DeviceTokenRepository deviceTokenRepo;
    private final ConfirmationTokenRepository confirmationTokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;



// ần xác thuc email
    @Override
    public AuthResponse register(AuthDTO authDTO) throws Exception{
        if(!authDTO.getUsername().isBlank() && userRepo.findByUsername(authDTO.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        if(!authDTO.getEmail().isBlank() && userRepo.findByEmail(authDTO.getEmail()).isPresent()){
            throw new UsernameAlreadyExistsException("email already exists");
        }


        Role userRole = roleService.getUserRole();

        Users newUser = Users.builder()
                .username(authDTO.getUsername())
                .password(passwordEncoder.encode(authDTO.getPassword()))
                .email(authDTO.getEmail())
                .dateOfBirth(authDTO.getDateOfBirth())
                .role(userRole)
                .active(true)
                .build();

        newUser.setEnabled(false);

        Users savedUser = userRepo.save(newUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .confirmationToken(token)
                .user(savedUser)
                .build();

        String toEmail = savedUser.getEmail();
        String subject = "Complete Your Registration!";

        // Nội dung email với HTML
        String body = "<html>" +
                "<body>" +
                "<h2>Welcome to Our Service!</h2>" +
                "<p>To confirm your account, please click the link below:</p>" +
                "<a href='http://localhost:8083/api/note-app/v1/auth/confirm-account?token=" + confirmationToken.getConfirmationToken() + "'>Confirm Account</a>" +
                "<br><br>" +
                "<p>Thank you for registering with us!</p>" +
                "</body>" +
                "</html>";

        emailService.sendEmail(toEmail, subject, body);

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        confirmationTokenRepo.save(confirmationToken);


        return AuthResponse.builder()
                .message("User registered successfully. Please check your email to activate your account.")
                .build();
    }

    @Override
    public AuthResponse login(LoginDTO loginDTO) throws Exception {
        Optional<Users> optionalUser = Optional.empty();
        if(loginDTO.getEmail() != null) {
            optionalUser = userRepo.findByEmail(loginDTO.getEmail());
        }

        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("don't find user");
        }

        Users userDetail = optionalUser.get();

        if(!userDetail.isActive()) {
            throw new DataNotFoundException("user is locked");
        }

        if(!passwordEncoder.matches(loginDTO.getPassword() ,userDetail.getPassword())) {
            throw new DataNotFoundException("user wrong password");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword(),
                        userDetail.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        String jwtToken = jwtService.generateToken(userDetail);
        String refreshToken = jwtService.generateRefreshToken(userDetail);
        revokeAllUserTokens(userDetail);
        saveUserToken(userDetail, jwtToken);

        return AuthResponse.builder()
                .message("Login Successfully")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType("BEARER")
                .username(userDetail.getUsername())
                .id(userDetail.getId())
                .build();
//        .username(userDetail.getUsername())
//        .id(userDetail.getId())
    }

    @Override
    public AuthResponse authenticateGoogleUser(String googleToken) throws Exception {
        // Xác thực Access Token với Google
        GoogleUser googleUser = verifyAccessTokenWithGoogle(googleToken);

        if(!clientId.equals(googleUser.getClientId())){
            throw new DataNotFoundException("Account google not login in note-app");
        }

        // Kiểm tra hoặc lưu người dùng trong DB

        Optional<Users> userOptional = userRepo.findByEmail(googleUser.getEmail());
        Users user;
        if (userOptional.isEmpty()) {
            user = Users.builder()
                    .email(googleUser.getEmail())
                    .username(googleUser.getName())
                    .active(true)
                    .build();

            // Lưu người dùng vào DB
            userRepo.save(user);
        } else {
            user = userOptional.get();
        }

        // Tạo JWT và trả về
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .message("Login Successfully")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType("BEARER")
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }

    @Override
    public Boolean confirmEmail(String confirmationToken) throws Exception{
        ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            Optional<Users> user = userRepo.findByEmail(token.getUser().getEmail());
            if(user.isEmpty()){
                throw new Exception("User not found");
            }
            Users users = user.get();
            users.setEnabled(true);
            userRepo.save(users);
            return true;
        }
        return false;
    }

    @Override
    public void logoutCustom(HttpServletRequest request, HttpServletResponse response, LogoutDTO logoutDTO) throws Exception {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new Exception("Missing or invalid Authorization header");
        }

        jwt = authHeader.substring(7);
        Token storedToken = tokenRepo.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepo.save(storedToken);
            SecurityContextHolder.clearContext();
        }

        DeviceTokens deviceTokens = deviceTokenRepo.findByUserIdAndDeviceId(storedToken.getUser().getId(), logoutDTO.getDeviceId())
                .orElseThrow(()-> new DataNotFoundException("not found device Id and user id"));
        deviceTokens.setStatusToken(false);
        deviceTokenRepo.save(deviceTokens);
    }

    // check token cua app
    private GoogleUser verifyAccessTokenWithGoogle(String googleToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + googleToken;
        return restTemplate.getForObject(url, GoogleUser.class);
    }


    private void revokeAllUserTokens(Users user) {
        List<Token> validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    private void saveUserToken(Users users, String jwtToken) {
        Token token = Token.builder()
                .user(users)
                .token(jwtToken)
                .tokenType("BEARER")
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }
}
