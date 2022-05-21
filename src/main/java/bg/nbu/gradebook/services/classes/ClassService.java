package bg.nbu.gradebook.services.classes;

import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;

public interface ClassService {
    void enrollStudentInClass(long studentId, long classId);

    void unenrollStudentFromClass(long studentId, long classId);

    ClassServiceModel create(ClassBindingModel classData);
}
