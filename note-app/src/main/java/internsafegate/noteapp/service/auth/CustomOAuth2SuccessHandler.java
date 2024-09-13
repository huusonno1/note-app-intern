package internsafegate.noteapp.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.auth.AuthResponse;
import internsafegate.noteapp.model.Role;
import internsafegate.noteapp.model.Token;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.RoleRepository;
import internsafegate.noteapp.repository.TokenRepository;
import internsafegate.noteapp.repository.UserRepository;
import internsafegate.noteapp.security.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final TokenRepository tokenRepo;
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null || name == null ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user information");
            return;
        }

        Optional<Users> userOptional = userRepo.findByEmail(email);
        Users user = userOptional.orElseGet(() -> registerNewUser(email, name));

        // Tạo JWT cho người dùng
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        AuthResponse authResponse = AuthResponse.builder()
                .message("Login Successfully")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType("BEARER")
                .username(user.getUsername())
                .id(user.getId())
                .build();

        // Tạo AuthResponse
        ResponseObject object = ResponseObject.builder()
                .message("Login successfully")
                .status(HttpStatus.OK)
                .data(authResponse)
                .build();
        System.out.println("login by google success");

        // Thiết lập phản hồi là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(object));
    }

    private Users registerNewUser(String email, String name) {
        Users newUser = Users.builder()
                .email(email)
                .username(name)
                .active(true)
                .role(roleRepository.findRoleById(1L))
                .build();
        newUser.setEnabled(true);

        return userRepo.save(newUser);
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
