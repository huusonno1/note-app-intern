package internsafegate.noteapp.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    @JsonProperty("email")
    private String email = "";

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be black")
    private String password = "";
}
