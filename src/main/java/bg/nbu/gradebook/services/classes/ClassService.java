package bg.nbu.gradebook.services.classes;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import bg.nbu.gradebook.domain.entities.Subject;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;

public interface ClassService {
    ClassServiceModel enrollStudentInClass(long studentId, long classId);

    void unenrollStudentFromClass(long studentId, long classId);

    ClassServiceModel create(ClassBindingModel classData);

    void addSubject(long classId, long subjectId);

    void removeSubject(long classId, long subjectId);

    ClassServiceModel updateStudentClass(long classId, @Valid ClassBindingModel classBindingModel);

    List<ClassServiceModel> findAll();

    void deleteById(long classId);

    Set<User> findAllStudentsByClassId(long classId);

    Set<Subject> findAllSubjectsByClassId(long classId);
}
