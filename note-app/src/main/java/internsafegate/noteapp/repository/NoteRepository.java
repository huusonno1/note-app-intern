package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Notes, Long> {
    @Query("SELECT n FROM Notes n WHERE n.user.id = ?1 ORDER BY n.isPinned DESC, n.numberOrder ASC ")
    Page<Notes> getAllNotes(Long userId, PageRequest pageRequest);
}
