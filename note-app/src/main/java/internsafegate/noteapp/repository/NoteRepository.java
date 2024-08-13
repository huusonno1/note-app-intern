package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Notes, Long> {
}
