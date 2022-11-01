package uet.ppvan.mangareader.mangas.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.ppvan.mangareader.mangas.Manga;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Integer> {

    <T> Page<T> findBy(Class<T> type, Pageable pageable);

}
