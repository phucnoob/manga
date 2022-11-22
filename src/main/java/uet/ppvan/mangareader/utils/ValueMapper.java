package uet.ppvan.mangareader.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.Role;
import uet.ppvan.mangareader.models.RoleEntity;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.RoleRepository;

/**
 * Value mapper to serialize and deserialize JSON
 */
@Service
@RequiredArgsConstructor
public class ValueMapper {
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public ValueMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        passwordEncoder = new BCryptPasswordEncoder();
        mapper = new ObjectMapper();
    }

    public String objectAsJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T jsonAsObject(Class<T> type, String json) {
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Profile toProfileEntity(ProfileRequest request) {
        Profile profile = new Profile();
        profile.setAvatar(request.avatar());
        profile.setCover(request.cover());
        profile.setBio(request.bio());

        profile.setUser(null); // will be set by others, which has authentication context

        return profile;
    }

    public static ProfileRequest toProfileRequest(Profile profile) {
        return new ProfileRequest(
            profile.getAvatar(),
            profile.getCover(),
            profile.getBio()

        );
    }

    public User toUserEntity(UserRequest request) {

        String password = passwordEncoder.encode(request.password());

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(password);
        user.setEmail(request.email());
        RoleEntity role = roleRepository.findByRole(Role.ROLE_USER);
        user.setRole(role);

        return user;
    }
}
