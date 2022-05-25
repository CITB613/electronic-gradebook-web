package bg.nbu.gradebook.services.roles;

import static java.util.stream.Collectors.toUnmodifiableSet;

import java.util.Set;

import org.springframework.stereotype.Service;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.models.service.RoleServiceModel;
import bg.nbu.gradebook.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final Mapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, Mapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> modelMapper.map(role, RoleServiceModel.class))
                .collect(toUnmodifiableSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return findAllRoles().stream()
                .filter(role -> role.getAuthority()
                        .equalsIgnoreCase(authority))
                .findAny()
                .orElseThrow();
    }
}