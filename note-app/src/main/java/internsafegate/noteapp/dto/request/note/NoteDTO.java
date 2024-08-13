package internsafegate.noteapp.dto.request.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import internsafegate.noteapp.model.NoteStatus;
import internsafegate.noteapp.model.Users;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDTO {

    @JsonProperty("title")
    private String title;

    @JsonProperty("is_pinned")
    private boolean isPinned;

    @JsonProperty("status_notes")
    @Enumerated(EnumType.STRING)
    private NoteStatus statusNotes;

    @JsonProperty("number_order")
    private Long numberOrder;

    @JsonProperty("user_id")
    private Long userId;

}
