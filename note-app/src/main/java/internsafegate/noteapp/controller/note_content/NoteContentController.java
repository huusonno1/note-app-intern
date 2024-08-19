package internsafegate.noteapp.controller.note_content;

import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.note_content.ListNoteContentResponse;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.note_content.NoteContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/{noteId}/note-content")
public class NoteContentController {
    private final SecurityUtils securityUtils;
    private final NoteContentService noteContentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNoteContent(
            @RequestPart("NoteContentDTO") NoteContentDTO noteContentDTO,
            @RequestPart("FileImage") MultipartFile file
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteContentResponse noteContentResponse = noteContentService
                .createNoteContent(noteContentDTO, loggedInUser.getId(), file);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(noteContentResponse)
                .message("Account created note-content successful")
                .build());
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getListNoteContent(
            @PathVariable Long noteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, limit);

        ListNoteContentResponse noteContentResponse = noteContentService
                .getListNoteContent(noteId, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(noteContentResponse)
                .message("Account created note successful")
                .build());
    }

    @PutMapping("{noteContentId}")
    public ResponseEntity<ResponseObject> getListNoteContent(
            @PathVariable Long noteContentId,
            @RequestPart("NoteContentDTO") NoteContentDTO noteContentDTO,
            @RequestPart("FileImage") MultipartFile file
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteContentResponse noteContentResponse = noteContentService
                .updateNoteContent(noteContentId, noteContentDTO, loggedInUser.getId(), file);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(noteContentResponse)
                .message("Account update note-content successful")
                .build());
    }

    @DeleteMapping("{noteContentId}")
    public ResponseEntity<ResponseObject> getListNoteContent(
            @PathVariable Long noteContentId
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();

        noteContentService
                .deleteNoteContent(noteContentId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("Account delete note-content successful")
                .build());
    }
}
