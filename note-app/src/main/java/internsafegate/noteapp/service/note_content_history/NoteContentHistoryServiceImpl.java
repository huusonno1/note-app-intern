package internsafegate.noteapp.service.note_content_history;

import internsafegate.noteapp.dto.response.note_content_history.ListNoteContentHistoryResponse;
import internsafegate.noteapp.dto.response.note_content_history.NoteContentHistoryResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.model.NoteContentHistory;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.ShareNotes;
import internsafegate.noteapp.repository.NoteContentHistoryRepository;
import internsafegate.noteapp.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteContentHistoryServiceImpl implements NoteContentHistoryService{
    private final NoteRepository noteRepo;
    private final NoteContentHistoryRepository noteContentHistoyRepo;

    @Override
    public ListNoteContentHistoryResponse getListNoteContent(Long noteId, Pageable pageable) throws Exception {
        Notes notes = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException("Not found noteId"));
        Page<NoteContentHistory> historyPage = noteContentHistoyRepo.getAllNoteContentByNoteId(noteId, pageable);
        List<NoteContentHistoryResponse> responseList = historyPage.stream()
                .map(noteContentHistory -> {
                    return NoteContentHistoryResponse.builder()
                            .id(noteContentHistory.getId())
                            .noteContentId(noteContentHistory.getNoteContentId())
                            .noteId(noteContentHistory.getNoteId())
                            .ownerId(noteContentHistory.getOwnerId())
                            .contentType(noteContentHistory.getContentType())
                            .imageUrl(noteContentHistory.getImageUrl())
                            .textContent(noteContentHistory.getTextContent())
                            .statusNoteContent(noteContentHistory.getStatusNoteContent())
                            .changedAt(noteContentHistory.getChangedAt())
                            .action(noteContentHistory.getAction())
                            .build();
                })
                .collect(Collectors.toList());
        return ListNoteContentHistoryResponse.builder()
                .contentHistoryResponses(responseList)
                .totalPages(historyPage.getTotalPages())
                .build();
    }
}
