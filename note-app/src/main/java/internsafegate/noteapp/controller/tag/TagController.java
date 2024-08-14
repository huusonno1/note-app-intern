package internsafegate.noteapp.controller.tag;

import internsafegate.noteapp.dto.request.tag.TagDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.tag.TagResponse;
import internsafegate.noteapp.dto.response.user.UserResponse;
import internsafegate.noteapp.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/tags")
public class TagController {

    private final TagService tagService;

    @PostMapping ("/{noteId}/create")
    public ResponseEntity<ResponseObject> addTagToNoteId(
            @RequestBody TagDTO tagDTO,
            @PathVariable Long noteId
    ) throws Exception {
        TagResponse tagResponse = tagService.addTag(tagDTO, noteId);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(tagResponse)
                .message("add tag successful")
                .build());
    }

    @DeleteMapping ("/{noteId}/delete/{tagId}")
    public ResponseEntity<ResponseObject> deleteTagToNoteId(
            @PathVariable Long noteId,
            @PathVariable Long tagId
    ) throws Exception {

        TagResponse tagResponse = tagService.deleteTag(noteId, tagId);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(tagResponse)
                .message("delete tag successful")
                .build());
    }

}
