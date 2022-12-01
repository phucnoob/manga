package uet.ppvan.mangareader.services;

import jakarta.validation.Valid;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;


public interface ProfileService {
    Profile createProfile(@Valid ProfileRequest request, User user);

    Profile updateProfile(@Valid ProfileRequest request, User user);

    ProfileRequest getUserProfile(User user);
}
