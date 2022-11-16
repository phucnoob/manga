package uet.ppvan.mangareader.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uet.ppvan.mangareader.models.Role;
import uet.ppvan.mangareader.models.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByRole(Role role);
}
