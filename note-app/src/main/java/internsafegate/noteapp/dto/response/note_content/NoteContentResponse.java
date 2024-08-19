package internsafegate.noteapp.dto.response.note_content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteContentResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("text_content")
    private String textContent;

    @JsonProperty("status_note_content")
    private String statusNoteContent;

    @JsonProperty("note_id")
    private Long noteId;
}
