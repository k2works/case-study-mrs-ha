package mrs.application.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * ユーザーID
 */
@Value
@Getter
@AllArgsConstructor
public class UserId {
    String value;
}
