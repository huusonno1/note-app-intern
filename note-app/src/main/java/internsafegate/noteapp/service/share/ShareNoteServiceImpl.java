package internsafegate.noteapp.service.share;

import internsafegate.noteapp.dto.request.share.ShareNoteDTO;
import internsafegate.noteapp.dto.response.share.ShareNoteResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.mapper.ShareNoteMapper;
import internsafegate.noteapp.model.*;
import internsafegate.noteapp.repository.NoteRepository;
import internsafegate.noteapp.repository.NotificationRepository;
import internsafegate.noteapp.repository.ShareNoteRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShareNoteServiceImpl implements ShareNoteService{

    private final NoteRepository noteRepo;
    private final UserRepository userRepo;
    private final ShareNoteRepository shareNoteRepo;
    private final NotificationRepository notificationRepo;

    @Override
    public ShareNoteResponse shareNote(ShareNoteDTO shareNoteDTO, Long senderId) throws Exception {
        Notes notes = noteRepo.findById(shareNoteDTO.getNoteId())
                .orElseThrow(() -> new DataNotFoundException("Not found note with id" + shareNoteDTO.getNoteId()));
        if(notes.getUser().getId() != senderId){
            throw new DataNotFoundException("User dont have noteId");
        }
        Users sender = userRepo.findById(senderId)
                .orElseThrow(() -> new DataNotFoundException("Not found sender"));

        Users receiver = userRepo.findByEmail(shareNoteDTO.getReceiver())
                .orElseThrow(() -> new DataNotFoundException("Not found receiver"));

        ShareNotes shareNotes = ShareNoteMapper.toEntity(notes, sender, receiver, false);

        ShareNotes savedShareNote = shareNoteRepo.save(shareNotes);

        createNotification(receiver, savedShareNote);

        return ShareNoteMapper.toResponseDTO(savedShareNote);
    }

    @Override
    public ShareNoteResponse getShareNoteById(Long shareId, Long senderId) throws Exception {
        ShareNotes shareNotes = shareNoteRepo.findById(shareId)
                .orElseThrow(() -> new DataNotFoundException("Not found share note id"));

        if(shareNotes.getSender().getId() != senderId){
            throw new DataNotFoundException("User dont have share note id" + shareId);
        }

        return ShareNoteMapper.toResponseDTO(shareNotes);
    }

    @Override
    public ShareNoteResponse acceptOrRejectShareNoteId(ShareNoteDTO shareNoteDTO, Long shareId, Long receiverId) throws Exception {
        ShareNotes shareNotes = shareNoteRepo.findById(shareId)
                .orElseThrow(() -> new DataNotFoundException("Not found share note id"));
        if(shareNotes.getReceiver().getId() != receiverId){
            throw new DataNotFoundException("User dont have share note id" + shareId);
        }
        boolean contribution = shareNoteDTO.isContributionAccepted();
        if(shareNotes.isContributionAccepted() != contribution){
            shareNotes.setContributionAccepted(contribution);
        }
        ShareNotes saved = shareNoteRepo.save(shareNotes);
        return ShareNoteMapper.toResponseDTO(saved);
    }

    @Override
    public void cancelShareNoteId(Long shareId, Long senderId) throws Exception {
        ShareNotes shareNotes = shareNoteRepo.findById(shareId)
                .orElseThrow(() -> new DataNotFoundException("ShareNotes not found for id: " + shareId));
        if(shareNotes.getSender().getId() != senderId){
            throw new DataNotFoundException("ShareNotes is not created by user");
        }
        Notification notification = notificationRepo.findByShareNoteId(shareId);
        notificationRepo.delete(notification);
        shareNoteRepo.delete(shareNotes);

    }


    private void createNotification(Users receiver, ShareNotes savedShareNote) {
        Notification notification = Notification.builder()
                .owner(receiver)
                .message("Share Note")
                .shareNote(savedShareNote)
                .isRead(false)
                .notificationType(NotificationType.SHARED)
                .build();

        notificationRepo.save(notification);
    }
}
