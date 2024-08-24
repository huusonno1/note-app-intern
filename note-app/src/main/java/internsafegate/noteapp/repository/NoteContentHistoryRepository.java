package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.NoteContentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteContentHistoryRepository extends JpaRepository<NoteContentHistory, Long> {
    @Query("SELECT h FROM NoteContentHistory h WHERE h.noteId = ?1 ORDER BY h.changedAt DESC ")
    Page<NoteContentHistory> getAllNoteContentByNoteId(Long noteId, Pageable pageable);
}
