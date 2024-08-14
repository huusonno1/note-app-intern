package internsafegate.noteapp.dto.request.tag;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDTO {
    @JsonProperty("name_tag")
    private String nameTag;

    @JsonProperty("is_active")
    private boolean isActive;
}
