package internsafegate.noteapp.service.share;

import internsafegate.noteapp.dto.request.share.ShareNoteDTO;
import internsafegate.noteapp.dto.response.share.ListShareNoteResponse;
import internsafegate.noteapp.dto.response.share.ShareNoteResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ShareNoteService {
    ShareNoteResponse shareNote(ShareNoteDTO shareNoteDTO, Long senderId) throws Exception;

    ShareNoteResponse getShareNoteById(Long shareId, Long senderId) throws Exception;

    ShareNoteResponse acceptOrRejectShareNoteId(ShareNoteDTO shareNoteDTO, Long shareId, Long receiverId) throws Exception;

    void cancelShareNoteId(Long shareId, Long senderId) throws Exception;

    ListShareNoteResponse getShareNotes(Long senderId, Pageable pageable) throws Exception;

    ListShareNoteResponse getShareNotesOfReceiver(Long receiverId, Pageable pageable) throws Exception;
}
