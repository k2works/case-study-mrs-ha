package mrs.application.domain.model.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * パスワード
 */
@Value
@Getter
@AllArgsConstructor
public class Password {
    String value;
}
