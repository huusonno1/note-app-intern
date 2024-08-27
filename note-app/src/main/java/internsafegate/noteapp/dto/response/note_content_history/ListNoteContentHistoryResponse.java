package internsafegate.noteapp.dto.response.note_content_history;

import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListNoteContentHistoryResponse {
    private List<NoteContentHistoryResponse> contentHistoryResponses;
    private int totalPages;
    private long totalItems;
}