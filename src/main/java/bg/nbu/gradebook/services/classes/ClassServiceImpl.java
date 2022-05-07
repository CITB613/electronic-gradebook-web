package bg.nbu.gradebook.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.domain.entities.Class;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.repositories.ClassRepository;
import bg.nbu.gradebook.services.users.UserService;

@Service
public class ClassServiceImpl implements ClassService {
    private final UserService userService;
    private final ClassRepository classRepository;

    @Autowired
    public ClassServiceImpl(UserService userService, ClassRepository classRepository) {
        this.userService = userService;
        this.classRepository = classRepository;
    }

    @Override
    public void enrollStudentInClass(long studentId, long classId) {
        final User user = userService.findById(studentId)
                .orElseThrow();
        final Class schoolClass = classRepository.findById(classId)
                .orElseThrow();

        schoolClass.getStudents()
                .add(user);

        classRepository.save(schoolClass);
    }

    @Override
    public void unenrollStudentFromClass(long studentId, long classId) {
        final User user = userService.findById(studentId)
                .orElseThrow();
        final Class schoolClass = classRepository.findById(classId)
                .orElseThrow();

        schoolClass.getStudents()
                .remove(user);

        classRepository.save(schoolClass);
    }
}