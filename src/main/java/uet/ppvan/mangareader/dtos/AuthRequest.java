package uet.ppvan.mangareader.dtos;


import javax.validation.constraints.NotBlank;

public record AuthRequest(

    @NotBlank
    String username,

    @NotBlank
    String password
) {
}
