package internsafegate.noteapp.service.tag;

import internsafegate.noteapp.dto.request.tag.TagDTO;
import internsafegate.noteapp.dto.response.tag.TagResponse;

public interface TagService {
    TagResponse addTag(TagDTO tagDTO, Long nodeId) throws Exception;
}
