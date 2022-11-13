package uet.ppvan.mangareader.users;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotBlank(message = "Email can't be blank")
    @Email(
        message = "Email is not valid",
        regexp = "(([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)?)(@)([a-zA-Z]+.[A-Za-z]+\\.?([a-zA-Z0-9]+)\\.?([a-zA-Z0-9]+))"
    )
    private String email;


    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "Password should at least 8 characters, include letters, numbers, special symbols"
    )
    private String password;
    private String confirmPassword;
}
