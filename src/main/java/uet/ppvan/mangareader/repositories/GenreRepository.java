package uet.ppvan.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.models.GenreEntity;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    GenreEntity findGenreEntityByGenre(Genre genre);

    @Query("""
        SELECT g.genre FROM GenreEntity g JOIN g.mangas m WHERE m.id = :id
        """)
    List<Genre> findGenreEntitiesByMangaId(Integer id);
}
