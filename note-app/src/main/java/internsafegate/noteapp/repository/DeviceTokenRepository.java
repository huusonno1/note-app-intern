package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.DeviceTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceTokenRepository extends JpaRepository<DeviceTokens, Long> {
    @Query("SELECT d FROM DeviceTokens d WHERE d.user.id = :userId")
    List<DeviceTokens> findByUserId(Long userId);
}
