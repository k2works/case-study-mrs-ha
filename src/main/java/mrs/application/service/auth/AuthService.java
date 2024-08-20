package mrs.application.service.auth;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.auth.User;
import mrs.application.port.out.UserPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 認証サービス
 */
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserPort userPort;

    /**
     * ユーザー情報を取得する
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userPort.findById(username).orElseThrow(() -> new UsernameNotFoundException(username + " is not found."));
        return new AuthUserDetails(user);
    }
}
