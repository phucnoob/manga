package uet.ppvan.mangareader.views;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uet.ppvan.mangareader.dtos.LoginForm;
import uet.ppvan.mangareader.dtos.RegisterForm;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserViews {

//    private final UserAuthService authService;


    @GetMapping("/login")
    public String loginForm(
        Model model
    ) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm("", ""));
        }

        return "user/login";
    }


    @PostMapping("/login-error")
    public String loginError(
        @Valid @ModelAttribute("loginForm") LoginForm loginForm,
        BindingResult bindingResult,
        Model model
    ) {

        model.addAttribute("loginError", true);
        model.addAttribute("message", "usename hoặc mật khẩu không chính xác.");

        return "user/login";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {

        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm("", "", ""));
        }

        return "user/register";
    }
}
