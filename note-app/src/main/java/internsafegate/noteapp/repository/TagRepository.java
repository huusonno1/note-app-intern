package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tags, Long> {
    @Query("SELECT t FROM Tags t WHERE t.nameTag = ?1")
    Optional<Tags> findByName(String nameTag);

    @Query("SELECT t FROM Tags t WHERE t.nameTag = ?1 and t.users.id = ?2")
    Tags findByNameAndUserId(String nameTag, Long userId);

    @Query("SELECT t FROM Tags t WHERE t.users.id = ?1")
    List<Tags> findListTagByUserId(Long userId);
}
