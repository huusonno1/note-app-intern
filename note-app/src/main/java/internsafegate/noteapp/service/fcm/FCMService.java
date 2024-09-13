package internsafegate.noteapp.service.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import internsafegate.noteapp.dto.request.device_tokens.DeviceTokenDTO;
import internsafegate.noteapp.model.Notification;

import java.util.List;

public interface FCMService {
    void sendNotification(List<String> token, String title, Notification data) throws FirebaseMessagingException;
}
