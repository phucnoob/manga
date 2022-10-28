package uet.ppvan.mangareader.upload.exceptions;

import uet.ppvan.mangareader.comons.exceptions.NoSuchElementFound;

public class ImageNotFound {
    public static NoSuchElementFound withUri(String uri) {
        return new NoSuchElementFound(String.format("Image[uri=%s] not found.", uri));
    }
}
