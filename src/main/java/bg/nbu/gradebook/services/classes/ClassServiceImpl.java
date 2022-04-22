package bg.nbu.gradebook.services.classes;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.domain.entities.Class;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.repositories.ClassRepository;
import bg.nbu.gradebook.services.users.UserService;

@Service
public class ClassServiceImpl implements ClassService {
    private final UserService userService;
    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClassServiceImpl(UserService userService, ClassRepository classRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.classRepository = classRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void enrollStudentInClass(long studentId, long classId) {
        final UserServiceModel userServiceModel = userService.findById(studentId)
                .orElseThrow();
        final Class schoolClass = classRepository.findById(classId)
                .orElseThrow();

        schoolClass.getStudents()
                .add(modelMapper.map(userServiceModel, User.class));

        classRepository.saveAndFlush(schoolClass);
    }

    @Override
    public void unenrollStudentFromClass(long studentId, long classId) {
        final UserServiceModel userServiceModel = userService.findById(studentId)
                .orElseThrow();
        final Class schoolClass = classRepository.findById(classId)
                .orElseThrow();

        schoolClass.getStudents()
                .add(modelMapper.map(userServiceModel, User.class));

        classRepository.saveAndFlush(schoolClass);
    }
}