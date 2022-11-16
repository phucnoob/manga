package uet.ppvan.mangareader.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.services.UserAuthService;
import uet.ppvan.mangareader.services.UserService;
import uet.ppvan.mangareader.utils.ResponseFactory;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserAuthService authService;


    @GetMapping("/test")
    public String testUser() {
        return "user";
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {

        var profile = userService.getProfile();

        return ResponseEntity.ok(ResponseFactory.success(profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileRequest request) {

        userService.updateProfile(request);

        return ResponseEntity.ok().body("Ok");
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody AuthRequest request) {

        return ResponseEntity.ok(ResponseFactory.success(authService.validateUserLogin(request)));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerNewAccount(@RequestBody @Valid UserRequest request) {
        userService.createUser(request);

        return ResponseEntity.ok(ResponseFactory.success("Register successfully.", ""));

    }
}
