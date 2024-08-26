package internsafegate.noteapp.controller.user;

import internsafegate.noteapp.dto.request.user.UserDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.user.UserResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserProfile(
            @PathVariable Long id
    ) throws Exception {
        UserResponse userProfile = userService.getUserProfile(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userProfile)
                .message("get user profile successful")
                .build());
    }

    @GetMapping("")

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseObject> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserDTO userDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        Users user = userService.getUserDetailsFromToken(extractedToken);

        if (user.getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UserResponse userProfile = userService.updateUserProfile(userId, userDTO);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userProfile)
                .message("update user profile successful")
                .build());
    }

}
