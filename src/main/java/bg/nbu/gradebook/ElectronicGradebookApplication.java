package bg.nbu.gradebook;

import static bg.nbu.gradebook.domain.entities.Roles.ROLE_ADMIN;
import static bg.nbu.gradebook.domain.entities.Roles.ROLE_STUDENT;
import static bg.nbu.gradebook.domain.entities.Roles.ROLE_TEACHER;
import static java.time.LocalDate.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import bg.nbu.gradebook.domain.models.service.SubjectServiceModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.services.classes.ClassService;
import bg.nbu.gradebook.services.subjects.SubjectService;
import bg.nbu.gradebook.services.users.UserService;

@SpringBootApplication
public class ElectronicGradebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicGradebookApplication.class, args);
    }

    @Bean
    @Autowired
    ApplicationRunner initialize(UserService userService, ClassService classService, SubjectService subjectService) {
        return args -> {
            if (!userService.findByUsername("admin")
                    .isEmpty()) {
                return;
            }

            userService.register(CreateUserBindingModel.builder()
                    .username("admin")
                    .password("pass")
                    .firstName("adminFirstName")
                    .lastName("adminLastName")
                    .birthDate(now())
                    .authority(ROLE_ADMIN.getRole())
                    .build());

            final UserServiceModel teacher = userService.register(CreateUserBindingModel.builder()
                    .username("teacher1")
                    .password("teacher1")
                    .firstName("teacher First Name")
                    .lastName("teacher last name")
                    .birthDate(now())
                    .authority(ROLE_TEACHER.getRole())
                    .build());

            final UserServiceModel student = userService.register(CreateUserBindingModel.builder()
                    .username("student1")
                    .password("student1")
                    .firstName("student First Name")
                    .lastName("student last name")
                    .birthDate(now())
                    .authority(ROLE_STUDENT.getRole())
                    .build());

            final SubjectServiceModel subjectServiceModel = subjectService.createSubject(SubjectServiceModel.builder()
                    .name("Subject 1")
                    .teacherId(teacher.getId())
                    .build());

            final ClassServiceModel classServiceModel = classService.create(ClassBindingModel.builder()
                    .grade(1)
                    .group("a")
                    .build());

            classService.addSubject(classServiceModel.getId(), subjectServiceModel.getId());
            classService.enrollStudentInClass(student.getId(), classServiceModel.getId());
        };
    }
}
