package internsafegate.noteapp.mapper;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.request.tag.TagDTO;
import internsafegate.noteapp.dto.response.tag.TagResponse;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Tags;
import internsafegate.noteapp.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {
    public static TagResponse toResponseDTO(Tags tags) {
        return TagResponse.builder()
                .id(tags.getId())
                .nameTag(tags.getNameTag())
                .isActive(tags.isActive())
                .build();
    }

    public static Tags toEntity(TagDTO tagDTO, Users user) {
        return Tags.builder()
                .nameTag(tagDTO.getNameTag())
                .isActive(tagDTO.isActive())
                .users(user)
                .build();
    }

    public static List<TagResponse> toListTagResponse(List<Tags> tags) {
        return tags.stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getNameTag(), tag.isActive()))
                .collect(Collectors.toList());
    }
}
