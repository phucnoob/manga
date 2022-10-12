package uet.ppvan.mangareader.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dto.ImageRequest;
import uet.ppvan.mangareader.entities.Image;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.ImageRepository;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ChapterRepository chapterRepository;

    public void saveImage(ImageRequest requestData, Integer id) {
        var image = ImageService.toImage(requestData);
        var chapter = chapterRepository.getReferenceById(id);
        image.setChapter(chapter);
        imageRepository.save(image);
    }

    public static ImageRequest toImageDTO(Image image) {
        return new ImageRequest(
            image.getUri(),
            image.getAlt()
        );
    }

    public static Image toImage(ImageRequest imageRequest) {
        return new Image(
            imageRequest.uri(),
            imageRequest.alt()
        );
    }
}
