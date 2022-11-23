package uet.ppvan.mangareader.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.exceptions.UserAlreadyExistException;
import uet.ppvan.mangareader.mappers.UserMapper;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.UserRepository;
import uet.ppvan.mangareader.services.ProfileService;
import uet.ppvan.mangareader.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final ProfileService profileService;


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

        var user = userMapper.buildUserEntity(request);

        // Create a default profile for new user.
        var profileRequest = new ProfileRequest();
        var defaultProfile = profileService.createProfile(profileRequest, user);

        user.setProfile(defaultProfile);
        user.setVerified(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }


}
