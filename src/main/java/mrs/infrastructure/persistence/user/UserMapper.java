package mrs.infrastructure.persistence.user;

import mrs.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToDomainEntity(UserJpaEntity userJpaEntity) {
        return new User(
                userJpaEntity.getUserId(),
                userJpaEntity.getPassword(),
                userJpaEntity.getFirstName(),
                userJpaEntity.getLastName(),
                userJpaEntity.getRoleName());
    }

    public UserJpaEntity mapToJpaEntity(User user) {
        return new UserJpaEntity(
                user.getUserId(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoleName());
    }
}
