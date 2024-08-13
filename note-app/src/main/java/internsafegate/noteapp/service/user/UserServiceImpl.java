package internsafegate.noteapp.service.user;

import internsafegate.noteapp.dto.request.user.UserDTO;
import internsafegate.noteapp.dto.response.user.UserResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.UserRepository;
import internsafegate.noteapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final JwtService jwtService;

    @Override
    public UserResponse getUserProfile(Long id) throws Exception {
        Optional<Users> users = userRepo.findById(id);

        if(users.isEmpty()){
            throw new DataNotFoundException(String.format("not found profile of user %d", id));
        }

        Users userProfile = users.get();

        UserResponse userProfileResponse = UserResponse.builder()
                .id(userProfile.getId())
                .username(userProfile.getUsername())
                .email(userProfile.getEmail())
                .googleAcountId(userProfile.getGoogleAccountId())
                .facebookAccountId(userProfile.getFacebookAccountId())
                .dateOfBirth(userProfile.getDateOfBirth())
                .isActive(userProfile.isActive())
                .build();
        return userProfileResponse;
    }

    @Override
    public Users getUserDetailsFromToken(String token) throws Exception{
        if(jwtService.isTokenExpired(token)) {
            throw new DataNotFoundException("Token is expired");
        }
        String username = jwtService.extractUsername(token);
        Optional<Users> user;
        user = userRepo.findByUsername(username);
        return user.orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public UserResponse updateUserProfile(Long userId, UserDTO userDTO) throws Exception{
        Users existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (userDTO.getUsername() != null) {
            existingUser.setUsername(userDTO.getUsername());
        }

        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(userDTO.getDateOfBirth());
        }
        if (userDTO.getFacebookAccountId() >= 0) {
            existingUser.setFacebookAccountId(userDTO.getFacebookAccountId());
        }
        if (userDTO.getGoogleAccountId() >= 0) {
            existingUser.setGoogleAccountId(userDTO.getGoogleAccountId());
        }

        Users updatedUser = userRepo.save(existingUser);

        UserResponse updatedUserResponse = UserResponse.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .googleAcountId(updatedUser.getGoogleAccountId())
                .facebookAccountId(updatedUser.getFacebookAccountId())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .isActive(updatedUser.isActive())
                .build();
        return updatedUserResponse;
    }
}