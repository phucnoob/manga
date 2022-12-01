package uet.ppvan.mangareader.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends BaseException {
    public ResourceNotFound(String message) {
        super(message);
    }

    public static ResourceNotFound userNotFound() {
        return new ResourceNotFound("User is not found.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public static ResourceNotFound mangaNotFound(Integer id) {
        return new ResourceNotFound(String.format("Manga[id=%s] not found.", id));
    }

    public static ResourceNotFound imageNotFound(String uri) {
        return new ResourceNotFound(String.format("Image[uri=%s] not found.", uri));
    }

    public static ResourceNotFound chapterNotFound(Integer id) {
        return new ResourceNotFound(String.format("Chapter[id=%s] not found.", id));
    }
}
