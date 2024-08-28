package internsafegate.noteapp.dto.response.share;

import com.fasterxml.jackson.annotation.JsonProperty;
import internsafegate.noteapp.model.StatusShare;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareNoteResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("is_contribution_accepted")
    private boolean contributionAccepted ;

    @JsonProperty("note_id")
    private Long noteId;

    @JsonProperty("sender_id")
    private Long senderId;

    @JsonProperty("receiver_id")
    private Long receiverId;

    @JsonProperty("note_title")
    private String noteTitle;

    @JsonProperty("status_share")
    private String statusShare;
}
