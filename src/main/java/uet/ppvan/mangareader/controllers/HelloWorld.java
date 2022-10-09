package uet.ppvan.mangareader.controllers;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloWorld {
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Collections.singletonMap("response","Hello world");
    }

    @GetMapping("/set-cookie")
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie", "hello-cookie");
        response.addCookie(cookie);

        return "Cookie setted.";
    }

    @GetMapping("/get-cookie")
    public String getCookie(@CookieValue (name = "cookie", defaultValue = "nothing") String cookie) {
        return cookie;
    }
}
