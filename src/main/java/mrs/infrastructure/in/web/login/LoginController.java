package mrs.infrastructure.in.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン画面
 */
@Controller
public class LoginController {
    /**
     * ログインフォーム表示
     */
    @RequestMapping("loginForm")
    String loginForm() {
        return "login/loginForm";
    }
}
