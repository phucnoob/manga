package uet.ppvan.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uet.ppvan.mangareader.chapters.image.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    void deleteImageByUri(String imageID);
    boolean existsImageByUri(String uri);

}
