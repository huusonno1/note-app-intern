package internsafegate.noteapp.controller.auth;

import internsafegate.noteapp.dto.request.auth.AuthDTO;
import internsafegate.noteapp.dto.request.auth.GoogleDTO;
import internsafegate.noteapp.dto.request.auth.LoginDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.auth.AuthResponse;
import internsafegate.noteapp.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ResponseObject> register(
            @RequestBody AuthDTO authDTO
    ) throws Exception {
        // check cac json co dung trg du lieu khong

        if(authDTO.getEmail() == null || authDTO.getEmail().trim().isBlank()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message("email is required")
                    .build());
        }


        AuthResponse registerResponse = authService.register(authDTO);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(registerResponse)
                .message("Account registration successful")
                .build());
    }

    @GetMapping("confirm-account")
    public ResponseEntity<String> confirmUserAccount(
            @RequestParam("token")String confirmationToken
    ) throws Exception {

        boolean isTokenValid = authService.confirmEmail(confirmationToken);

        if (isTokenValid) {
            // Trả về giao diện thành công
            return ResponseEntity.ok(getSuccessPage());
        } else {
            // Trả về giao diện thất bại
            return ResponseEntity.ok(getFailurePage());
        }

    }
    @PostMapping("login")
    public ResponseEntity<ResponseObject> login(
            @RequestBody LoginDTO loginDTO
    ) throws Exception {

        if(loginDTO.getEmail() == null || loginDTO.getEmail().trim().isBlank()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message("email is required")
                    .build());
        }

        AuthResponse loginResponse = authService.login(loginDTO);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(loginResponse)
                .message("Login successfully")
                .build());
    }

    @PostMapping("google")
    public ResponseEntity<ResponseObject> googleLogin(
            @RequestBody GoogleDTO googleDTO
            ) throws Exception {

        if(googleDTO.getGoogleToken() == null || googleDTO.getGoogleToken().trim().isBlank()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message("username is required")
                    .build());
        }

        AuthResponse loginResponse = authService.authenticateGoogleUser(googleDTO.getGoogleToken());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(loginResponse)
                .message("Login successfully")
                .build());
    }

    private String getSuccessPage() {
        return "<html>" +
                "<body>" +
                "<div style='text-align: center; padding: 50px;'>" +
                "<h2>Account Confirmed!</h2>" +
                "<p>Your account has been successfully confirmed. Thank you for registering with us!</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    private String getFailurePage() {
        return "<html>" +
                "<body>" +
                "<div style='text-align: center; padding: 50px;'>" +
                "<h2>Confirmation Failed</h2>" +
                "<p>There was a problem confirming your account. Please try again later or contact support.</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

}
