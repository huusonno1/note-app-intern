package internsafegate.noteapp.controller.user;

import internsafegate.noteapp.dto.request.user.UserDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.user.UserResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
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
    private final SecurityUtils securityUtils;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getUserProfile(
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();
        UserResponse userProfile = userService.getUserProfile(loggedInUser.getId());
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userProfile)
                .message("get user profile successful")
                .build());
    }



    @PutMapping("")
    public ResponseEntity<ResponseObject> updateUserProfile(
            @RequestBody UserDTO userDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        Users user = userService.getUserDetailsFromToken(extractedToken);
        Users loggedInUser= securityUtils.getLoggedInUser();

        if (user.getId() != loggedInUser.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UserResponse userProfile = userService.updateUserProfile(loggedInUser.getId(), userDTO);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userProfile)
                .message("update user profile successful")
                .build());
    }

}
