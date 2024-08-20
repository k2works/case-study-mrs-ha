package mrs.infrastructure.out.persistence.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mrs.application.domain.model.auth.RoleName;

import java.io.Serializable;

/**
 * ユーザー
 */
@Entity
@Table(name = "usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity implements Serializable {
    @Id
    private String userId;

    private String password;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
