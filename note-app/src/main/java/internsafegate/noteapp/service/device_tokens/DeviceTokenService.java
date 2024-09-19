package internsafegate.noteapp.service.device_tokens;

import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;
import internsafegate.noteapp.dto.response.device_tokens.CheckDeviceResponse;

public interface DeviceTokenService {
    void saveDeviceToken(DeviceTokenDTO deviceTokenDTO, Long userId) throws Exception;

    CheckDeviceResponse checkDeviceId(String deviceId, Long userId) throws Exception;
}
