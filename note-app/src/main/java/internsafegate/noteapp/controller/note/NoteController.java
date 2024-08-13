package internsafegate.noteapp.controller.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/notes")
public class NoteController {
    private final NoteService noteService;
    private final SecurityUtils securityUtils;


    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNotes(
            @RequestBody NoteDTO noteDTO
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();

        if(noteDTO.getUserId() == null) {
            noteDTO.setUserId(loggedInUser.getId());
        }

        NoteResponse noteResponse = noteService.createNote(noteDTO);

        return ResponseEntity.ok(ResponseObject.builder()
                 .status(HttpStatus.CREATED)
                 .data(noteResponse)
                 .message("Account created note successful")
                 .build());
    }

}
