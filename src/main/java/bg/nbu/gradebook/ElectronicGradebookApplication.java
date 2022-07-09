package bg.nbu.gradebook;

import static bg.nbu.gradebook.domain.entities.Roles.ROLE_ADMIN;
import static java.time.LocalDate.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import bg.nbu.gradebook.domain.entities.Roles;
import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.services.classes.ClassService;
import bg.nbu.gradebook.services.users.UserService;

@SpringBootApplication
public class ElectronicGradebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicGradebookApplication.class, args);
    }

    @Bean
    @Autowired
    ApplicationRunner initialize(UserService userService, ClassService classService) {
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
            
            
            classService.create(ClassBindingModel.builder()
                    .grade(1)
                    .group("a")
                    .build());
        };
    }
}
