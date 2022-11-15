package uet.ppvan.mangareader.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.services.UserAuthService;
import uet.ppvan.mangareader.utils.ResponseFactory;

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
