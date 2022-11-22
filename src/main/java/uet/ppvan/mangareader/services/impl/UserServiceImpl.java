package uet.ppvan.mangareader.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.exceptions.NotAuthenticatedException;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.exceptions.UserAlreadyExistException;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.ProfileRepository;
import uet.ppvan.mangareader.repositories.RoleRepository;
import uet.ppvan.mangareader.repositories.UserRepository;
import uet.ppvan.mangareader.services.UserService;
import uet.ppvan.mangareader.utils.ValueMapper;

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
    public User createUser(UserRequest request) {
        if (userRepository.existsUserByEmail(request.email())) {
            throw UserAlreadyExistException.emailTaken();
        }

        if (userRepository.existsUserByUsername(request.username())) {
            throw UserAlreadyExistException.usernameTaken();
        }

        var user = ValueMapper.toUserEntity(request);

        // Create a default profile for new user.
        var profileRequest = new ProfileRequest();
        var defaultProfile = ValueMapper.toProfileEntity(profileRequest);
        defaultProfile.setUser(user);
        user.setProfile(defaultProfile);
        user.setVerified(false);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }


    @Override
    public Profile updateProfile(ProfileRequest request) {
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

            return profileRepository.save(profile);
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

            return ValueMapper.toProfileRequest(profile);
        } else throw new NotAuthenticatedException();

    }


}
