package internsafegate.noteapp.service.fcm;

public interface FCMService {
    void sendNotification(String token, String title, String body);
}
