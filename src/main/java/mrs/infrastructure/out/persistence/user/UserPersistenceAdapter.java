package mrs.infrastructure.out.persistence.user;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.User;
import mrs.application.port.out.UserPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public Optional<User> findById(String userId) {
        Optional<UserJpaEntity> userJpaEntity = userRepository.findById(userId);
        return Optional.of(userMapper.mapToDomainEntity(userJpaEntity.orElseThrow()));
    }

    public void save(User user) {
        userRepository.save(userMapper.mapToJpaEntity(user));
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
