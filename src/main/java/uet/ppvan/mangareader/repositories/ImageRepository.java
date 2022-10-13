package uet.ppvan.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uet.ppvan.mangareader.entities.ChapterImage;

public interface ImageRepository extends JpaRepository<ChapterImage, Integer> {

    void deleteImageByUri(String imageID);
    boolean existsImageByUri(String uri);

}
