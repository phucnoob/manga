package uet.ppvan.mangareader.services.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.mappers.ProfileMapper;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.ProfileRepository;
import uet.ppvan.mangareader.services.ProfileService;

@Service
@RequiredArgsConstructor
@Validated
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;


    @Override
    public Profile createProfile(@Valid ProfileRequest request, User user) {
        return profileMapper.buildProfileEntity(user, request);
    }

    @Override
    public Profile updateProfile(@Valid ProfileRequest request, User user) {
        Profile profile = profileRepository.findProfileByUser(user)
                              .orElseThrow(ResourceNotFound::userNotFound);
        profile.setAvatar(request.avatar());
        profile.setCover(request.cover());
        profile.setBio(request.bio());
        profile.setCover(request.cover());
        profile.setUser(user);

        return profile;
    }

    @Override
    public ProfileRequest getUserProfile(User user) {
        if (user == null) {
            throw ResourceNotFound.userNotFound();
        }

        var profile = profileRepository.findProfileByUser(user)
                          .orElseThrow(ResourceNotFound::userNotFound);

        return profileMapper.buildProfileDTO(profile);
    }
}
