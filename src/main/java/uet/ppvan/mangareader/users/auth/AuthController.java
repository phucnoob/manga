package uet.ppvan.mangareader.users.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.comons.ResponseFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String jwtToken = authService.validateUserLogin(request);

        return ResponseEntity.ok(ResponseFactory.success(jwtToken));
    }


    @GetMapping("/test")
    public String test() {
        return "Auth ok";
    }
}
