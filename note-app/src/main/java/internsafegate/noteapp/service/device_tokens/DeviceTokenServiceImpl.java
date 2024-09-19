package internsafegate.noteapp.service.device_tokens;

import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;
import internsafegate.noteapp.dto.response.device_tokens.CheckDeviceResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.model.DeviceTokens;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.DeviceTokenRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceTokenServiceImpl implements DeviceTokenService{

    private final DeviceTokenRepository deviceTokenRepo;
    private final UserRepository userRepo;
    @Override
    public void saveDeviceToken(DeviceTokenDTO deviceTokenDTO, Long userId) throws Exception {

        DeviceTokens deviceTokens = deviceTokenRepo.findByUserIdAndDeviceId(userId, deviceTokenDTO.getDeviceId())
                .orElseThrow(() -> new DataNotFoundException("not found row data have both user id and device id"));
        // build device token
        deviceTokens.setTokenOfDevice(deviceTokenDTO.getRegistrationToken());
        deviceTokens.setPlatform("WEB");

        // save token fcm
        deviceTokenRepo.save(deviceTokens);
    }

    @Override
    public CheckDeviceResponse checkDeviceId(String deviceId, Long userId) throws Exception {
        Users users = userRepo.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("not found User by id"));

        Optional<DeviceTokens> deviceTokenOpt = deviceTokenRepo.findByDeviceId(deviceId);

        CheckDeviceResponse response = new CheckDeviceResponse();

        if (deviceTokenOpt.isPresent()) {
            DeviceTokens deviceTokens = deviceTokenOpt.get();
            boolean isSameUser = deviceTokens.getUser().getId().equals(userId);

            if(isSameUser){
                // Nếu tồn tại, cập nhật status_token thành true và trả về true
                updateStatusToken(deviceTokens);
                response.setExists(true);
            } else {

                createNewDeviceToken(users, deviceTokens.getDeviceId());

                response.setExists(true);
                response.setDeviceId(deviceTokens.getDeviceId());
            }

        } else {

            // Nếu chưa có, sinh deviceId mới và trả về false kèm deviceId
            String newDeviceId = UUID.randomUUID().toString();

            createNewDeviceToken(users, newDeviceId);

            response.setExists(true);
            response.setDeviceId(newDeviceId);
        }
        return response;
    }

    private void updateStatusToken(DeviceTokens deviceToken) {
        deviceToken.setStatusToken(true);
        deviceTokenRepo.save(deviceToken);
    }

    // Tạo mới DeviceToken
    private void createNewDeviceToken(Users users, String deviceId) {
        DeviceTokens newDeviceToken = new DeviceTokens();
        newDeviceToken.setUser(users);
        newDeviceToken.setDeviceId(deviceId);
        newDeviceToken.setStatusToken(true);
        deviceTokenRepo.save(newDeviceToken);
    }
}
