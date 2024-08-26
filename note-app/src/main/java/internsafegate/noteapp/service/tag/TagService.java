package internsafegate.noteapp.service.tag;

import internsafegate.noteapp.dto.request.tag.TagDTO;
import internsafegate.noteapp.dto.response.tag.TagListResponse;
import internsafegate.noteapp.dto.response.tag.TagResponse;

public interface TagService {
    TagResponse addTag(TagDTO tagDTO, Long noteId) throws Exception;

    TagResponse deleteTag(Long nodeId, Long tagId) throws Exception;

    TagResponse createTag(TagDTO tagDTO, Long userId) throws Exception;

    TagResponse getTagById(Long tagId) throws Exception;

    TagResponse updateTagByIdOfUser(TagDTO tagDTO, Long tagId, Long userId) throws Exception;

    void deleteTagByIdOfUser(Long tagId, Long id) throws Exception;

    TagListResponse getListTagOfUser(Long userId) throws Exception;
}
