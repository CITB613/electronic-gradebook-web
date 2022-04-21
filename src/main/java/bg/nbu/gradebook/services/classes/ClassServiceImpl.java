package bg.nbu.gradebook.services.classes;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.domain.entities.Class;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.repositories.ClassRepository;

@Service
public class ClassServiceImpl implements ClassService {
    private final ModelMapper modelMapper;
    private final ClassRepository classRepository;

    @Autowired
    public ClassServiceImpl(ModelMapper modelMapper, ClassRepository classRepository) {
        this.modelMapper = modelMapper;
        this.classRepository = classRepository;
    }

    @Override
    public void enrollStudentInClass(UserServiceModel userServiceModel, ClassServiceModel classServiceModel) {
        findAllStudentsByClass(classServiceModel).add(modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public void unenrollStudentFromClass(UserServiceModel userServiceModel, ClassServiceModel classServiceModel) {
        findAllStudentsByClass(classServiceModel).remove(modelMapper.map(userServiceModel, User.class));
    }

    private Set<User> findAllStudentsByClass(ClassServiceModel classServiceModel) {
        return classRepository.findByIdGradeAndGroup(classServiceModel.getGrade(), classServiceModel.getGroup())
                .map(Class::getStudents)
                .orElseThrow();
    }
}