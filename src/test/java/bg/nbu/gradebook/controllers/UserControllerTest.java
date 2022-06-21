package bg.nbu.gradebook.controllers;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.UserBindingModel;
import bg.nbu.gradebook.services.users.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final long USER_ID = 1;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @Mock
    private Mapper mapperMock;

    @Mock
    private UserBindingModel userBindingModelMock;

    @Mock
    private User userMock;

    @BeforeEach
    void setUp() {
        lenient().when(mapperMock.map(userBindingModelMock, User.class))
                .thenReturn(userMock);
        lenient().when(userServiceMock.findAllPrincipals())
                .thenReturn(singletonList(userMock));
    }

    @Test
    void testUpdatePersonalDetails() {
        userController.updatePersonalDetails(USER_ID, userBindingModelMock);

        verify(userServiceMock).update(USER_ID, userMock);
    }

    @Test
    void testDelete() {
        userController.delete(USER_ID);

        verify(userServiceMock).delete(USER_ID);
    }

    @Test
    void testFindAllPrincipals() {
        assertThat(userController.findAllPrincipals(), contains(userMock));
    }
}
