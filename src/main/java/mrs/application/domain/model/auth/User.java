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

    public static User of(String userId, String password, String firstName, String lastName, RoleName roleName) {
        return new User(new UserId(userId), new Password(password), new Name(firstName, lastName), roleName);
    }
}
