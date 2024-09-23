package internsafegate.noteapp.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutDTO {
    @JsonProperty("device_id")
    private String deviceId = "";
}
