package uet.ppvan.mangareader.mappers;

import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;

@Service
public class ProfileMapper {
    public Profile buildProfileEntity(User user, ProfileRequest request) {
        Profile profile = new Profile();
        profile.setAvatar(request.avatar());
        profile.setCover(request.cover());
        profile.setBio(request.bio());

        profile.setUser(user); // will be set by others, which has authentication context

        return profile;
    }

    public ProfileRequest buildProfileDTO(Profile profile) {
        return new ProfileRequest(
            profile.getAvatar(),
            profile.getCover(),
            profile.getBio()

        );
    }
}
