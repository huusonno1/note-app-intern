package internsafegate.noteapp.service.note_content;

import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.note_content.ListNoteContentResponse;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface NoteContentService {
    NoteContentResponse createNoteContent(NoteContentDTO noteContentDTO, Long ownerId) throws Exception;

    ListNoteContentResponse getListNoteContent(Long noteId, PageRequest pageRequest) throws Exception;

    NoteContentResponse updateNoteContent(Long noteId, Long noteContentId, NoteContentDTO noteContentDTO, Long ownerId) throws Exception;

    void deleteNoteContent(Long noteContentId, Long ownerId) throws Exception;
}
