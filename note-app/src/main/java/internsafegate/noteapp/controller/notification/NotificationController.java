package internsafegate.noteapp.controller.notification;


import internsafegate.noteapp.dto.request.notification.NotificationDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.notification.ListNotificationResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.fcm.FCMService;
import internsafegate.noteapp.service.fcm.FCMServiceImpl;
import internsafegate.noteapp.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/notifications")
public class NotificationController {
    private final SecurityUtils securityUtils;
    private final NotificationService notificationService;
    private final FCMService fcmService;

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<ResponseObject> markRead(
            @PathVariable Long notificationId
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();

        notificationService.markRead(notificationId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("Notification marked as read")
                .build());
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getListNotification(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        ListNotificationResponse notifications = notificationService.getListNotification(loggedInUser.getId(), pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(notifications)
                .message("Notification marked as read")
                .build());
    }

}
