package uet.ppvan.mangareader.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.mappers.UserMapper;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.services.ProfileService;
import uet.ppvan.mangareader.utils.ResponseFactory;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ProfileController {

    private final ProfileService profileService;

    private final UserMapper userMapper;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal AuthUserDetail authUserDetail) {

        User user = userMapper.buildUserFromAuth(authUserDetail);
        var profile = profileService.getUserProfile(user);

        return ResponseEntity.ok(ResponseFactory.success(profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
        @RequestBody @Valid ProfileRequest request,
        @AuthenticationPrincipal AuthUserDetail authUserDetail
    ) {

        User user = userMapper.buildUserFromAuth(authUserDetail);
        var profile = profileService.updateProfile(request, user);

        return ResponseFactory.success("Update profile successfully.", profile);
    }
}
