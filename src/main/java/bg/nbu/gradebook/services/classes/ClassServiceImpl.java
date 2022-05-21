package bg.nbu.gradebook.services.classes;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import org.modelmapper.ModelMapper;
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
    private Mapper modelMapper;

    @Autowired
    public ClassServiceImpl(UserService userService, ClassRepository classRepository, Mapper modelMapper) {
        this.userService = userService;
        this.classRepository = classRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public ClassServiceModel create(ClassBindingModel classData) {
        Class c = modelMapper.map(classData, Class.class);

        c = classRepository.save(c);

        return modelMapper.map(c, ClassServiceModel.class);
    }
}