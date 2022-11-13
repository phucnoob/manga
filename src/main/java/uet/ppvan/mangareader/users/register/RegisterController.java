package uet.ppvan.mangareader.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.ppvan.mangareader.comons.ResponseFactory;
import uet.ppvan.mangareader.users.UserRequest;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RegisterController {

    private final UserRegisterService service;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewAccount(@RequestBody @Valid UserRequest request) {
        service.createUser(request);

        return ResponseEntity.ok(ResponseFactory.success("Register successfully.", ""));

    }

}
