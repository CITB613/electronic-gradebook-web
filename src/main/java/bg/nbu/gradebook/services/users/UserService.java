package bg.nbu.gradebook.services.users;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import bg.nbu.gradebook.domain.models.service.UserServiceModel;

public interface UserService extends UserDetailsService {
    Optional<UserServiceModel> findByUsername(String username);

    Optional<UserServiceModel> findById(long id);
    
    void promoteToPrincipal(UserServiceModel userServiceModel);

    void delete(long id);
}
