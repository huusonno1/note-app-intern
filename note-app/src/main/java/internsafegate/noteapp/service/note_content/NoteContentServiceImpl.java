package internsafegate.noteapp.service.note_content;

import internsafegate.noteapp.dto.request.note_content.NoteContentDTO;
import internsafegate.noteapp.dto.response.note_content.ListNoteContentResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ListNoteContentResponse getListNoteContent(
            Long noteId,
            PageRequest pageRequest
    ) throws Exception {
        Page<NoteContent> noteContentPage = noteContentRepo.getAllNoteContent(noteId, pageRequest);
        if(noteContentPage == null) {
            throw new DataNotFoundException("Failed to fetch noteContent: noteContentPage is null");
        }
        List<NoteContentResponse> listNoteContentResponses = noteContentPage.getContent().stream()
                .map(noteContent -> {
                    NoteContentResponse noteContentResponse = new NoteContentResponse();
                    noteContentResponse.setId(noteContent.getId());
                    noteContentResponse.setContentType(noteContent.getContentType().name());
                    noteContentResponse.setTextContent(noteContent.getTextContent());
                    noteContentResponse.setImageUrl(noteContent.getImageUrl());
                    noteContentResponse.setStatusNoteContent(noteContent.getStatusNoteContent().name());
                    noteContentResponse.setNoteId(noteContent.getNotes().getId());
                    noteContentResponse.setOwnerId(noteContent.getUser().getId());

                    return noteContentResponse;
                })
                .collect(Collectors.toList());

        return ListNoteContentResponse.builder()
                .noteContents(listNoteContentResponses)
                .totalPages(noteContentPage.getTotalPages())
                .build();
    }

    @Override
    public NoteContentResponse updateNoteContent(
            Long noteContentId,
            NoteContentDTO noteContentDTO,
            Long ownerId,
            MultipartFile file
    ) throws Exception {
        NoteContent noteContent = noteContentRepo.findById(noteContentId)
                .orElseThrow(() -> new DataNotFoundException("Not found note-content by note-content id"));

        Users owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new DataNotFoundException("Not found user by owner id"));
        if(noteContent.getUser().getId() != ownerId
        ){
            noteContent.setUser(owner);
        }
        if(noteContentDTO.getStatusNoteContent() != null &&
                noteContentDTO.getStatusNoteContent() != noteContent.getStatusNoteContent()
        ){
            noteContent.setStatusNoteContent(noteContentDTO.getStatusNoteContent());
        }
        if(noteContentDTO.getContentType() != null &&
                noteContentDTO.getContentType() != noteContent.getContentType()
        ){
            noteContent.setContentType(noteContentDTO.getContentType());
        }
        if(noteContentDTO.getTextContent() != null &&
                noteContentDTO.getTextContent() != noteContent.getTextContent()
        ){
            noteContent.setTextContent(noteContentDTO.getTextContent());
        }

        if(!file.isEmpty()){
            //set image_url base
            String image_url  = file.getOriginalFilename();

            noteContent.setImageUrl(image_url);
        }


        NoteContent saved = noteContentRepo.save(noteContent);

        // set note_log_version

        return NoteContentMapper.toResponseDTO(saved);
    }


}
