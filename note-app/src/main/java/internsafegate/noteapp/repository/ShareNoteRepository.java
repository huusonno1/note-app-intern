package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.ShareNotes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShareNoteRepository extends JpaRepository<ShareNotes, Long> {
    @Query("SELECT sn FROM ShareNotes sn WHERE sn.sender.id = ?1 ORDER BY sn.updatedAt DESC ")
    Page<ShareNotes> getAllShareNoteOfUser(Long senderId, Pageable pageable);
}
