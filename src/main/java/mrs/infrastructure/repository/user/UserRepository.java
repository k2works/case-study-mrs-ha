package mrs.infrastructure.repository.user;

import mrs.infrastructure.persistence.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity, String> {
}
