package mrs.service.user;

import mrs.domain.model.RoleName;
import mrs.domain.model.User;
import mrs.infrastructure.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationUserDetailsServiceTest {

    @Autowired
    ReservationUserDetailsService service;

    @MockBean
    UserRepository repository;

    @Test
    @DisplayName("ユーザが存在する場合、ユーザ情報を返す")
    void loadUserByUsername_WhenUserExists_ReturnsUserDetails() {
        User user = new User();
        user.setUserId("test");
        user.setPassword("pass");
        user.setFirstName("first");
        user.setLastName("last");
        user.setRoleName(RoleName.USER);

        when(repository.findById(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = service.loadUserByUsername("test");

        Assertions.assertEquals(userDetails.getUsername(), "test");

    }

    @Test
    @DisplayName("ユーザが存在しない場合、UsernameNotFoundExceptionをスローする")
    void loadUserByUsername_WhenUserDoesNotExist_ThrowsUsernameNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("test"));
    }
}
