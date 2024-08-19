package internsafegate.noteapp.service.notification;

import internsafegate.noteapp.dto.response.note.NoteListResponse;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.dto.response.notification.ListNotificationResponse;
import internsafegate.noteapp.dto.response.notification.NotificationResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Notification;
import internsafegate.noteapp.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepo;
    @Override
    public void markRead(Long notificationId, Long ownerId) throws Exception {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new DataNotFoundException("Not found notification has id: " + notificationId));

        if(notification.getOwner().getId() != ownerId) {
            throw new DataNotFoundException("User not have notificationId" + notificationId);
        }
        notification.setRead(true);
        notificationRepo.save(notification);
    }

    @Override
    public ListNotificationResponse getListNotification(Long ownerId, PageRequest pageRequest) throws Exception {
        Page<Notification> NotificationsPage = notificationRepo.getAllNotifications(ownerId, pageRequest);
        if (NotificationsPage == null) {
            throw new DataNotFoundException("Failed to fetch notifications: notificationsPage is null");
        }
        List<NotificationResponse> notificationResponses = NotificationsPage.getContent().stream()
                .map(notification -> {
                    NotificationResponse notificationResponse = new NotificationResponse();
                    notificationResponse.setId(notification.getId());
                    notificationResponse.setNotificationType(notification.getNotificationType().name());
                    notificationResponse.setRead(notification.isRead());
                    notificationResponse.setMessage(notification.getMessage());
                    notificationResponse.setOwnerId(ownerId);
                    notificationResponse.setCreateAt(null);

                    return notificationResponse;
                })
                .collect(Collectors.toList());

        return ListNotificationResponse.builder()
                .notificationList(notificationResponses)
                .totalPages(NotificationsPage.getTotalPages())
                .build();
    }

}
