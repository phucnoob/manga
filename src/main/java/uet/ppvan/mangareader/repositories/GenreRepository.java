package uet.ppvan.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.models.GenreEntity;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    GenreEntity findGenreEntityByGenre(Genre genre);
}
