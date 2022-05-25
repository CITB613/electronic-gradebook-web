package bg.nbu.gradebook;

import static bg.nbu.gradebook.domain.entities.Roles.ROLE_ADMIN;
import static java.time.LocalDate.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.services.users.UserService;

@SpringBootApplication
public class ElectronicGradebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicGradebookApplication.class, args);
    }

    @Bean
    @Autowired
    ApplicationRunner initialize(UserService userService) {
        return args -> {
            if(!userService.findByUsername("admin").isEmpty()) {
                return;
            }
            
            UserServiceModel user = userService.register(CreateUserBindingModel.builder()
                    .username("admin")
                    .password("pass")
                    .firstName("adminFirstName")
                    .lastName("adminLastName")
                    .birthDate(now())
                    .build());
            userService.setRole(user, ROLE_ADMIN);
        };
    }
}
