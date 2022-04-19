package bg.nbu.gradebook.services.roles;

import java.util.Set;

import bg.nbu.gradebook.domain.models.service.RoleServiceModel;

public interface RoleService {
    Set<RoleServiceModel> findAllRoles();
}