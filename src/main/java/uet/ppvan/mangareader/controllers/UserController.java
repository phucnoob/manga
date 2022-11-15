package uet.ppvan.mangareader.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.ppvan.mangareader.services.UserService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/test")
    public String testUser() {
        return "user";
    }

}
