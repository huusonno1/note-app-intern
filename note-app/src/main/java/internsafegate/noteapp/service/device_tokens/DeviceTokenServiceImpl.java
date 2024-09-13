package internsafegate.noteapp.service.device_tokens;

import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.model.DeviceTokens;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.DeviceTokenRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceTokenServiceImpl implements DeviceTokenService{

    private final DeviceTokenRepository deviceTokenRepo;
    private final UserRepository userRepo;
    @Override
    public void saveDeviceToken(DeviceTokenDTO deviceTokenDTO, Long userId) throws Exception {
        Users users = userRepo.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("not found User by id"));

        // build device token
        DeviceTokens deviceTokens = DeviceTokens.builder()
                .tokenOfDevice(deviceTokenDTO.getRegistrationToken())
                .platform("WEB")
                .user(users)
                .build();

        // save token fcm
        deviceTokenRepo.save(deviceTokens);
    }
}
