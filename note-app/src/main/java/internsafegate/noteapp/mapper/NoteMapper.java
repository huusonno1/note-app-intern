package internsafegate.noteapp.mapper;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public static NoteResponse toResponseDTO(Notes note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .isPinned(note.isPinned())
                .statusNotes(note.getStatusNotes())
                .numberOrder(note.getNumberOrder())
                .ownerId(note.getUser().getId())
                .build();
    }

    public static Notes toEntity(NoteDTO noteRequestDTO, Users user) {
        return Notes.builder()
                .title(noteRequestDTO.getTitle())
                .isPinned(noteRequestDTO.isPinned())
                .statusNotes(noteRequestDTO.getStatusNotes())
                .numberOrder(noteRequestDTO.getNumberOrder())
                .user(user)
                .build();
    }
}
