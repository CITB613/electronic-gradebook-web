package bg.nbu.gradebook.services.users;

import static bg.nbu.gradebook.domain.entities.Roles.ROLE_PRINCIPAL;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Role;
import bg.nbu.gradebook.domain.entities.Roles;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.repositories.UserRepository;
import bg.nbu.gradebook.services.roles.RoleService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Mapper modelMapper, PasswordEncoder passwordEncoder,
            RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public UserServiceModel register(CreateUserBindingModel userData) {
        if (userRepository.findByUsername(userData.getUsername())
                .isPresent()) {
            throw new ValidationException("Username exists!");
        }

        User user = this.modelMapper.map(userData, User.class);
        user.setPassword(passwordEncoder.encode(userData.getPassword()));

        return modelMapper.map(userRepository.save(user), UserServiceModel.class);
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
        userRepository.save(user);
    }

    @Override
    public User update(long userId, User userModel) {
        final User user = findById(userId).orElseThrow();

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

    @Override
    public void setRole(UserServiceModel userServiceModel, Roles role) {
        final User user = modelMapper.map(userServiceModel, User.class);
        final Role authority = modelMapper.map(roleService.findByAuthority(role.getRole()), Role.class);
        user.setAuthorities(singleton(authority));

        userRepository.save(user);
    }

    @Override
    public List<User> findAllPrincipals() {
        return userRepository.findAll()
                .stream()
                .filter(this::filterByPrincipalRole)
                .collect(toUnmodifiableList());
    }

    private boolean filterByPrincipalRole(User user) {
        return user.getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .collect(toSet())
                .contains(ROLE_PRINCIPAL.getRole());
    }
}
