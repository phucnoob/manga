package uet.ppvan.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uet.ppvan.mangareader.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    Boolean existsUserByUsername(String username);

    void deleteUserByEmail(String email);

    void deleteUserByUsername(String username);
}
