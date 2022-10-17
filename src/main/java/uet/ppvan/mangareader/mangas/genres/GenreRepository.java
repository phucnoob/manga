package uet.ppvan.mangareader.mangas.genres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.ppvan.mangareader.mangas.enums.Genre;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    GenreEntity findGenreEntityByGenre(Genre genre);
}
