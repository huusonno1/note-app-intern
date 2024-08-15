package internsafegate.noteapp.controller.tag;

import internsafegate.noteapp.dto.request.tag.TagDTO;
import internsafegate.noteapp.dto.response.ResponseObject;
import internsafegate.noteapp.dto.response.tag.TagListResponse;
import internsafegate.noteapp.dto.response.tag.TagResponse;
import internsafegate.noteapp.dto.response.user.UserResponse;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.security.SecurityUtils;
import internsafegate.noteapp.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/tags")
public class TagController {
    private final SecurityUtils securityUtils;

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

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTag(
            @RequestBody TagDTO tagDTO
    ) throws Exception {
        Users loggedInUser= securityUtils.getLoggedInUser();

        TagResponse tagResponse = tagService.createTag(tagDTO, loggedInUser.getId());
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(tagResponse)
                .message("create tag successful")
                .build());
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<ResponseObject> getTagById(
            @PathVariable Long tagId
    ) throws Exception {

        TagResponse tagResponse = tagService.getTagById(tagId);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(tagResponse)
                .message("get tag successful")
                .build());
    }

    @GetMapping("/list-tag")
    public ResponseEntity<ResponseObject> listTagOfUser(
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        TagListResponse tagListResponse = tagService.getListTagOfUser(loggedInUser.getId());
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(tagListResponse)
                .message("get list tag successful")
                .build());
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<ResponseObject> updateTagById(
            @RequestBody TagDTO tagDTO,
            @PathVariable Long tagId
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        TagResponse tagResponse = tagService.updateTagByIdOfUser(tagDTO, tagId, loggedInUser.getId());
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(tagResponse)
                .message("update tag successful")
                .build());
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<ResponseObject> deleteTagById(
            @PathVariable Long tagId
    ) throws Exception {

        Users loggedInUser= securityUtils.getLoggedInUser();

        tagService.deleteTagByIdOfUser(tagId, loggedInUser.getId());
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(null)
                .message("delete tag successful")
                .build());
    }



}
