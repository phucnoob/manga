//package uet.ppvan.mangareader.services.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import uet.ppvan.mangareader.dtos.AuthUserDetail;
//import uet.ppvan.mangareader.dtos.ProfileRequest;
//import uet.ppvan.mangareader.dtos.UserRequest;
//import uet.ppvan.mangareader.exceptions.ResourceNotFound;
//import uet.ppvan.mangareader.exceptions.UserAlreadyExistException;
//import uet.ppvan.mangareader.models.Profile;
//import uet.ppvan.mangareader.models.Role;
//import uet.ppvan.mangareader.models.User;
//import uet.ppvan.mangareader.repositories.ProfileRepository;
//import uet.ppvan.mangareader.repositories.RoleRepository;
//import uet.ppvan.mangareader.repositories.UserRepository;
//import uet.ppvan.mangareader.services.UserService;
//import uet.ppvan.mangareader.utils.ValueMapper;
//
//import java.util.Optional;
//
//import static org.mockito.AdditionalAnswers.returnsFirstArg;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private ProfileRepository profileRepository;
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Spy
//    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
//
//
//    private UserService userService;
//
//    private ValueMapper valueMapper;
//
//    @BeforeEach
//    public void init() {
//        valueMapper = new ValueMapper(new ObjectMapper(), new BCryptPasswordEncoder(12), roleRepository);
//        userService = new UserServiceImpl(
//            userRepository,
//            profileRepository,
//            valueMapper
//        );
//
//    }
//
//    @Test
//    @DisplayName("Create user with existed email")
//    void shouldThrowException_IfEmail_Existed() {
//        String email = "dummy@gmail.com";
//        Mockito.when(userRepository.existsUserByEmail(email)).thenReturn(true);
//
//        UserRequest request = new UserRequest(
//            "test",
//            email,
//            "pass",
//            "pass"
//        );
//
//        Assertions.assertThatThrownBy(() -> userService.createUser(request))
//            .isInstanceOf(UserAlreadyExistException.class)
//            .hasMessage("Email is taken.");
//    }
//
//    @Test
//    @DisplayName("Create user with existed username")
//    void shouldThrowException_IfUsername_Existed() {
//        Mockito.when(userRepository.existsUserByUsername("test")).thenReturn(true);
//
//        UserRequest request = new UserRequest(
//            "test",
//            "dummy@gmail.com",
//            "pass",
//            "pass"
//        );
//
//        Assertions.assertThatThrownBy(() -> userService.createUser(request))
//            .isInstanceOf(UserAlreadyExistException.class)
//            .hasMessage("Username is taken. Try different name.");
//    }
//
//    @Test
//    @DisplayName("Create user with default profile.")
//    void shouldCreateUser_with_EmptyProfile() {
//        Mockito.when(userRepository.existsUserByUsername("test")).thenReturn(false);
//        Mockito.when(userRepository.existsUserByEmail("dummy@gmail.com")).thenReturn(false);
//
//        UserRequest request = new UserRequest(
//            "test",
//            "dummy@gmail.com",
//            "pass",
//            "pass"
//        );
//        var profileRequest = new ProfileRequest();
//        var profile = valueMapper.toProfileEntity(profileRequest);
//        Mockito.when(userRepository.save(Mockito.any(User.class))).then(returnsFirstArg());
//
//
//        var user = userService.createUser(request);
//
//        Assertions.assertThat(user.getProfile())
//            .usingRecursiveComparison()
//            .ignoringFields("user")
//            .isEqualTo(profile);
//
//        Assertions.assertThat(user.getVerified()).isFalse();
//    }
//
//
//    @Test
//    @DisplayName("Update profile from default profile")
//    void updateProfile() {
//        var authentication = Mockito.mock(Authentication.class);
//        var securityContext = Mockito.mock(SecurityContext.class);
//        var user = new AuthUserDetail(12, "test", Role.ROLE_USER);
//
//        securityContext.setAuthentication(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        // Mock security context
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
//
//        // Mock repository result.
//        Mockito.when(userRepository.getReferenceById(Mockito.anyInt()))
//            .thenReturn(Mockito.mock(User.class));
//        Mockito.when(profileRepository.findProfileByUser(Mockito.any(User.class)))
//            .thenReturn(Optional.of(Profile.defaultProfile()));
//        Mockito.when(authentication.getPrincipal()).thenReturn(user);
//        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).then(returnsFirstArg());
//
//        var request = new ProfileRequest("avatar", "cover", "bio");
//        var actualProfile = userService.updateProfile(request);
//        var expectProfile = valueMapper.toProfileEntity(request);
//
//        Assertions.assertThat(actualProfile)
//            .usingRecursiveComparison()
//            .ignoringFields("user")
//            .isEqualTo(expectProfile);
//    }
//
//    @Test
//    @DisplayName("Update an non-exist profile.")
//    void shouldThrowException_when_UpdateNotExistedProfile() {
//        var authentication = Mockito.mock(Authentication.class);
//        var securityContext = Mockito.mock(SecurityContext.class);
//        var user = new AuthUserDetail(12, "test", Role.ROLE_USER);
//
//        securityContext.setAuthentication(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        // Mock security context
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
//
//        // Mock repository result.
//        Mockito.when(authentication.getPrincipal()).thenReturn(user);
//        Mockito.when(userRepository.getReferenceById(Mockito.anyInt()))
//            .thenReturn(Mockito.mock(User.class));
//        Mockito.when(profileRepository.findProfileByUser(Mockito.any(User.class)))
//            .thenReturn(Optional.empty());
////        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).then(returnsFirstArg());
//
//        var request = new ProfileRequest("avatar", "cover", "bio");
//
//        Assertions.assertThatThrownBy(() -> userService.updateProfile(request)).isInstanceOf(ResourceNotFound.class);
//    }
//}