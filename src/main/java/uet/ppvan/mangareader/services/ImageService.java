package uet.ppvan.mangareader.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dto.ImageRequest;
import uet.ppvan.mangareader.entities.ChapterImage;
import uet.ppvan.mangareader.chapters.ChapterNotFound;
import uet.ppvan.mangareader.exceptions.ImageNotFound;
import uet.ppvan.mangareader.chapters.ChapterRepository;
import uet.ppvan.mangareader.repositories.ImageRepository;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ChapterRepository chapterRepository;

    public void saveImage(ImageRequest requestData, Integer chapterId) {
        var image = ImageService.toImage(requestData);
        var chapter = chapterRepository.findById(chapterId)
            .orElseThrow(() -> ChapterNotFound.withId(chapterId));
        image.setChapter(chapter);
        imageRepository.save(image);
    }

    public void deleteImage(String imageID) {
        if (!imageRepository.existsImageByUri(imageID)) {
            throw ImageNotFound.withUri(imageID);
        }

        imageRepository.deleteImageByUri(imageID);
    }

    public static ImageRequest toImageDTO(ChapterImage chapterImage) {
        return new ImageRequest(
            chapterImage.getUri(),
            chapterImage.getAlt()
        );
    }

    public static ChapterImage toImage(ImageRequest imageRequest) {
        return new ChapterImage(
            imageRequest.uri(),
            imageRequest.alt()
        );
    }
}
