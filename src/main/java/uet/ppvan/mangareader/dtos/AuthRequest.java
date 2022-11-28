package uet.ppvan.mangareader.dtos;


import jakarta.validation.constraints.NotBlank;


/**
 * DTO object send as {@link org.springframework.web.bind.annotation.RequestBody @RequestBody} to login.
 *
 * @param username
 * @param password
 */
public record AuthRequest(

    @NotBlank
    String username,

    @NotBlank
    String password
) {
}
