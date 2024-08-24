package internsafegate.noteapp.dto.response.share;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListShareNoteResponse {
    private List<ShareNoteResponse> shareNoteResponses;
    private int totalPages;
}