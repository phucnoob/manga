package uet.ppvan.mangareader.views;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.LoginForm;
import uet.ppvan.mangareader.dtos.PasswordResetForm;
import uet.ppvan.mangareader.dtos.RegisterForm;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.security.SecurityUser;
import uet.ppvan.mangareader.services.impl.FormUserService;
import uet.ppvan.mangareader.utils.ValueMapper;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserViews {

    private final FormUserService userService;

    private final ValueMapper valueMapper;

    @GetMapping("/login")
    public String loginForm(
        Model model
    ) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm("", ""));
        }

        return "user/login";
    }

    @GetMapping("/profile")
    @ResponseBody
    public Object profileForm(@AuthenticationPrincipal SecurityUser user) {

        Profile profile = user.getProfile();

        return valueMapper.objectAsJson(profile);
    }

    @GetMapping("/delete")
    public String deactivateAccountForm() {
        return "user/deactivate";
    }

    @PostMapping("/delete")
    public String deactivateAccount(
        HttpSession session,
        @RequestParam String username,
        @AuthenticationPrincipal SecurityUser user
    ) {
        if (!username.equals(user.getUsername())) {
            return "index";
        }

        log.info("Delete user account: {}", username);

        userService.deleteUser(username);

        return "redirect:user/logout";
    }

    @PostMapping("/login-error")
    public String loginError(
        @Valid @ModelAttribute("loginForm") LoginForm loginForm,
        Model model
    ) {
        model.addAttribute("loginError", true);
        if (userService.validCredentials(loginForm)) {
            model.addAttribute("errorMessage", "Email chưa được xác thực");
        } else {
            model.addAttribute("errorMessage", "Tên người dùng hoặc mật khẩu không chính xác");
        }

        return "user/login";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {

        log.debug("Begin UserView#registerForm");

        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm("", "", ""));
        }

        log.debug("End UserView#registerForm");

        return "user/register";
    }

    @PostMapping("/register")
    public String handleRegisterForm(
        Model model,
        @ModelAttribute("registerForm") @Valid RegisterForm form,
        BindingResult result
    ) {
        log.info("Register Form submitted with data. {}", valueMapper.objectAsJson(form));

        if (result.hasErrors()) {
            model.addAttribute("registerError", true);
            return "user/register";
        }

        var createdUser = userService.createUser(form);
        userService.sendVerifiedEmail(form, "/user/verify");

        log.info("User created. {}", valueMapper.objectAsJson(createdUser));

        return "redirect:login";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam String token) {
        userService.verifyAccount(token);

        return "redirect:login";
    }


    @GetMapping("/password-reset-request")
    public String passwordResetRequestForm() {
        return "user/password-reset-request";
    }

    @PostMapping("/password-reset-request")
    public String passwordResetRequest(@RequestParam String email, Model model) {

        userService.sendPasswordResetEmail(email, "/user/password-reset");
        String message = "Email đặt lại mật khẩu đã được gửi. Kiểm tra mail của bạn";

        model.addAttribute(message);

        return "user/password-reset-request";
    }

    @GetMapping("/password-reset")
    public String resetPasswordForm(@RequestParam String token, Model model) {

        var email = userService.parseEmailFromPasswordResetToken(token);

        model.addAttribute("requestedEmail", email);
        model.addAttribute("passwordResetForm", new PasswordResetForm(""));

        return "user/password-reset";
    }

    @PostMapping("/password-reset")
    public String handleResetPassword(
        @Valid @ModelAttribute("passwordResetForm") PasswordResetForm form,
        @RequestParam("email") String email,
        Model model
    ) {
        userService.resetPassword(form, email);
        model.addAttribute("message", "Mật khẩu đã đặt lại thành công.");

        return "redirect:login";
    }
}
