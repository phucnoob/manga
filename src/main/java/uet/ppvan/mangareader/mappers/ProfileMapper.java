package uet.ppvan.mangareader.mappers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;


@Service
@Validated
public class ProfileMapper {
    public Profile buildProfileEntity(User user, @Valid ProfileRequest request) {

        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User can't be null");
        }

        Profile profile = new Profile();
        profile.setAvatar(request.avatar());
        profile.setCover(request.cover());
        profile.setBio(request.bio());
        profile.setId(0); // zero means no id

        profile.setUser(user);

        return profile;
    }

    public ProfileRequest buildProfileDTO(@Valid Profile profile) {
        return new ProfileRequest(
            profile.getAvatar(),
            profile.getCover(),
            profile.getBio()
        );
    }
}
