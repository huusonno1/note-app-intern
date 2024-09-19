package internsafegate.noteapp.service.fcm;

import com.google.firebase.messaging.*;
import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.model.DeviceTokens;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.DeviceTokenRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FCMServiceImpl implements FCMService{


    public void sendNotification(
            List<String> token,
            String title,
            internsafegate.noteapp.model.Notification dataNoti
    ) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(dataNoti.getMessage())
                .build();

        Boolean isRead = dataNoti.isRead();
        Map<String, String> data = new HashMap<>();
        data.put("id", dataNoti.getId().toString());
        data.put("notification_type", dataNoti.getNotificationType().name());
        data.put("is_read", isRead.toString());
        data.put("message", dataNoti.getMessage());
        data.put("owned_id", dataNoti.getOwner().getId().toString() + "moi");
        data.put("sender", dataNoti.getShareNote().getSender().getUsername());


        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .putAllData(data)
                .addAllTokens(token)
                .build();
        BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
        if (response.getFailureCount() > 0) {
            List<SendResponse> responses = response.getResponses();
            List<String> failedTokens = new ArrayList<>();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    // The order of responses corresponds to the order of the registration tokens.
                    failedTokens.add(token.get(i));
                }
            }

            System.out.println("List of tokens that caused failures: " + failedTokens);
        }

    }


}
