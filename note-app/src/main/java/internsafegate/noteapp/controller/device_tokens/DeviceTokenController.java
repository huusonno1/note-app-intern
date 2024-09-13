package internsafegate.noteapp.controller.device_tokens;

import internsafegate.noteapp.dto.request.auth.AuthDTO;
import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.auth.AuthResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.device_tokens.DeviceTokenService;
import internsafegate.noteapp.service.fcm.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/fcm")
public class DeviceTokenController {
    private final DeviceTokenService deviceTokenService;

    private final SecurityUtils securityUtils;

    @PostMapping("/save-device-token")
    public ResponseEntity<ResponseObject> saveDeviceToken(
            @RequestBody DeviceTokenDTO deviceTokenDTO
    ) throws Exception {
        // check cac json co dung trg du lieu khong

        Users loggedInUser= securityUtils.getLoggedInUser();

        deviceTokenService.saveDeviceToken(deviceTokenDTO, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(null)
                .message("save device token fcm successfully")
                .build());
    }
}
