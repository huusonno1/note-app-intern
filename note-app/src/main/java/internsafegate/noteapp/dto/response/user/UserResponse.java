package internsafegate.noteapp.dto.response.user;

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
public class UserResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("google_account_id")
    private Integer googleAcountId;

    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("is_active")
    private Boolean isActive;
}
