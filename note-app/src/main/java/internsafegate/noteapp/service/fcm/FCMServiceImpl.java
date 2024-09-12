package internsafegate.noteapp.service.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.model.DeviceTokens;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.DeviceTokenRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FCMServiceImpl implements FCMService{

    private final DeviceTokenRepository deviceTokenRepo;
    private final UserRepository userRepo;


    public void sendNotification(String token, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Map<String, String> data = new HashMap<>();
        data.put("id", "39");
        data.put("notification_type", "SHARED");
        data.put("is_read", "false");
        data.put("message", "Share Note By A");
        data.put("owned_id", "1");
        data.put("sender", "username");

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(data)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveDeviceToken(DeviceTokenDTO deviceTokenDTO, Long userId) throws Exception {
        Users users = userRepo.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("not found User by id"));

        // check id token fcm ??

        // build device token
        DeviceTokens deviceTokens = DeviceTokens.builder()
                .tokenOfDevice(deviceTokenDTO.getRegistrationToken())
                .user(users)
                .build();

        // save token fcm
        deviceTokenRepo.save(deviceTokens);
    }
}
