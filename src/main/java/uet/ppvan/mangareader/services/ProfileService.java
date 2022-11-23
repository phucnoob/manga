package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;

import javax.validation.Valid;

public interface ProfileService {
    Profile createProfile(@Valid ProfileRequest request, User user);

    Profile updateProfile(@Valid ProfileRequest request, User user);

    ProfileRequest getUserProfile(User user);
}
