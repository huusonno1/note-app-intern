package internsafegate.noteapp.service.share;

import internsafegate.noteapp.dto.request.share.ShareNoteDTO;
import internsafegate.noteapp.dto.response.share.ShareNoteResponse;

public interface ShareNoteService {
    ShareNoteResponse shareNote(ShareNoteDTO shareNoteDTO, Long senderId) throws Exception;
}
