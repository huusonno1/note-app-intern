package internsafegate.noteapp.service.notification;

import internsafegate.noteapp.dto.response.notification.ListNotificationResponse;
import org.springframework.data.domain.PageRequest;

public interface NotificationService {
    void markRead(Long notificationId, Long ownerId) throws Exception;

    ListNotificationResponse getListNotification(Long ownerId, PageRequest pageRequest)  throws Exception;
}
