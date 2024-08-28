package internsafegate.noteapp.dto.request.share;

import com.fasterxml.jackson.annotation.JsonProperty;

import internsafegate.noteapp.model.ContentType;
import internsafegate.noteapp.model.StatusShare;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareNoteDTO {
    @JsonProperty("is_contribution_accepted")
    private boolean contributionAccepted ;

    @JsonProperty("note_id")
    private Long noteId;

    @JsonProperty("receiver")
    private String receiver;

    @JsonProperty("status_share")
    @Enumerated(EnumType.STRING)
    private StatusShare statusShare;

}
