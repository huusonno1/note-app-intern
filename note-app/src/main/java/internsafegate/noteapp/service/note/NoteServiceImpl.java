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

    @Override
    public NoteResponse getNoteByIds(Long noteId) throws Exception {
        Notes notes = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Note %d is not found", noteId)));

        return noteMapper.toResponseDTO(notes);
    }

    @Override
    public NoteResponse updateNote(Long noteId, NoteDTO noteDTO) throws Exception {
        Notes notes = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException("Node not found"));
        Users users = userRepo.findById(noteDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Admin not found"));
        if(notes.getUser().getId() != users.getId()){
            throw new DataNotFoundException("Note is not created by user");
        }

        if(noteDTO.getTitle() != null && noteDTO.getTitle() != notes.getTitle()) {
            notes.setTitle(noteDTO.getTitle());
        }

        if(noteDTO.getStatusNotes() != null && noteDTO.getStatusNotes() != notes.getStatusNotes()) {
            notes.setStatusNotes(noteDTO.getStatusNotes());
        }

        Boolean isPinnedDTO = noteDTO.isPinned();
        if (isPinnedDTO != null && isPinnedDTO != notes.isPinned()) {
            notes.setPinned(isPinnedDTO);
        }

        if(noteDTO.getNumberOrder() != null && noteDTO.getNumberOrder() != notes.getNumberOrder()) {
            notes.setNumberOrder(noteDTO.getNumberOrder());
        }


        Notes savedNote = noteRepo.save(notes);

        return noteMapper.toResponseDTO(savedNote);
    }
}
