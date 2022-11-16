package uet.ppvan.mangareader.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.models.User;

public interface UserService {
    User findUserByUsername(String username) throws UsernameNotFoundException;

    void createUser(UserRequest request);

    void deleteUser(Integer userId);

    void createProfile(ProfileRequest request);

    void updateProfile(ProfileRequest request);

    ProfileRequest getProfile();
}
