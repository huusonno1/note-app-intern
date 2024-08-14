package internsafegate.noteapp.dto.response.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name_tag")
    private String nameTag;

    @JsonProperty("is_active")
    private boolean isActive;
}
