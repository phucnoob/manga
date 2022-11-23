package uet.ppvan.mangareader.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.models.User;

public interface UserService {
    User findUserByUsername(String username) throws UsernameNotFoundException;

    User createUser(UserRequest request);

    void deleteUser(Integer userId);
}
