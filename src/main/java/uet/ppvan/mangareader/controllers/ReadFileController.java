package uet.ppvan.mangareader.controllers;

import java.io.IOException;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uet.ppvan.mangareader.services.ReadFileService;

@RestController
@RequestMapping("/file")
public class ReadFileController {

    @Autowired
    private ReadFileService fileService;

    @GetMapping("/")
    public String readFile(@RequestParam("path") String filename) {

        try {
            String baseDir = System.getProperty("user.home");
            Path filePath = Path.of(baseDir, filename).toRealPath();

            return fileService.readTextFile(filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "Content";
    }
}
