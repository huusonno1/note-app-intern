package internsafegate.noteapp.mapper;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import internsafegate.noteapp.model.NoteContent;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {
    public static NoteResponse toResponseDTO(Notes note) {
        List<NoteContentResponse> noteContentDTOS = note.getNoteContents().stream()
                .map(NoteMapper:: toNoteContentResponse)
                .collect(Collectors.toList());

        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .isPinned(note.isPinned())
                .statusNotes(note.getStatusNotes())
                .numberOrder(note.getNumberOrder())
                .noteContentDTOS(noteContentDTOS)
                .ownerId(note.getUser().getId())
                .build();
    }

    private static NoteContentResponse toNoteContentResponse(NoteContent noteContent) {
        return NoteContentMapper.toResponseDTO(noteContent);
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
