package uet.ppvan.mangareader.mappers;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;


@ExtendWith(MockitoExtension.class)
class ProfileMapperTest {

    @Spy
    private ProfileMapper profileMapper;

    private Validator validator;

    @BeforeEach
    void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    @DisplayName("Report violation rules")
    void shouldValidateProfile() {
        ProfileRequest request = new ProfileRequest(null, "", null);
        var violations = validator.validate(request);

        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("Exception if user is null")
    void shouldThrowExceptionIfUserIsNull() {
        ProfileRequest request = new ProfileRequest(null, "", null);
        var violations = validator.validate(request);

        Assertions.assertThatThrownBy(() -> profileMapper.buildProfileEntity(null, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Exception if user id is null")
    void shouldThrowExceptionIfUserIdIsNull() {
        ProfileRequest request = new ProfileRequest(null, "", null);
        var violations = validator.validate(request);
        User user = new User();
        user.setId(null);

        Assertions.assertThatThrownBy(() -> profileMapper.buildProfileEntity(user, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Convert to profile happy case.")
    void shouldReturnExactData() {
        Profile profile = new Profile();
        profile.setAvatar("Avatar");
        profile.setCover("Cover");
        profile.setBio("Bio");
        profile.setId(0);

        User user = new User();
        user.setId(123);
        profile.setUser(user);
        ProfileRequest request = new ProfileRequest(profile.getAvatar(), profile.getCover(), profile.getBio());

        Assertions.assertThat(profileMapper.buildProfileEntity(user, request))
            .usingRecursiveComparison()
            .isEqualTo(profile);
    }

    @Test
    void buildProfileDTO() {
        Profile profile = new Profile();
        profile.setAvatar("Avatar");
        profile.setCover("Cover");
        profile.setBio("Bio");
        profile.setUser(null);
        profile.setId(0);

        ProfileRequest request = new ProfileRequest(profile.getAvatar(), profile.getCover(), profile.getBio());

        Assertions.assertThat(profileMapper.buildProfileDTO(profile))
            .usingRecursiveComparison()
            .isEqualTo(request);
    }
}