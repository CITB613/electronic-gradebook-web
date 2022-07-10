package bg.nbu.gradebook.services;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {
    private static final String USERNAME = "username";

    @InjectMocks
    private UserAuthService userAuthService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private User userMock;

    @Test
    void testLoadUserByUsernameThrowsExceptionWhenUserNotFound() {
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(empty());

        assertThrows(UsernameNotFoundException.class, () -> userAuthService.loadUserByUsername(USERNAME));
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(of(userMock));

        assertThat(userAuthService.loadUserByUsername(USERNAME), equalTo(userMock));
    }
}
