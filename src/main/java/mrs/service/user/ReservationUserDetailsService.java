package mrs.service.user;

import mrs.domain.model.User;
import mrs.infrastructure.persistence.user.UserPersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 認証サービス
 */
@Service
public class ReservationUserDetailsService implements UserDetailsService {
    @Autowired
    UserPersistenceAdapter userRepository;

    /**
     * ユーザー情報を取得する
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username + " is not found."));
        return new ReservationUserDetails(user);
    }
}
