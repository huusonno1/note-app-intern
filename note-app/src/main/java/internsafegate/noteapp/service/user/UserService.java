package internsafegate.noteapp.service.user;

import internsafegate.noteapp.dto.request.user.UserDTO;
import internsafegate.noteapp.dto.response.user.UserResponse;
import internsafegate.noteapp.model.Users;

public interface UserService {
    UserResponse getUserProfile(Long id) throws Exception;

    Users getUserDetailsFromToken(String extractedToken) throws Exception;

    UserResponse updateUserProfile(Long userId, UserDTO userDTO) throws Exception;
}
