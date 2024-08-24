package internsafegate.noteapp.service.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.note.NoteListResponse;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import org.springframework.data.domain.PageRequest;

public interface NoteService {
    NoteResponse createNote(NoteDTO noteDTO) throws Exception;

    NoteResponse getNoteByIds(Long noteId) throws Exception;

    NoteResponse updateNote(Long noteId, NoteDTO noteDTO) throws Exception;

    void deleteNote(Long noteId, Long userId) throws Exception;

    NoteResponse pinNote(Long noteId, Long userId) throws Exception;

    NoteResponse unpinNote(Long noteId, Long userId) throws Exception;

    NoteListResponse getListNotes(Long userId, PageRequest pageRequest) throws Exception;

    NoteListResponse getListNotesByTag(Long userId, String tag, PageRequest pageRequest) throws Exception;
}
