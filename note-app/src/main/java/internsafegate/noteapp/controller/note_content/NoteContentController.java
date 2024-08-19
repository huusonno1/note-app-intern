package internsafegate.noteapp.controller.note_content;

import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.note_content.NoteContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/note-content")
public class NoteContentController {
    private final SecurityUtils securityUtils;
    private final NoteContentService noteContentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNotes(
            @RequestPart("NoteContentDTO") NoteContentDTO noteContentDTO,
            @RequestPart("file") MultipartFile file
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteContentResponse noteContentResponse = noteContentService
                .createNoteContent(noteContentDTO, loggedInUser.getId(), file);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(noteContentResponse)
                .message("Account created note successful")
                .build());
    }
}
