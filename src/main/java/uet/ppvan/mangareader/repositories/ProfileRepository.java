package uet.ppvan.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findProfileByUser(User user);

}
