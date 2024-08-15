package internsafegate.noteapp.service.tag;

import internsafegate.noteapp.dto.request.tag.TagDTO;
import internsafegate.noteapp.dto.response.tag.TagListResponse;
import internsafegate.noteapp.dto.response.tag.TagResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.mapper.TagMapper;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Tags;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.NoteRepository;
import internsafegate.noteapp.repository.TagRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepo;

    private final NoteRepository noteRepo;

    private final UserRepository userRepo;

    @Override
    public TagResponse addTag(TagDTO tagDTO, Long noteId) throws Exception {
        Optional<Tags> existingTag = tagRepo.findByName(tagDTO.getNameTag());

        Tags tag;
        if (existingTag != null) {
            tag = existingTag.get();
        } else {
            tag = new Tags();
            tag.setNameTag(tagDTO.getNameTag());
            Boolean isActiveDTO = tagDTO.isActive();
            tag.setActive(isActiveDTO);
            tag = tagRepo.save(tag);
        }

        Notes note = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException("Note not found for id: " + noteId));

        note.getTags().add(tag);
        noteRepo.save(note);

        return TagMapper.toResponseDTO(tag);
    }

    @Override
    public TagResponse deleteTag(Long noteId, Long tagId) throws Exception {
        Tags tag = tagRepo.findById(tagId)
                .orElseThrow(() -> new DataNotFoundException("Note not found for id: " + tagId));

        Notes note = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException("Note not found for id: " + noteId));

        note.getTags().remove(tag);
        noteRepo.save(note);

        return TagMapper.toResponseDTO(tag);
    }

    @Override
    public TagResponse createTag(TagDTO tagDTO, Long userId) throws Exception {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found for id: " + userId));
        Optional<Tags> tags = tagRepo.findByName(tagDTO.getNameTag());
        if (tags.isPresent()){
            throw new DataNotFoundException("Tag is exist for name tag: " + tagDTO.getNameTag());
        }

        Tags savedTag = TagMapper.toEntity(tagDTO,users);
        tagRepo.save(savedTag);
        return TagMapper.toResponseDTO(savedTag);
    }

    @Override
    public TagResponse getTagById(Long tagId) throws Exception {
        Tags tags = tagRepo.findById(tagId)
                .orElseThrow(() -> new DataNotFoundException("Tag not found for id: " + tagId));
        return TagMapper.toResponseDTO(tags);
    }

    @Override
    public TagResponse updateTagByIdOfUser(TagDTO tagDTO, Long tagId, Long userId) throws Exception {
        Tags tags = tagRepo.findById(tagId)
                .orElseThrow(() -> new DataNotFoundException("Tag not found for id: " + tagId));
        if(tags.getUsers().getId() != userId){
            throw new DataNotFoundException("user don't have tag id" + tagId);
        }
        if (tagDTO.getNameTag() != null && !tagDTO.getNameTag().equals(tags.getNameTag())) {
            tags.setNameTag(tagDTO.getNameTag());
        }
        Boolean isActiveDTO = tagDTO.isActive();
        if (isActiveDTO != null && isActiveDTO != tags.isActive()) {
            tags.setActive(isActiveDTO);
        }
        Tags savedTag = tagRepo.save(tags);
        return TagMapper.toResponseDTO(savedTag);
    }

    @Override
    public void deleteTagByIdOfUser(Long tagId, Long userId) throws Exception {
        Tags tags = tagRepo.findById(tagId)
                .orElseThrow(() -> new DataNotFoundException("Tag not found for id: " + tagId));
        if(tags.getUsers().getId() != userId){
            throw new DataNotFoundException("Tag is not created by user");
        }
        tagRepo.delete(tags);

    }

    @Override
    public TagListResponse getListTagOfUser(Long id) throws Exception {
        return null;
    }
}
