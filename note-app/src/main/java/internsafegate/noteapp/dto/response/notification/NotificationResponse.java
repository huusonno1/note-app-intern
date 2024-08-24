package internsafegate.noteapp.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("notification_type")
    private String notificationType;

    @JsonProperty("is_read")
    private boolean isRead ;

    @JsonProperty("message")
    private String message;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("created_at")
    private Date createAt;
}
