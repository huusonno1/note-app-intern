package internsafegate.noteapp.controller.note_content_history;

import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.note_content_history.ListNoteContentHistoryResponse;
import internsafegate.noteapp.service.note_content_history.NoteContentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/{noteId}/note-content-history")
public class NoteContentHistoryController {
    private final NoteContentHistoryService noteContentHistoryService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getListNoteContentHistory(
            @PathVariable Long noteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, limit);

        ListNoteContentHistoryResponse contentHistoryResponse = noteContentHistoryService.getListNoteContent(noteId, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(contentHistoryResponse)
                .message("get list note content history successful")
                .build());
    }
}
