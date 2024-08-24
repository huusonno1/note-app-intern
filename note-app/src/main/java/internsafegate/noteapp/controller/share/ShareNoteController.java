package internsafegate.noteapp.controller.share;

import internsafegate.noteapp.dto.request.share.ShareNoteDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.share.ListShareNoteResponse;
import internsafegate.noteapp.dto.response.share.ShareNoteResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.share.ShareNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/share-notes")
public class ShareNoteController {
    private final SecurityUtils securityUtils;
    private final ShareNoteService shareNoteService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> shareNote(
            @RequestBody ShareNoteDTO shareNoteDTO
    ) throws Exception{
        Users loggedInUser= securityUtils.getLoggedInUser();

        ShareNoteResponse shareNoteResponse = shareNoteService.shareNote(shareNoteDTO, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(shareNoteResponse)
                .message("Note shared successfully")
                .build());
    }

    @GetMapping("/{shareId}")
    public ResponseEntity<ResponseObject> getShareNoteById(
            @PathVariable Long shareId
    ) throws Exception{
        Users loggedInUser= securityUtils.getLoggedInUser();

        ShareNoteResponse shareNoteResponse = shareNoteService.getShareNoteById(shareId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(shareNoteResponse)
                .message("Get note shared successfully")
                .build());
    }
//    get share notes
    @GetMapping("")
    public ResponseEntity<ResponseObject> getShareNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception{
        PageRequest pageRequest = PageRequest.of(page, limit);

        Users loggedInUser= securityUtils.getLoggedInUser();

        ListShareNoteResponse shareNoteResponse = shareNoteService.getShareNotes(loggedInUser.getId(), pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(shareNoteResponse)
                .message("Get note shared successfully")
                .build());
    }

    @PutMapping("/{shareId}")
    public ResponseEntity<ResponseObject> acceptOrRejectShareNoteId(
            @RequestBody ShareNoteDTO shareNoteDTO,
            @PathVariable Long shareId
    ) throws Exception{
        Users loggedInUser= securityUtils.getLoggedInUser();

        ShareNoteResponse shareNoteResponse = shareNoteService.acceptOrRejectShareNoteId(shareNoteDTO, shareId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(shareNoteResponse)
                .message("Update note shared successfully")
                .build());
    }

    @DeleteMapping("/{shareId}")
    public ResponseEntity<ResponseObject> cancelShareNoteId(
            @PathVariable Long shareId
    ) throws Exception{
        Users loggedInUser= securityUtils.getLoggedInUser();

        shareNoteService.cancelShareNoteId(shareId, loggedInUser.getId());

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("Cancel note shared successfully")
                .build());
    }

}
