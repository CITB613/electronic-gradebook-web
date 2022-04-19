package bg.nbu.gradebook.services.users;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.repositories.UserRepository;
import bg.nbu.gradebook.services.roles.RoleService;

@Service
public class UserServiceImpl implements UserService {
    public static final String UNABLE_TO_FIND_USER_BY_NAME_MESSAGE = "Unable to find user by name.";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UNABLE_TO_FIND_USER_BY_NAME_MESSAGE));
    }

    @Override
    public Optional<UserServiceModel> findByUsername(String username) {
        final Optional<User> user = userRepository.findByUsername(username);

        return user.isPresent() ? of(modelMapper.map(user.get(), UserServiceModel.class)) : empty();
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
