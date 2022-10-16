package uet.ppvan.mangareader.upload;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    String storeFile(MultipartFile file) throws Exception;

    void deleteFile(String uri) throws Exception;

    byte[] readFileContent(String uri) throws IOException;

    URI getFileDownloadLink(String uri) throws IOException;
}
