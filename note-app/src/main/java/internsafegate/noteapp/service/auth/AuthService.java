package internsafegate.noteapp.service.auth;

import internsafegate.noteapp.dto.request.auth.AuthDTO;
import internsafegate.noteapp.dto.request.auth.LoginDTO;
import internsafegate.noteapp.dto.request.auth.LogoutDTO;
import internsafegate.noteapp.dto.response.auth.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponse register(AuthDTO authDTO) throws Exception;

    AuthResponse login(LoginDTO loginDTO) throws Exception;

    AuthResponse authenticateGoogleUser(String googleToken) throws Exception;

    Boolean confirmEmail(String confirmationToken) throws Exception;

    void logoutCustom(HttpServletRequest request, HttpServletResponse response, LogoutDTO logoutDTO) throws Exception;
}
