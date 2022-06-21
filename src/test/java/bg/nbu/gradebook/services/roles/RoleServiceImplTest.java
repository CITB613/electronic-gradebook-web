package bg.nbu.gradebook.services.roles;

import static bg.nbu.gradebook.domain.entities.Roles.ROLE_ADMIN;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Role;
import bg.nbu.gradebook.domain.models.service.RoleServiceModel;
import bg.nbu.gradebook.repositories.RoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Mock
    private Mapper mapperMock;

    @Mock
    private RoleServiceModel roleServiceModelMock;

    @Mock
    private Role roleMock;

    @BeforeEach
    public void setUp() {
        lenient().when(mapperMock.map(roleMock, RoleServiceModel.class))
                .thenReturn(roleServiceModelMock);

        lenient().when(roleRepositoryMock.findAll())
                .thenReturn(singletonList(roleMock));

        lenient().when(roleServiceModelMock.getAuthority())
                .thenReturn(ROLE_ADMIN.getRole());
    }

    @Test
    void testFindAllRolesNoRolesPresent() {
        when(roleRepositoryMock.findAll()).thenReturn(emptyList());

        assertThat(roleService.findAllRoles(), empty());
    }

    @Test
    void testFindAllRoles() {
        assertThat(roleService.findAllRoles(), contains(roleServiceModelMock));
    }

    @Test
    void testFindByAuthorityNonExistingAuthority() {
        when(roleRepositoryMock.findAll()).thenReturn(emptyList());

        assertThrows(NoSuchElementException.class, () -> roleService.findByAuthority(ROLE_ADMIN.getRole()));
    }

    @Test
    void testFindByAuthority() {
        assertThat(roleService.findByAuthority(ROLE_ADMIN.getRole()), equalTo(roleServiceModelMock));
    }
}
