package bg.nbu.gradebook.controllers;

import static java.util.Collections.singletonList;
import static java.util.Map.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.CreateUserBindingModel;
import bg.nbu.gradebook.domain.models.bindings.UserPasswordBindingModel;
import bg.nbu.gradebook.services.users.UserService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    private static final long ID = 1;
    private static final String TOKEN_VALUE = "tokenValue";
    private static final String AUTHORITY = "authority";
    private static final String USERNAME = "username";

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManagerMock;

    @Mock
    private JwtEncoder jwtEncoderMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private CreateUserBindingModel createUserBindingModelMock;

    @Mock
    private UserPasswordBindingModel userPasswordBindingModelMock;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private User userMock;

    @Mock
    private GrantedAuthority grantedAuthorityMock;

    @Mock
    private Jwt jwtMock;

    @BeforeEach
    void setUp() {
        lenient().when(authenticationManagerMock.authenticate(any()))
                .thenReturn(authenticationMock);

        lenient().when(authenticationMock.getPrincipal())
                .thenReturn(userMock);

        lenient().doReturn(singletonList(grantedAuthorityMock))
                .when(authenticationMock)
                .getAuthorities();
        lenient().when(grantedAuthorityMock.getAuthority())
                .thenReturn(AUTHORITY);

        lenient().when(userMock.getId())
                .thenReturn(ID);
        lenient().when(userMock.getUsername())
                .thenReturn(USERNAME);

        lenient().when(jwtEncoderMock.encode(any()))
                .thenReturn(jwtMock);
        lenient().when(jwtMock.getTokenValue())
                .thenReturn(TOKEN_VALUE);

    }

    @Test
    void testLoginThrowsAuthenticationException() {
        doThrow(BadCredentialsException.class).when(authenticationManagerMock)
                .authenticate(any());

        assertThat(authController.login(userPasswordBindingModelMock), equalTo(status(UNAUTHORIZED).build()));
    }

    @Test
    void testLogin() {
        assertThat(authController.login(userPasswordBindingModelMock),
                equalTo(ok().body(of("AccessToken", TOKEN_VALUE))));
    }

    @Test
    void testRegister() {
        authController.register(createUserBindingModelMock);

        verify(userServiceMock).register(createUserBindingModelMock);
    }
}
