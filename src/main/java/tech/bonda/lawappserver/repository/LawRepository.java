package tech.bonda.lawappserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bonda.lawappserver.models.Law;

@Repository
public interface LawRepository extends JpaRepository<Law, Long> {
}