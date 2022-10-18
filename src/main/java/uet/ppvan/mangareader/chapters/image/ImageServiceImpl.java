package uet.ppvan.mangareader.chapters.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.chapters.Chapter;
import uet.ppvan.mangareader.chapters.interfaces.ChapterRepository;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ChapterRepository chapterRepository;
    private final ImageRepository imageRepository;

    @Override
    public void saveImages(Set<String> imageLinks, Integer chapterId) {

        var chapter = chapterRepository.getReferenceById(chapterId);
        AtomicInteger i = new AtomicInteger();
        imageLinks.forEach(imageLink -> {
            var image = new Image();
            image.setChapter(chapter);
            image.setUri(imageLink);
            image.setChapterOrder(i.incrementAndGet());
            imageRepository.save(image);
        });
    }

    public static Set<String> toImageRequest(Set<Image> images) {
        return images.stream().map(Image::getUri)
            .collect(Collectors.toSet());
    }

    public static Set<Image> toImages(Set<String> imageRequests, Chapter chapter) {
        AtomicInteger i = new AtomicInteger(1);
        return imageRequests.stream().map(imageRequest -> {
            var image = new Image();
            image.setUri(imageRequest);
            image.setChapterOrder(i.getAndIncrement());
            image.setChapter(chapter);

            return image;
        }).collect(Collectors.toSet());
    }
}
