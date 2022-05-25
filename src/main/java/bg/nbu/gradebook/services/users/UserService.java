package bg.nbu.gradebook.services.users;

import java.util.Optional;

import bg.nbu.gradebook.domain.entities.Roles;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;

public interface UserService {
    UserServiceModel register(CreateUserBindingModel userData);

    Optional<User> findByUsername(String username);

    Optional<User> findById(long id);

    void promoteToPrincipal(User user);

    void delete(long id);

    User update(long userId, User user);

    UserServiceModel mapToUserServiceModel(User user);

    void setRole(UserServiceModel userServiceModel, Roles admin);
}
