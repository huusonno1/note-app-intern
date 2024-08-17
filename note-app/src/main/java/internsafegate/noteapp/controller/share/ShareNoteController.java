package internsafegate.noteapp.controller.share;

import internsafegate.noteapp.dto.request.share.ShareNoteDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.share.ShareNoteResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.share.ShareNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
