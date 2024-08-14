package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tags, Long> {
    @Query("SELECT t FROM Tags t WHERE t.nameTag = ?1")
    Tags findByName(String nameTag);
}
