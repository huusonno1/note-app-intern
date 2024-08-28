package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.NoteStatus;
import internsafegate.noteapp.model.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Notes, Long> {
    @Query("SELECT n FROM Notes n WHERE n.user.id = ?1 ORDER BY n.isPinned DESC, n.numberOrder ASC ")
    Page<Notes> getAllNotes(Long userId, PageRequest pageRequest);
    @Query("SELECT n FROM Notes n JOIN n.tags t WHERE n.user.id = ?1 AND t.nameTag = ?2 ORDER BY n.isPinned DESC, n.numberOrder ASC ")
    Page<Notes> getAllNotesByTag(Long userId, String tagId, PageRequest pageRequest);

    @Query("SELECT n FROM Notes n JOIN n.tags t WHERE n.user.id = :userId " +
            "AND (:keyword IS NULL OR :keyword = '' OR n.title LIKE %:keyword% )" +
            "AND (:noteStatus IS NULL OR n.statusNotes = :noteStatus) " +
            "AND (:statusPin IS NULL OR n.isPinned = :statusPin) " +
            "AND (:tagId IS NULL OR t.id = :tagId)" +
            "ORDER BY n.createdAt DESC ")
    Page<Notes> searchNotes(Long userId,
                            @Param("keyword") String keyword,
                            NoteStatus noteStatus,
                            Boolean statusPin,
                            Long tagId,
                            Pageable pageable);
    @Query("SELECT n FROM Notes n WHERE n.user.id = :userId AND (:statuses IS NULL OR n.statusNotes IN :statuses) ORDER BY n.isPinned DESC, n.numberOrder ASC ")
    Page<Notes> getAllNotesByStatus(Long userId, List<NoteStatus> statuses, Pageable pageable);
    @Query("SELECT n FROM Notes n WHERE n.user.id = ?1 AND n.isPinned = ?2 ORDER BY n.createdAt DESC")
    Page<Notes> getAllNotesByStatusPin(Long userId, Boolean statusPin, Pageable pageable);

    @Query("SELECT n FROM Notes n JOIN n.tags t WHERE n.user.id = :userId " +
            "AND (:statusPin IS NULL OR n.isPinned = :statusPin) " +
            "AND (:noteStatus IS NULL OR n.statusNotes = :noteStatus) " +
            "AND (:tagId IS NULL OR t.id = :tagId)" +
            "ORDER BY n.numberOrder DESC ")
    Page<Notes> getAllNotesCustom(Long userId,
                                  Boolean statusPin,
                                  NoteStatus noteStatus,
                                  Long tagId,
                                  Pageable pageable);

    @Query("SELECT COALESCE(MAX(n.numberOrder), 0) FROM Notes n WHERE n.user.id = :userId")
    Long findMaxNumberOrder(Long userId);
    @Modifying
    @Query("UPDATE Notes n SET n.numberOrder = n.numberOrder - 1 WHERE " +
            "n.numberOrder > :oldOrder AND " +
            "n.numberOrder <= :newOrder AND " +
            "n.user.id = :userId")
    void updateOrderDecrement(Long oldOrder, Long newOrder, Long userId);
    @Modifying
    @Query("UPDATE Notes n SET n.numberOrder = n.numberOrder + 1 WHERE " +
            "n.numberOrder >= :newOrder AND " +
            "n.numberOrder < :oldOrder AND " +
            "n.user.id = :userId")
    void updateOrderIncrement(Long newOrder, Long oldOrder, Long userId);
}
