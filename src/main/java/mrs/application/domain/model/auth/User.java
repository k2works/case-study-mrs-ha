package mrs.application.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * ユーザー
 */
@Value
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User implements Serializable {
    UserId userId;

    Password password;

    Name name;

    RoleName roleName;
}
