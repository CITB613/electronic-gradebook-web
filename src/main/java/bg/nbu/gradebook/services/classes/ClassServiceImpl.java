package bg.nbu.gradebook.services.classes;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Class;
import bg.nbu.gradebook.domain.entities.Subject;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import bg.nbu.gradebook.repositories.ClassRepository;
import bg.nbu.gradebook.services.subjects.SubjectService;
import bg.nbu.gradebook.services.users.UserService;

@Service
public class ClassServiceImpl implements ClassService {
    private final UserService userService;
    private final SubjectService subjectService;
    private final ClassRepository classRepository;
    private final Mapper modelMapper;

    @Autowired
    public ClassServiceImpl(UserService userService, SubjectService subjectService, ClassRepository classRepository,
            Mapper modelMapper) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.classRepository = classRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ClassServiceModel enrollStudentInClass(long studentId, long classId) {
        final User user = userService.findById(studentId)
                .orElseThrow();
        final Class schoolClass = classRepository.findById(classId)
                .orElseThrow();

        schoolClass.getStudents()
                .add(user);

        return modelMapper.map(classRepository.save(schoolClass), ClassServiceModel.class);
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

    @Override
    public void addSubject(long classId, long subjectId) {
        final Class schoolClass = classRepository.findById(classId)
                .orElseThrow();

        final Subject subject = subjectService.findById(subjectId)
                .orElseThrow();

        schoolClass.getSubjects()
                .add(subject);
        
        classRepository.save(schoolClass);
    }

    @Override
    public void removeSubject(long classId, long subjectId) {
        final Class schoolClass = classRepository.findById(classId)
                .orElseThrow();

        final Subject subject = subjectService.findById(subjectId)
                .orElseThrow();

        schoolClass.getSubjects()
                .remove(subject);
        
        classRepository.save(schoolClass);
    }

    @Override
    public ClassServiceModel updateStudentClass(long studentId, @Valid ClassBindingModel classBindingModel) {
        final Map<Long, Set<User>> classWithStudents = classRepository.findAll()
                .stream()
                .collect(toMap(Class::getId, Class::getStudents));

        final long currentClassId = classWithStudents.entrySet()
                .stream()
                .filter(entry -> entry.getValue()
                        .stream()
                        .map(User::getId)
                        .collect(toList())
                        .contains(studentId))
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();

        unenrollStudentFromClass(studentId, currentClassId);

        final Class newClass = classRepository
                .findByGradeAndGroup(classBindingModel.getGrade(), classBindingModel.getGroup())
                .orElseThrow();

        return enrollStudentInClass(studentId, newClass.getId());
    }
}