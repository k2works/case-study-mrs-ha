package mrs.infrastructure.persistence;

import mrs.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    User mapToDomainEntity(UserJpaEntity userJpaEntity) {
        return new User(
                userJpaEntity.getUserId(),
                userJpaEntity.getPassword(),
                userJpaEntity.getFirstName(),
                userJpaEntity.getLastName(),
                userJpaEntity.getRoleName());
    }

    UserJpaEntity mapToJpaEntity(User user) {
        return new UserJpaEntity(
                user.getUserId(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoleName());
    }
}
