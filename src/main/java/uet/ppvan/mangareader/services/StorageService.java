package uet.ppvan.mangareader.services;

import org.springframework.cache.annotation.Caching;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public interface StorageService {
    String storeFile(MultipartFile file);

    void deleteFile(String id);

    byte[] readFileContent(String id);

    URI getLink(String id);
}
