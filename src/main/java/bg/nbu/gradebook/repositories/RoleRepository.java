package bg.nbu.gradebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.nbu.gradebook.domain.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
