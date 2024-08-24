package internsafegate.noteapp.controller.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.note.NoteListResponse;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/notes")
public class NoteController {
    private final NoteService noteService;
    private final SecurityUtils securityUtils;

//  CREATE
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

//  READ
    @GetMapping("/{noteId}")
    public ResponseEntity<ResponseObject> getNoteById(
            @PathVariable Long noteId
    ) throws Exception {

        NoteResponse noteResponse = noteService.getNoteByIds(noteId);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteResponse)
                .message("Note found successfully.")
                .build());
    }

    @GetMapping("/list-notes")
    public ResponseEntity<ResponseObject> getListNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteListResponse noteListResponse = noteService.getListNotes(loggedInUser.getId(), pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteListResponse)
                .message("Get list notes successfully.")
                .build());
    }

//    List Notes by Tag
    @GetMapping("/list-notes-by-tag")
    public ResponseEntity<ResponseObject> getListNotesByTag(
            @RequestParam String nameTag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteListResponse noteListResponse = noteService.getListNotesByTag(loggedInUser.getId(), nameTag, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteListResponse)
                .message("Get list notes successfully.")
                .build());
    }
//    Search Notes by Title

//    Filter Notes by Status

//  UPDATE
    @PutMapping("/{noteId}")
    public ResponseEntity<ResponseObject> updateNotes(
            @PathVariable Long noteId,
            @RequestBody NoteDTO noteDTO
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        if(noteDTO.getUserId() == null) {
            noteDTO.setUserId(loggedInUser.getId());
        }

        NoteResponse noteResponse = noteService.updateNote( noteId, noteDTO);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(noteResponse)
                .message("Update node successfully.")
                .build());
    }
    @PutMapping("/{noteId}/pin")
    public ResponseEntity<ResponseObject> pinNotes(
            @PathVariable Long noteId
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteResponse noteResponse = noteService.pinNote( noteId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(noteResponse)
                .message("Update node successfully.")
                .build());
    }

    @PutMapping("/{noteId}/unpin")
    public ResponseEntity<ResponseObject> unpinNotes(
            @PathVariable Long noteId
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteResponse noteResponse = noteService.unpinNote( noteId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(noteResponse)
                .message("Update node successfully.")
                .build());
    }

//    Change Note Order

//  DELETE
    @DeleteMapping("/{noteId}")
    public ResponseEntity<ResponseObject> deleteNotes(
            @PathVariable Long noteId
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        noteService.deleteNote( noteId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("Delete node successfully.")
                .build());
    }
}
