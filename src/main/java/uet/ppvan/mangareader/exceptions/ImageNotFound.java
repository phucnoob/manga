package uet.ppvan.mangareader.exceptions;

public class ImageNotFound {
    public static ResourceNotFound withUri(String uri) {
        return new ResourceNotFound(String.format("Image[uri=%s] not found.", uri));
    }
}
