package internsafegate.noteapp.service.fcm;

import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;

public interface FCMService {
    void sendNotification(String token, String title, String body);

    void saveDeviceToken(DeviceTokenDTO deviceTokenDTO, Long userId) throws Exception;
}
