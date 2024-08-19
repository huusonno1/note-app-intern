package internsafegate.noteapp.service.note_content;

import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import org.springframework.web.multipart.MultipartFile;

public interface NoteContentService {
    NoteContentResponse createNoteContent(NoteContentDTO noteContentDTO, Long ownerId, MultipartFile file) throws Exception;
}
