package uet.ppvan.mangareader.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.models.Manga;

import java.util.Optional;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Integer> {


    @Query("""
        SELECT new uet.ppvan.mangareader.dtos.MangaOverview(m.id, m.name, m.cover, m.description)
        FROM Manga m
        """)
    Page<MangaOverview> findAllOverview(Pageable pageable);

    @Query("""
        SELECT new uet.ppvan.mangareader.dtos.MangaOverview(m.id, m.name, m.cover, m.description)
        FROM Manga m WHERE m.id = :id
        """)
    Optional<MangaOverview> findOverviewById(Integer id);

    @Query("""
        SELECT m FROM Manga m JOIN FETCH m.chapters c WHERE m.id = :id
        """)
    Optional<MangaDetails> findMangaById(Integer id);
}
