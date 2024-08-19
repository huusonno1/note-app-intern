package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.NoteContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteContentRepository extends JpaRepository<NoteContent, Long> {
    @Query("select nc from NoteContent nc WHERE nc.notes.id = ?1")
    Page<NoteContent> getAllNoteContent(Long noteId, PageRequest pageRequest);
}
