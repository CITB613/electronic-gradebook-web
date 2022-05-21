package bg.nbu.gradebook.services.users;

import bg.nbu.gradebook.domain.entities.Role;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Optional;

import static bg.nbu.gradebook.domain.entities.Roles.ROLE_PRINCIPAL;
import static java.util.Collections.singleton;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel register(CreateUserBindingModel userData) {
        if (userRepository.findByUsername(userData.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }

        User user = this.modelMapper.map(userData, User.class);
        user.setPassword(passwordEncoder.encode(userData.getPassword()));

        user = userRepository.save(user);

        return this.mapToUserServiceModel(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void promoteToPrincipal(User user) {
        user.setAuthorities(singleton(new Role(ROLE_PRINCIPAL.getRole())));
        userRepository.saveAndFlush(modelMapper.map(user, User.class));
    }

    @Override
    public User update(long userId, User userModel) {
        final User user = userRepository.findById(userId)
                .orElseThrow();

        if (isNotBlank(userModel.getFirstName())) {
            user.setFirstName(userModel.getFirstName());
        }

        if (isNotBlank(userModel.getLastName())) {
            user.setLastName(userModel.getLastName());
        }

        if (userModel.getBirthDate() != null) {
            user.setBirthDate(userModel.getBirthDate());
        }

        return userRepository.save(userModel);
    }
    
    public UserServiceModel mapToUserServiceModel(final User user) {
        return modelMapper.map(user, UserServiceModel.class);
    }
}
