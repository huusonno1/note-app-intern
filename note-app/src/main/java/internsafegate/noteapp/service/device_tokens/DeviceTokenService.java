package internsafegate.noteapp.service.device_tokens;

import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;

public interface DeviceTokenService {
    void saveDeviceToken(DeviceTokenDTO deviceTokenDTO, Long userId) throws Exception;
}
