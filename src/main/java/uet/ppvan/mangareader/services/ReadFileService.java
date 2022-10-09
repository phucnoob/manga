package uet.ppvan.mangareader.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class ReadFileService {
    public String readTextFile(Path path) throws IOException {
        return Files.readString(path);
    }
}
