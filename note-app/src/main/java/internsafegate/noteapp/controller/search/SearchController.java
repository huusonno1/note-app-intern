package internsafegate.noteapp.controller.search;

import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.note.NoteListResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/search")
public class SearchController {
    private final NoteService noteService;
    private final SecurityUtils securityUtils;

    @GetMapping("/notes")
    public ResponseEntity<ResponseObject> searchNotes(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser = securityUtils.getLoggedInUser();

        NoteListResponse noteListResponse = noteService.searchNotes(loggedInUser.getId(), keyword, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteListResponse)
                .message("Search notes successfully.")
                .build());
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseObject> searchUsers(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser = securityUtils.getLoggedInUser();

        NoteListResponse noteListResponse = noteService.searchNotes(loggedInUser.getId(), keyword, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteListResponse)
                .message("Search notes successfully.")
                .build());
    }

}
