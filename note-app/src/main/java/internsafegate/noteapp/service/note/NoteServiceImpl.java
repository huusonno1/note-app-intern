package internsafegate.noteapp.service.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.mapper.NoteMapper;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.NoteRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final UserRepository userRepo;

    private final NoteRepository noteRepo;

    private final NoteMapper noteMapper;

    @Override
    public NoteResponse createNote(NoteDTO noteDTO) throws Exception {
        Users users = userRepo.findById(noteDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Admin not found"));

        Notes note = noteMapper.toEntity(noteDTO, users);

        Notes savedNote = noteRepo.save(note);

        return noteMapper.toResponseDTO(savedNote);
    }
}
