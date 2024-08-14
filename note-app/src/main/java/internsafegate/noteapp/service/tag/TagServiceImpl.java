package internsafegate.noteapp.service.tag;

import internsafegate.noteapp.dto.request.tag.TagDTO;
import internsafegate.noteapp.dto.response.tag.TagResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.mapper.TagMapper;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Tags;
import internsafegate.noteapp.repository.NoteRepository;
import internsafegate.noteapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepo;

    private final NoteRepository noteRepo;

    @Override
    public TagResponse addTag(TagDTO tagDTO, Long noteId) throws Exception {
        Tags existingTag = tagRepo.findByName(tagDTO.getNameTag());

        Tags tag;
        if (existingTag != null) {
            tag = existingTag;
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
}
