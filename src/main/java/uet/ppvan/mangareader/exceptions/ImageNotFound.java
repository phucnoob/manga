package uet.ppvan.mangareader.exceptions;

public class ImageNotFound {
    public static NoSuchElementFound withUri(String uri) {
        return new NoSuchElementFound(String.format("Image[uri=%s] not found.", uri));
    }
}
