package bg.nbu.gradebook.services.users;

import static bg.nbu.gradebook.domain.entities.Roles.ROLE_ADMIN;
import static bg.nbu.gradebook.domain.entities.Roles.ROLE_PRINCIPAL;
import static bg.nbu.gradebook.domain.entities.Roles.ROLE_STUDENT;
import static bg.nbu.gradebook.domain.entities.Roles.ROLE_TEACHER;
import static java.time.LocalDate.now;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.util.Lists.list;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import javax.validation.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Role;
import bg.nbu.gradebook.domain.entities.Roles;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.service.RoleServiceModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.repositories.UserRepository;
import bg.nbu.gradebook.services.roles.RoleService;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String PASSWORD = "password";
    private static final LocalDate BIRTH_DATE = now();
    private static final String LAST_NAME = "lastName";
    private static final String FIRST_NAME = "firstName";
    private static final long ID = 1;
    private static final String USERNAME = "username";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private Mapper modelMapperMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private RoleService roleServiceMock;

    @Mock
    private User userMock;

    @Mock
    private UserServiceModel userServiceModelMock;

    @Mock
    private RoleServiceModel roleServiceModelMock;

    @Mock
    private Role roleMock;

    @Mock
    private CreateUserBindingModel createUserBindingModelMock;

    @BeforeEach
    void setUp() {
        lenient().when(userRepositoryMock.findById(ID))
                .thenReturn(of(userMock));
        lenient().when(userRepositoryMock.findAll())
                .thenReturn(list(userMock));

        lenient().when(userMock.getFirstName())
                .thenReturn(FIRST_NAME);
        lenient().when(userMock.getLastName())
                .thenReturn(LAST_NAME);
        lenient().when(userMock.getBirthDate())
                .thenReturn(BIRTH_DATE);
        lenient().when(userMock.getAuthorities())
                .thenReturn(singleton(roleMock));

        lenient().when(modelMapperMock.map(userServiceModelMock, User.class))
                .thenReturn(userMock);
        lenient().when(roleServiceMock.findByAuthority(anyString()))
                .thenReturn(roleServiceModelMock);
        lenient().when(modelMapperMock.map(roleServiceModelMock, Role.class))
                .thenReturn(roleMock);
        lenient().when(modelMapperMock.map(createUserBindingModelMock, User.class))
                .thenReturn(userMock);
        lenient().when(modelMapperMock.map(userMock, UserServiceModel.class))
                .thenReturn(userServiceModelMock);
        lenient().when(modelMapperMock.mapCollection(any(), eq(UserServiceModel.class)))
                .thenReturn(list(userServiceModelMock));

        lenient().when(userRepositoryMock.save(userMock))
                .thenReturn(userMock);

        lenient().when(createUserBindingModelMock.getUsername())
                .thenReturn(USERNAME);
        lenient().when(createUserBindingModelMock.getPassword())
                .thenReturn(PASSWORD);
        lenient().when(createUserBindingModelMock.getAuthority())
                .thenReturn(ROLE_ADMIN.getRole());
    }

    @Test
    void testRegisterThrowsExceptionWhenUserAlreadyExists() {
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(of(userMock));

        assertThrows(ValidationException.class, () -> userService.register(createUserBindingModelMock));
    }

    @Test
    void testRegister() {
        when(passwordEncoderMock.encode(PASSWORD)).thenReturn(PASSWORD);

        assertThat(userService.register(createUserBindingModelMock), equalTo(userServiceModelMock));

        verify(userMock).setPassword(PASSWORD);
        verify(userRepositoryMock).save(userMock);
        verify(modelMapperMock).map(userMock, UserServiceModel.class);
    }

    @Test
    void testFindByUsername() {
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(of(userMock));

        assertThat(userService.findByUsername(USERNAME), equalTo(of(userMock)));
    }

    @Test
    void testFindById() {
        assertThat(userService.findById(ID), equalTo(of(userMock)));
    }

    @Test
    void testDelete() {
        userService.delete(ID);

        verify(userRepositoryMock).deleteById(ID);
    }

    @Test
    void testPromoteToPrincipal() {
        userService.promoteToPrincipal(userMock);

        verify(userMock).setAuthorities(any());
        verify(userRepositoryMock).save(userMock);
    }

    @Test
    void testUpdateThrowsExceptionWhenUserNotFound() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> userService.update(ID, userMock));
    }

    @Test
    void testUpdate() {
        assertThat(userService.update(ID, userMock), equalTo(userMock));

        verify(userMock).setFirstName(FIRST_NAME);
        verify(userMock).setLastName(LAST_NAME);
        verify(userMock).setBirthDate(BIRTH_DATE);
        verify(userRepositoryMock).save(userMock);
    }

    @Test
    void testSetRole() {
        userService.setRole(userMock, ROLE_PRINCIPAL);

        verify(userMock).setAuthorities(singleton(roleMock));
    }

    @Test
    void testFindAllPrincipals() {
        when(roleMock.getAuthority()).thenReturn(ROLE_PRINCIPAL.getRole());

        assertThat(userService.findAllPrincipals(), equalTo(singletonList(userMock)));
    }

    @Test
    void testFindAll() {
        assertThat(userService.findAll(), contains(userServiceModelMock));
    }

    @Test
    void testFindAllTeachers() {
        when(roleMock.getAuthority()).thenReturn(ROLE_TEACHER.getRole());

        assertThat(userService.findAllTeachers(), equalTo(singletonList(userMock)));
    }
    
    @Test
    void testFindAllStudents() {
        when(roleMock.getAuthority()).thenReturn(ROLE_STUDENT.getRole());

        assertThat(userService.findAllStudents(), equalTo(singletonList(userMock)));
    }
}
