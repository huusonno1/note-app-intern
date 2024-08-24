package internsafegate.noteapp.mapper;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import internsafegate.noteapp.model.NoteContent;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteContentMapper {
    public static NoteContentResponse toResponseDTO(NoteContent noteContent) {
        return NoteContentResponse.builder()
                .id(noteContent.getId())
                .contentType(noteContent.getContentType().name())
                .textContent(noteContent.getTextContent())
                .statusNoteContent(noteContent.getStatusNoteContent().name())
                .imageUrl(noteContent.getImageUrl())
                .noteId(noteContent.getNotes().getId())
                .ownerId(noteContent.getUser().getId())
                .build();
    }

    public static NoteContent toEntity(
            NoteContentDTO noteContentDTO,
            Users user,
            Notes notes
    ) {
        return NoteContent.builder()
                .contentType(noteContentDTO.getContentType())
                .textContent(noteContentDTO.getTextContent())
                .statusNoteContent(noteContentDTO.getStatusNoteContent())
                .imageUrl(noteContentDTO.getImageUrl())
                .user(user)
                .notes(notes)
                .build();
    }

    public static List<NoteContentResponse> toListNoteContentResponse(List<NoteContent> noteContents) {
        return noteContents.stream()
                .map(NoteContentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
