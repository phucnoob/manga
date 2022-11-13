package uet.ppvan.mangareader.users;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "username can't be blank")
    private String username;

    @NotBlank(message = "email can't be blank")
    @Email(message = "Not a valid email", regexp = "(([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)([\\.\\-_]?)([a-zA-Z0-9]+)?)(@)([a-zA-Z]+.[A-Za-z]+\\.?([a-zA-Z0-9]+)\\.?([a-zA-Z0-9]+))")
    private String email;


    private String password;
    private String confirmPassword;
}
