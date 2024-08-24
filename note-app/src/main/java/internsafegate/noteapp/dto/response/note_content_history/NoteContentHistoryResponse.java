package internsafegate.noteapp.dto.response.note_content_history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteContentHistoryResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("note_content_id")
    private Long noteContentId;

    @JsonProperty("note_id")
    private Long noteId;

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

    @JsonProperty("changed_at")
    private LocalDateTime changedAt;

    @JsonProperty("action")
    private String action;
}
