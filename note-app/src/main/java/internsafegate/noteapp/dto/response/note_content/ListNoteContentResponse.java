package internsafegate.noteapp.dto.response.note_content;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListNoteContentResponse {
    private List<NoteContentResponse> noteContents;
    private int totalPages;
}
