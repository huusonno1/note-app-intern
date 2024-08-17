package internsafegate.noteapp.service.share;

import internsafegate.noteapp.dto.request.share.ShareNoteDTO;
import internsafegate.noteapp.dto.response.share.ShareNoteResponse;

public interface ShareNoteService {
    ShareNoteResponse shareNote(ShareNoteDTO shareNoteDTO, Long senderId) throws Exception;

    ShareNoteResponse getShareNoteById(Long shareId, Long senderId) throws Exception;

    ShareNoteResponse acceptOrRejectShareNoteId(ShareNoteDTO shareNoteDTO, Long shareId, Long receiverId) throws Exception;

    void cancelShareNoteId(Long shareId, Long senderId) throws Exception;
}
