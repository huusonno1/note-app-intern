package internsafegate.noteapp.controller.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.note.NoteListResponse;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.model.NoteStatus;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteResponse noteResponse = noteService.getNoteByIds(noteId, loggedInUser.getId());

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

    @GetMapping("/list-notes-pin")
    public ResponseEntity<ResponseObject> getListNotesPin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        Boolean statusPin = true;

        NoteListResponse noteListResponse = noteService
                .getListNotesByStatusPin(loggedInUser.getId(), statusPin, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteListResponse)
                .message("Get list notes successfully.")
                .build());
    }

    @GetMapping("/list-notes-unpin")
    public ResponseEntity<ResponseObject> getListNotesUnpin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        Boolean statusPin = false;

        NoteListResponse noteListResponse = noteService
                .getListNotesByStatusPin(loggedInUser.getId(), statusPin, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteListResponse)
                .message("Get list notes successfully.")
                .build());
    }

    @GetMapping("/list-notes-custom")
    public ResponseEntity<ResponseObject> getListNotesCustom(
            @RequestParam(required = false) Boolean statusPin,
            @RequestParam(defaultValue = "", required = false) String statusNote,
            @RequestParam(required = false) Long tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        NoteStatus noteStatus = null;
        if(statusNote != null  && !statusNote.isEmpty()){
            try {
                noteStatus = NoteStatus.valueOf(statusNote.toUpperCase());
            } catch (IllegalArgumentException e) {
                noteStatus = null;
            }
        }

        NoteListResponse noteListResponse = noteService
                .getListNotesCustom(loggedInUser.getId(), statusPin, noteStatus, tagId, pageRequest);

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

//    Filter Notes by Status
    @GetMapping("/status")
    public ResponseEntity<ResponseObject> getListNotesByStatus(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam List<String> status,
            @RequestParam(required = false) Long tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        List<NoteStatus> noteStatuses = status.stream()
                .map(String::toUpperCase)  // Chuyển tất cả các chuỗi thành chữ hoa để khớp với giá trị enum
                .map(NoteStatus::valueOf)  // Chuyển chuỗi thành giá trị `NoteStatus`
                .collect(Collectors.toList());

        NoteListResponse noteListResponse = noteService
                .getListNotesByStatus(loggedInUser.getId(), keyword, noteStatuses, tagId, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.FOUND)
                .data(noteListResponse)
                .message("Get list notes successfully.")
                .build());
    }

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

    @PutMapping("/{noteId}/archived")
    public ResponseEntity<ResponseObject> archivedNotes(
            @PathVariable Long noteId
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        noteService.archivedNote( noteId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("archived node successfully.")
                .build());
    }
    @PutMapping("/{noteId}/completed")
    public ResponseEntity<ResponseObject> completedNotes(
            @PathVariable Long noteId
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        noteService.completedNote( noteId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("completed node successfully.")
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

    @PutMapping("/{noteId}/order")
    public ResponseEntity<ResponseObject> changeOrderNotes(
            @PathVariable Long noteId,
            @RequestParam Long newOrder
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        noteService.updateNoteOrder( noteId, newOrder, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("Order updated successfully")
                .build());
    }

//    Change Note Order
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
