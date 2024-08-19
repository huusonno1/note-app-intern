package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.NoteContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteContentRepository extends JpaRepository<NoteContent, Long> {
}
