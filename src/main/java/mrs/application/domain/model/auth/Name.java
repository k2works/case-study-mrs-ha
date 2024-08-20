package mrs.application.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 名前
 */
@Value
@Getter
@AllArgsConstructor
public class Name {
    String firstName;
    String lastName;
}
