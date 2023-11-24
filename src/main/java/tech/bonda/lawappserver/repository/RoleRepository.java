package tech.bonda.lawappserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bonda.lawappserver.models.Role.ERole;
import tech.bonda.lawappserver.models.Role.Role;


import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
