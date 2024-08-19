package internsafegate.noteapp.service.note_content;

import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.note_content.NoteContentResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.mapper.NoteContentMapper;
import internsafegate.noteapp.model.NoteContent;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.NoteContentRepository;
import internsafegate.noteapp.repository.NoteRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class NoteContentServiceImpl implements NoteContentService{

    private final NoteRepository noteRepo;
    private final UserRepository userRepo;
    private final NoteContentRepository noteContentRepo;

    @Override
    public NoteContentResponse createNoteContent(
            NoteContentDTO noteContentDTO,
            Long ownerId,
            MultipartFile file
    ) throws Exception {
        Notes notes = noteRepo.findById(noteContentDTO.getNoteId())
                .orElseThrow(() -> new DataNotFoundException("Not found note by note id"));
        if(notes.getUser().getId() != ownerId){
            // check xem user co duoc chia se note_id khong
            // va user phai chap nhan chia se moi duoc tao moi
//
            //Neu khong duoc nua thi bao Exception
            throw new DataNotFoundException("User dont created new note-content");
        }
        Users owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new DataNotFoundException("Not found user by owner id"));

        //set image_url
        String image_url  = file.getOriginalFilename();

        NoteContent noteContent = NoteContentMapper
                .toEntity(noteContentDTO, owner, notes, image_url);

        NoteContent saved = noteContentRepo.save(noteContent);

        // set note_log_version

        return NoteContentMapper.toResponseDTO(saved);
    }
}
