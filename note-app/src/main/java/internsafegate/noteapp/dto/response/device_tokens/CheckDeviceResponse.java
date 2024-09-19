package internsafegate.noteapp.dto.response.device_tokens;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckDeviceResponse {
    @JsonProperty("exists")
    private Boolean exists;

    @JsonProperty("device_id")
    private String deviceId;

}
