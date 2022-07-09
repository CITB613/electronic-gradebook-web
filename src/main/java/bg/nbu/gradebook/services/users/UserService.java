package bg.nbu.gradebook.services.users;

import java.util.List;
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


    List<User> findAllPrincipals();

    List<UserServiceModel> findAll();

    void setRole(User user, Roles role);
}
