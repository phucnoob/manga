package uet.ppvan.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.ppvan.mangareader.entities.Manga;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Integer> {

}
