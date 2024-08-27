package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.Notes;
import internsafegate.noteapp.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR u.email like %:keyword% ) ")
    Page<Users> searchUsers(String keyword, Pageable pageable);
}
