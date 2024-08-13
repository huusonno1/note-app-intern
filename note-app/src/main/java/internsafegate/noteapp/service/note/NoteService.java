package internsafegate.noteapp.service.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.note.NoteResponse;

public interface NoteService {
    NoteResponse createNote(NoteDTO noteDTO) throws Exception;

    NoteResponse getNoteByIds(Long noteId) throws Exception;
}
