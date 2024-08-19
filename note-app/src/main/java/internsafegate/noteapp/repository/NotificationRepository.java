package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.owner.id = ?1")
    Page<Notification> getAllNotifications(Long ownerId, PageRequest pageRequest);
}
