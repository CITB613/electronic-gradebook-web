package bg.nbu.gradebook.services.schools;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.SchoolBindingModel;
import bg.nbu.gradebook.repositories.SchoolRepository;
import bg.nbu.gradebook.services.users.UserService;

@ExtendWith(MockitoExtension.class)
class SchoolServiceImplTest {
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final long SCHOOL_ID = 1;
    private static final long USER_ID = 2;

    @InjectMocks
    private SchoolServiceImpl schoolService;

    @Mock
    private SchoolRepository schoolRepositoryMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private Mapper modelMapperMock;

    @Mock
    private School schoolMock;

    @Mock
    private SchoolBindingModel schooldBindingModelMock;

    @Mock
    private User userMock;

    @BeforeEach
    void setUp() {
        lenient().when(schoolRepositoryMock.findById(anyLong()))
                .thenReturn(of(schoolMock));
        lenient().when(userServiceMock.findById(anyLong()))
                .thenReturn(of(userMock));
        lenient().when(schoolRepositoryMock.findByName(anyString()))
                .thenReturn(empty());

        lenient().when(modelMapperMock.map(schooldBindingModelMock, School.class))
                .thenReturn(schoolMock);
        lenient().when(schoolRepositoryMock.save(schoolMock))
                .thenReturn(schoolMock);

        lenient().when(schooldBindingModelMock.getAddress())
                .thenReturn(ADDRESS);
        lenient().when(schooldBindingModelMock.getName())
                .thenReturn(NAME);
    }

    @Test
    void testRegisterThrowsExceptionWhenExistingSchool() {
        when(schoolRepositoryMock.findByName(anyString())).thenReturn(of(schoolMock));

        assertThrows(IllegalArgumentException.class, () -> schoolService.registerSchool(schooldBindingModelMock));
    }

    @Test
    void testRegisterThrowsExceptionWhenUserNotFound() {
        when(userServiceMock.findById(anyLong())).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> schoolService.registerSchool(schooldBindingModelMock));
    }

    @Test
    void testRegister() {
        assertThat(schoolService.registerSchool(schooldBindingModelMock), equalTo(schoolMock));

        verify(modelMapperMock).map(schooldBindingModelMock, School.class);
        verify(schoolMock).setPrincipal(userMock);
    }

    @Test
    void testSetPrincipalThrowsExceptionWhenSchoolNotFound() {
        when(schoolRepositoryMock.findById(anyLong())).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> schoolService.setPrincipal(SCHOOL_ID, USER_ID));

    }

    @Test
    void testSetPrincipalThrowsExceptionWhenUserNotFound() {
        when(userServiceMock.findById(anyLong())).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> schoolService.setPrincipal(SCHOOL_ID, USER_ID));
    }

    @Test
    void testSetPrincipal() {
        schoolService.setPrincipal(SCHOOL_ID, USER_ID);

        verify(userServiceMock).promoteToPrincipal(userMock);
        verify(schoolMock).setPrincipal(userMock);
        verify(schoolRepositoryMock).save(schoolMock);
    }

    @Test
    void testFindAll() {
        when(schoolRepositoryMock.findAll()).thenReturn(singletonList(schoolMock));

        assertThat(schoolService.findAll(), hasItem(schoolMock));
    }

    @Test
    void testUpdate() {
        schoolService.update(SCHOOL_ID, schooldBindingModelMock);

        verify(schoolMock).setAddress(ADDRESS);
        verify(schoolMock).setName(NAME);
        verify(schoolRepositoryMock).save(schoolMock);
    }

    @Test
    void testUpdateThrowsExceptionWhenSchoolNotFound() {
        when(schoolRepositoryMock.findById(anyLong())).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> schoolService.update(SCHOOL_ID, schooldBindingModelMock));
    }
}
