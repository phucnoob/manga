package uet.ppvan.mangareader.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.models.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<ChapterOverview> findByManga_Id(Integer id);

    @Override
    @Query("""
        SELECT c FROM Chapter c JOIN FETCH c.images WHERE c.id = :id
        """)
    Optional<Chapter> findById(Integer id);
}
