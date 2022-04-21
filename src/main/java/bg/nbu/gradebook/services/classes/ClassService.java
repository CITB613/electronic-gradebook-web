package bg.nbu.gradebook.services.classes;

import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;

public interface ClassService {
    void enrollStudentInClass(UserServiceModel userServiceModel, ClassServiceModel classServiceModel);

    void unenrollStudentFromClass(UserServiceModel userServiceModel, ClassServiceModel classServiceModel);
}
