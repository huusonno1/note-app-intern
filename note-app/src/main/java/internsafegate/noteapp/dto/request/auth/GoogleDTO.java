package internsafegate.noteapp.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleDTO {
    @JsonProperty("google_token")
    private String googleToken;
}
