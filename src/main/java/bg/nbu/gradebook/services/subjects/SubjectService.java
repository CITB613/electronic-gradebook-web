package bg.nbu.gradebook.services.subjects;

import java.util.List;
import java.util.Optional;

import bg.nbu.gradebook.domain.entities.Subject;
import bg.nbu.gradebook.domain.models.service.SubjectServiceModel;

public interface SubjectService {
    List<SubjectServiceModel> findAll();

    SubjectServiceModel createSubject(SubjectServiceModel subjectServiceModel);

    Optional<Subject> findById(long subjectId);
}
