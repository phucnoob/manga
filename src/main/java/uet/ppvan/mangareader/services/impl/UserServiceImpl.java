package uet.ppvan.mangareader.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.exceptions.NotAuthenticatedException;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.Role;
import uet.ppvan.mangareader.models.RoleEntity;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.ProfileRepository;
import uet.ppvan.mangareader.repositories.RoleRepository;
import uet.ppvan.mangareader.repositories.UserRepository;
import uet.ppvan.mangareader.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("%s is not found", username)));
    }

    @Override
    public void createUser(UserRequest request) {
        var user = toUserEntity(request);

        // Create a default profile for new user.
        var profileRequest = new ProfileRequest();
        var defaultProfile = toProfileEntity(profileRequest);
        defaultProfile.setUser(user);
        user.setProfile(defaultProfile);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void createProfile(ProfileRequest request) {
        throw new NotImplementedException();
    }

    @Override
    public void updateProfile(ProfileRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // Unreachable if not authenticated with spring security, just a double check
        if (authentication.isAuthenticated()) {
            AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
            var user = userRepository.getReferenceById(authUserDetail.id());
            var profile = profileRepository
                .findProfileByUser(user)
                .orElseThrow(() -> new ResourceNotFound("Profile not found."));

            profile.setUser(user);
            profile.setAvatar(request.avatar());
            profile.setBio(request.bio());
            profile.setCover(request.cover());

            profileRepository.save(profile);
        } else throw new NotAuthenticatedException();
    }

    @Override
    public ProfileRequest getProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // Unreachable if not authenticated with spring security, just a double check
        if (authentication.isAuthenticated()) {
            AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
            var user = userRepository.getReferenceById(authUserDetail.id());
            var profile = profileRepository
                .findProfileByUser(user)
                .orElseThrow(() -> new ResourceNotFound("Profile not found."));

            return toProfileRequest(profile);
        } else throw new NotAuthenticatedException();

    }

    private Profile toProfileEntity(ProfileRequest request) {
        Profile profile = new Profile();
        profile.setAvatar(request.avatar());
        profile.setCover(request.cover());
        profile.setBio(request.bio());

        profile.setUser(null); // will be set by others, which has authentication context

        return profile;
    }

    private ProfileRequest toProfileRequest(Profile profile) {
        return new ProfileRequest(
            profile.getAvatar(),
            profile.getCover(),
            profile.getBio()

        );
    }

    private User toUserEntity(UserRequest request) {

        String password = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(password);
        user.setEmail(request.getEmail());
        RoleEntity role = roleRepository.findByRole(Role.ROLE_USER);
        user.setRole(role);

        return user;
    }
}
