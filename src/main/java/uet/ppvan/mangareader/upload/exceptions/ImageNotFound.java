package uet.ppvan.mangareader.upload.exceptions;

import uet.ppvan.mangareader.comons.exceptions.ResourceNotFound;

public class ImageNotFound {
    public static ResourceNotFound withUri(String uri) {
        return new ResourceNotFound(String.format("Image[uri=%s] not found.", uri));
    }
}
