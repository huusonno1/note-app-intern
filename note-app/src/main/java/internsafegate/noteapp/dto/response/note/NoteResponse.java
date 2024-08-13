package internsafegate.noteapp.dto.response.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import internsafegate.noteapp.model.NoteStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("is_pinned")
    private boolean isPinned;

    @JsonProperty("status_notes")
    @Enumerated(EnumType.STRING)
    private NoteStatus statusNotes;

    @JsonProperty("number_order")
    private Long numberOrder;

    @JsonProperty("owner_id")
    private Long ownerId;

}
