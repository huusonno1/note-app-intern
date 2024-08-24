package internsafegate.noteapp.service.note_content_history;

import internsafegate.noteapp.dto.response.note_content_history.ListNoteContentHistoryResponse;
import org.springframework.data.domain.Pageable;

public interface NoteContentHistoryService {
    ListNoteContentHistoryResponse getListNoteContent(Long noteId, Pageable pageable) throws Exception;
}
