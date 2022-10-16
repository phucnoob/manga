package uet.ppvan.mangareader.chapters.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.chapters.Chapter;
import uet.ppvan.mangareader.chapters.ChapterRepository;
import uet.ppvan.mangareader.repositories.ImageRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ChapterRepository chapterRepository;
    private final ImageRepository imageRepository;

    public void saveImages(List<ImageRequest> images, Integer chapterId) {

        var chapter = chapterRepository.getReferenceById(chapterId);
        images.forEach(
            imageRequest -> {
                var image = new Image();
                image.setChapter(chapter);
                image.setUri(imageRequest.uri());
                imageRepository.save(image);
            }
        );
    }

    public static List<ImageRequest> toImageRequest(List<Image> images) {
        return images.stream().map(image -> new ImageRequest(image.getUri())).collect(Collectors.toList());
    }

    public static List<Image> toImages(List<ImageRequest> imageRequests, Chapter chapter) {
        AtomicInteger i = new AtomicInteger(1);
        return imageRequests.stream().map(imageRequest -> {
            var image = new Image();
            image.setUri(imageRequest.uri());
            image.setChapterOrder(i.getAndIncrement());
            image.setChapter(chapter);

            return image;
        }).collect(Collectors.toList());
    }
}
