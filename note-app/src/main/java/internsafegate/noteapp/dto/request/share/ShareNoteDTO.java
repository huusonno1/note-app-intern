package internsafegate.noteapp.dto.request.share;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
