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

    //    CREATE
    @PostMapping ("/{nodeId}/create")
    public ResponseEntity<ResponseObject> addTag(
            @RequestBody TagDTO tagDTO,
            @PathVariable Long nodeId
    ) throws Exception {
        TagResponse tagResponse = tagService.addTag(tagDTO, nodeId);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(tagResponse)
                .message("add tag successful")
                .build());
    }

//    READ

//    UPDATE

//    DELETE
}
