package uet.ppvan.mangareader.services;

import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    String storeFile(MultipartFile file) throws Exception;

    void deleteFile(String imageURI) throws Exception;

    byte[] readFileContent(String imageURI) throws IOException;
}
