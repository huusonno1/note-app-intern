package internsafegate.noteapp.dto.response.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListNotificationResponse {
    private List<NotificationResponse> notificationList;
    private int totalPages;

}
