package mrs.infrastructure.out.persistence.auth;

import mrs.application.domain.model.auth.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToDomainEntity(UserJpaEntity userJpaEntity) {
        return User.of(
                userJpaEntity.getUserId(),
                userJpaEntity.getPassword(),
                userJpaEntity.getFirstName(),
                userJpaEntity.getLastName(),
                userJpaEntity.getRoleName());
    }

    public UserJpaEntity mapToJpaEntity(User user) {
        return new UserJpaEntity(
                user.getUserId().getValue(),
                user.getPassword().getValue(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getRoleName());
    }
}
