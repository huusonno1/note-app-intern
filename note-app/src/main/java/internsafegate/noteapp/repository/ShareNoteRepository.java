package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.ShareNotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareNoteRepository extends JpaRepository<ShareNotes, Long> {
}
