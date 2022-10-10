package uet.ppvan.mangareader.services;

import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    String storeFile(MultipartFile file) throws Exception;
    Stream<String> loadAll() throws Exception;

    byte[] readFileContent(String filename) throws IOException;
}
