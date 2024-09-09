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

        if(authDTO.getUsername() == null || authDTO.getUsername().trim().isBlank()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message("username is required")
                    .build());
        }

        if(!authDTO.getPassword().equals(authDTO.getRetypePassword())) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message("password is not match")
                    .build());
        }

        AuthResponse registerResponse = authService.register(authDTO);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(registerResponse)
                .message("Account registration successful")
                .build());
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<ResponseObject> confirmUserAccount(
            @RequestParam("token")String confirmationToken
    ) throws Exception {

        String message = authService.confirmEmail(confirmationToken);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(null)
                .message(message)
                .build());
    }
    @PostMapping("login")
    public ResponseEntity<ResponseObject> login(
            @RequestBody LoginDTO loginDTO
    ) throws Exception {

        if(loginDTO.getUsername() == null || loginDTO.getUsername().trim().isBlank()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message("username is required")
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

}
