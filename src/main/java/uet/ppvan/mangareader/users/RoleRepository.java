package uet.ppvan.mangareader.users;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByRole(Role role);
}
