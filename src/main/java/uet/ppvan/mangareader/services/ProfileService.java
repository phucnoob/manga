package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.models.User;

public interface ProfileService {
    void createProfile(ProfileRequest request, Integer userId);

    void updateProfile(ProfileRequest request, Integer userId);

    ProfileRequest getUserProfile(User user);
}
