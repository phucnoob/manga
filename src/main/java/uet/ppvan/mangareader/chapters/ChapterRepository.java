package uet.ppvan.mangareader.chapters;


import org.springframework.data.jpa.repository.JpaRepository;
import uet.ppvan.mangareader.chapters.Chapter;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<ChapterOverview> findByManga_Id(Integer id);
}
