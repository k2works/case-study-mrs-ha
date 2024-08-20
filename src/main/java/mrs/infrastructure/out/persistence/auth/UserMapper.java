package mrs.infrastructure.out.persistence.auth;

import mrs.application.domain.model.auth.Name;
import mrs.application.domain.model.auth.Password;
import mrs.application.domain.model.auth.User;
import mrs.application.domain.model.auth.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToDomainEntity(UserJpaEntity userJpaEntity) {
        return new User(
                new UserId(userJpaEntity.getUserId()),
                new Password(userJpaEntity.getPassword()),
                new Name(userJpaEntity.getFirstName(), userJpaEntity.getLastName()),
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
