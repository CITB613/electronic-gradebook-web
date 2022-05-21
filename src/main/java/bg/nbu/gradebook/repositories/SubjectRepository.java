package bg.nbu.gradebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.nbu.gradebook.domain.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
