package uet.ppvan.mangareader.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class RegisterForm {

    @NotBlank(message = "Username can't be blank")
    String username;
    @NotBlank(message = "Email can't be blank")
    @Email(
        message = "Email is not valid",
        regexp = "(([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)?)(@)([a-zA-Z]+.[A-Za-z]+\\.?([a-zA-Z0-9]+)\\.?([a-zA-Z0-9]+))"
    )
    String email;

    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "Password should at least 8 characters; include letters; numbers; special symbols"
    )
    String password;

}