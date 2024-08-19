package internsafegate.noteapp.dto.request.note_content;

import com.fasterxml.jackson.annotation.JsonProperty;
import internsafegate.noteapp.model.ContentType;
import internsafegate.noteapp.model.NoteStatus;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteContentDTO {
    @JsonProperty("content_type")
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("text_content")
    private String textContent;

    @JsonProperty("status_note_content")
    @Enumerated(EnumType.STRING)
    private NoteStatus statusNoteContent;

    @JsonProperty("note_id")
    private Long noteId;
}
