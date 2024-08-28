package internsafegate.noteapp.service.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.note.NoteListResponse;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.model.NoteStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteService {
    NoteResponse createNote(NoteDTO noteDTO) throws Exception;

    NoteResponse getNoteByIds(Long noteId, Long ownerId) throws Exception;

    NoteResponse updateNote(Long noteId, NoteDTO noteDTO) throws Exception;

    void deleteNote(Long noteId, Long userId) throws Exception;

    NoteResponse pinNote(Long noteId, Long userId) throws Exception;

    NoteResponse unpinNote(Long noteId, Long userId) throws Exception;

    NoteListResponse getListNotes(Long userId, PageRequest pageRequest) throws Exception;

    NoteListResponse getListNotesByTag(Long userId, String tag, PageRequest pageRequest) throws Exception;

    NoteListResponse searchNotes(Long userId, String keyword, NoteStatus noteStatus, Long tagId, Pageable pageable) throws Exception;

    NoteListResponse getListNotesByStatus(Long userId, List<NoteStatus> status, Pageable pageable) throws Exception;

    NoteListResponse getListNotesByStatusPin(Long userId, Boolean statusPin, Pageable pageable) throws Exception;

    NoteListResponse getListNotesCustom(Long userId, Boolean statusPin, NoteStatus noteStatus, Long tagId, Pageable pageable) throws Exception;

}
