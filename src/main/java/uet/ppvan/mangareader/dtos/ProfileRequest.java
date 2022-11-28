package uet.ppvan.mangareader.dtos;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;


/**
 * DTO to update the {@link uet.ppvan.mangareader.models.Profile}
 * @param avatar
 * @param cover
 * @param bio
 */
public record ProfileRequest(
    @URL(message = "Avatar has to be a valid URL")
    String avatar,

    @URL(message = "Cover has to be a valid URL")
    String cover,

    @NotNull(message = "Bio can't be null")
    String bio) {
    public ProfileRequest() {
        this("", "", "");
    }

}
