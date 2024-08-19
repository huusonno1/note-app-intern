package internsafegate.noteapp.mapper;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import internsafegate.noteapp.model.NoteContent;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import org.springframework.stereotype.Component;

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
            Notes notes,
            String imageUrl
    ) {
        return NoteContent.builder()
                .contentType(noteContentDTO.getContentType())
                .textContent(noteContentDTO.getTextContent())
                .statusNoteContent(noteContentDTO.getStatusNoteContent())
                .imageUrl(imageUrl)
                .user(user)
                .notes(notes)
                .build();
    }
}