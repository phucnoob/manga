package uet.ppvan.mangareader.users.auth;


import javax.validation.constraints.NotBlank;

public record AuthRequest(

    @NotBlank
    String username,

    @NotBlank
    String password
) {
}
