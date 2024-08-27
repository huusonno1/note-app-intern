package internsafegate.noteapp.mapper;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.request.share.ShareNoteDTO;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.dto.response.share.ShareNoteResponse;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.ShareNotes;
import internsafegate.noteapp.model.Users;

public class ShareNoteMapper {
    public static ShareNoteResponse toResponseDTO(ShareNotes shareNotes) {
        return ShareNoteResponse.builder()
                .id(shareNotes.getId())
                .noteId(shareNotes.getNotes().getId())
                .contributionAccepted(shareNotes.isContributionAccepted())
                .senderId(shareNotes.getSender().getId())
                .receiverId(shareNotes.getReceiver().getId())
                .noteTitle(shareNotes.getNotes().getTitle())
                .build();
    }

    public static ShareNotes toEntity(Notes notes, Users sender, Users receiver, Boolean status) {
        return ShareNotes.builder()
                .notes(notes)
                .sender(sender)
                .receiver(receiver)
                .contributionAccepted(status)
                .build();
    }
}
