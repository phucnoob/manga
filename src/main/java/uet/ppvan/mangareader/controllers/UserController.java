package uet.ppvan.mangareader.controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.services.UserAuthService;
import uet.ppvan.mangareader.services.UserService;
import uet.ppvan.mangareader.utils.ResponseFactory;



@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserAuthService authService;


    @GetMapping("/test")
    public String testUser() {
        return "user";
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid AuthRequest request) {

        return ResponseEntity.ok(ResponseFactory.success(authService.validateUserLogin(request)));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);

        return ResponseFactory.success("Your email is verified, You can login now.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewAccount(@RequestBody @Valid UserRequest request) {
        userService.createUser(request);
        authService.sendVerificationEmail(request.email());
        String message = "Verification email sent, please check your email.";

        return ResponseEntity.ok(ResponseFactory.success("Register successfully.", message));

    }

    @PostMapping("/password-reset-request")
    public ResponseEntity<?> requestResetPassword(
    @Email(regexp = "(([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)?)(@)([a-zA-Z]+.[A-Za-z]+\\.?([a-zA-Z0-9]+)\\.?([a-zA-Z0-9]+))")
    @RequestParam String email
    ) {
        authService.sendPasswordResetEmail(email);

        return ResponseFactory.success("Email sent. Check your mail for reset link");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> handlePasswordResetRequest(
    @RequestParam String token,
    @RequestBody String newPassword
    ) {
        authService.resetPassword(token, newPassword);

        return ResponseFactory.success("Password has been reset. Please login again");
    }
}
