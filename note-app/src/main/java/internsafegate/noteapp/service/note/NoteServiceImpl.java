package internsafegate.noteapp.service.note;

import internsafegate.noteapp.dto.request.note.NoteDTO;
import internsafegate.noteapp.dto.response.note.NoteListResponse;
import internsafegate.noteapp.dto.response.note.NoteResponse;
import internsafegate.noteapp.dto.response.tag.TagResponse;
import internsafegate.noteapp.exception.DataNotFoundException;
import internsafegate.noteapp.mapper.NoteContentMapper;
import internsafegate.noteapp.mapper.NoteMapper;
import internsafegate.noteapp.mapper.TagMapper;
import internsafegate.noteapp.model.NoteContent;
import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Tags;
import internsafegate.noteapp.model.Users;
import internsafegate.noteapp.repository.NoteContentRepository;
import internsafegate.noteapp.repository.NoteRepository;
import internsafegate.noteapp.repository.TagRepository;
import internsafegate.noteapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final UserRepository userRepo;

    private final NoteRepository noteRepo;

    private final NoteMapper noteMapper;
    private final NoteContentRepository noteContentRepo;
    private final TagRepository tagRepo;

    @Override
    public NoteResponse createNote(NoteDTO noteDTO) throws Exception {
        Users users = userRepo.findById(noteDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Admin not found"));

        Notes note = noteMapper.toEntity(noteDTO, users);

        Notes savedNote = noteRepo.save(note);


        List<NoteContent> noteContents = noteDTO.getNoteContentDTOS().stream()
                .map(contentDTO -> NoteContentMapper.toEntity(contentDTO, users, savedNote))
                .collect(Collectors.toList());

        noteContentRepo.saveAll(noteContents);

        List<Tags> tagsList = noteDTO.getTags().stream()
                .map(tagDTO -> {
                    Tags existingTag = tagRepo.findByNameAndUserId(tagDTO.getNameTag(), users.getId());
                    if (existingTag == null) {
                        tagDTO.setActive(true);
                        Tags newTag = TagMapper.toEntity(tagDTO, users);
                        return tagRepo.save(newTag);
                    } else {
                        return existingTag;
                    }
                })
                .collect(Collectors.toList());

        savedNote.setTags(tagsList);
        noteRepo.save(savedNote);

        savedNote.setNoteContents(noteContents);

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

    @Override
    public void deleteNote(Long noteId, Long userId) throws Exception{
        Notes notes = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException("Node not found"));
        if(notes.getUser().getId() != userId){
            throw new DataNotFoundException("Note is not created by user");
        }
        noteRepo.delete(notes);
    }

    @Override
    public NoteResponse pinNote(Long noteId, Long userId) throws Exception {
        Notes notes = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException("Node not found"));
        if(notes.getUser().getId() != userId){
            throw new DataNotFoundException("Note is not created by user");
        }

        notes.setPinned(true);

        Notes savedNote = noteRepo.save(notes);

        return noteMapper.toResponseDTO(savedNote);
    }

    @Override
    public NoteResponse unpinNote(Long noteId, Long userId) throws Exception {
        Notes notes = noteRepo.findById(noteId)
                .orElseThrow(() -> new DataNotFoundException("Node not found"));
        if(notes.getUser().getId() != userId){
            throw new DataNotFoundException("Note is not created by user");
        }

        notes.setPinned(false);

        Notes savedNote = noteRepo.save(notes);

        return noteMapper.toResponseDTO(savedNote);
    }

    @Override
    public NoteListResponse getListNotes(Long userId, PageRequest pageRequest) throws Exception {
        Page<Notes> notesPage = noteRepo.getAllNotes(userId, pageRequest);
        if (notesPage == null) {
            throw new DataNotFoundException("Failed to fetch notes: notesPage is null");
        }
        List<NoteResponse> noteResponses = notesPage.getContent().stream()
                .map(note -> {
                    NoteResponse noteResponse = new NoteResponse();
                    noteResponse.setId(note.getId());
                    noteResponse.setTitle(note.getTitle());
                    noteResponse.setStatusNotes(note.getStatusNotes());
                    noteResponse.setPinned(note.isPinned());
                    noteResponse.setNumberOrder(note.getNumberOrder());
                    noteResponse.setOwnerId(note.getUser().getId());
                    noteResponse.setTags(TagMapper.toListTagResponse(note.getTags()));
                    return noteResponse;
                })
                .collect(Collectors.toList());

        return NoteListResponse.builder()
                .notes(noteResponses)
                .totalPages(notesPage.getTotalPages())
                .build();
    }

    @Override
    public NoteListResponse getListNotesByTag(Long userId, String nameTag, PageRequest pageRequest) throws Exception {
        Page<Notes> notesPage = noteRepo.getAllNotesByTag(userId, nameTag, pageRequest);
        if (notesPage == null) {
            throw new DataNotFoundException("Failed to fetch notes: notesPage is null");
        }
        List<NoteResponse> noteResponses = notesPage.getContent().stream()
                .map(note -> {
                    NoteResponse noteResponse = new NoteResponse();
                    noteResponse.setId(note.getId());
                    noteResponse.setTitle(note.getTitle());
                    noteResponse.setStatusNotes(note.getStatusNotes());
                    noteResponse.setPinned(note.isPinned());
                    noteResponse.setNumberOrder(note.getNumberOrder());
                    noteResponse.setOwnerId(note.getUser().getId());
                    noteResponse.setTags(TagMapper.toListTagResponse(note.getTags()));
                    return noteResponse;
                })
                .collect(Collectors.toList());

        return NoteListResponse.builder()
                .notes(noteResponses)
                .totalPages(notesPage.getTotalPages())
                .build();
    }


}
