package internsafegate.noteapp.dto.request.device_tokens;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckDeviceDTO {
    @JsonProperty("device_id")
    private String deviceId = "";
}
