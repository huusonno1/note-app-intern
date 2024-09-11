package internsafegate.noteapp.repository;

import internsafegate.noteapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.id = :id")
    Role findRoleById(Long id);
}
