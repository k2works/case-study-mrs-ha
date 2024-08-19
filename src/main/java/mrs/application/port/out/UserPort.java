package mrs.application.port.out;

import mrs.application.domain.model.User;

import java.util.Optional;

/**
 * ユーザポート
 */
public interface UserPort {
    Optional<User> findById(String userId);
}
