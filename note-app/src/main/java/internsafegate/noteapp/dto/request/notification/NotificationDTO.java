package internsafegate.noteapp.dto.request.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {
    @JsonProperty("token")
    private String token;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;
}
