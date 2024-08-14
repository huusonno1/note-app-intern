package internsafegate.noteapp.mapper;

import internsafegate.noteapp.dto.response.tag.TagResponse;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Tags;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public static TagResponse toResponseDTO(Tags tags) {
        return TagResponse.builder()
                .id(tags.getId())
                .nameTag(tags.getNameTag())
                .isActive(tags.isActive())
                .build();
    }
}
