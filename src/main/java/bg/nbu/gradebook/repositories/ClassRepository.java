package bg.nbu.gradebook.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.nbu.gradebook.domain.entities.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    Optional<Class> findByIdGradeAndGroup(int grade, String group);
}
