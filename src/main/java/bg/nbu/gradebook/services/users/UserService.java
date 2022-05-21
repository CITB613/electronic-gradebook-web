package bg.nbu.gradebook.services.users;

import java.util.Optional;

import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;

public interface UserService {
    void register(CreateUserBindingModel userData);

    Optional<User> findByUsername(String username);

    Optional<User> findById(long id);

    void promoteToPrincipal(User user);

    void delete(long id);

    User update(long userId, User user);

    UserServiceModel mapToUserServiceModel(User user);
}
