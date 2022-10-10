package uet.ppvan.mangareader.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService implements IStorageService {

    public static Path storageFolder = Path.of(System.getProperty("user.dir"), "images");
    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            String ext = file.getContentType().split("/")[1];
            String filename = String.format("%s.%s", UUID.randomUUID(), ext);
            Path filePath = storageFolder.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } else {
            throw new RuntimeException("File is not a image.");
        }
    }

    @Override
    public Stream<String> loadAll() throws IOException {
        return Files.walk(storageFolder)
            .filter(Files::isRegularFile)
            .map(Path::getFileName)
            .map(Path::toString);
    }

    @Override
    public byte[] readFileContent(String filename) throws IOException {
        return Files.readAllBytes(storageFolder.resolve(filename));
    }
}
