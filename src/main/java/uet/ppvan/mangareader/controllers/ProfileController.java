package uet.ppvan.mangareader.controllers;

//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/users")
//public class ProfileController {
//
//    private final ProfileService profileService;
//
//    private final UserMapper userMapper;
//
//    @GetMapping("/profile")
//    public ResponseEntity<?> getProfile(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
//
//        User user = userMapper.buildUserFromAuth(authUserDetail);
//        var profile = profileService.getUserProfile(user);
//
//        return ResponseEntity.ok(ResponseFactory.success(profile));
//    }
//
//    @PutMapping("/profile")
//    public ResponseEntity<?> updateProfile(
//        @RequestBody @Valid ProfileRequest request,
//        @AuthenticationPrincipal AuthUserDetail authUserDetail
//    ) {
//
//        User user = userMapper.buildUserFromAuth(authUserDetail);
//        var profile = profileService.updateProfile(request, user);
//
//        return ResponseFactory.success("Update profile successfully.", profile);
//    }
//}
