package internsafegate.noteapp.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("google_account_id")
    private Integer googleAccountId;

    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
}
