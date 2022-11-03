package uet.ppvan.mangareader.mangas.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.ppvan.mangareader.mangas.Manga;
import uet.ppvan.mangareader.mangas.MangaOverview;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Integer> {

    <T> Page<T> findBy(Class<T> type, Pageable pageable);

    <T> Optional<T> findById(Class<T> type, Integer id);
}
