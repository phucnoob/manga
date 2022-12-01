package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.models.User;

public interface UserService {

    User createUser(UserRequest request);

    void deleteUser(Integer userId);
}
