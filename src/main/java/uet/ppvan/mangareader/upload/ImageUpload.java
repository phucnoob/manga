package uet.ppvan.mangareader.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uet.ppvan.mangareader.dto.ResponseFactory;

@RestController
@RequestMapping("/api/v2/image")
public class ImageUpload {

    public static final String API_END_POINTS = "/api/v2/image";

    private final StorageService storage;

    @Value("${config.host}")
    private String hostName;

    @Autowired
    public ImageUpload(DriveStorageService storage) {
        this.storage = storage;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file) {
        String id = storage.storeFile(file);
        // <host><api_path>/<id>
        String uri = String.format("%s%s/%s", hostName, API_END_POINTS, id);
        return ResponseEntity.ok(
                ResponseFactory.success(uri)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getImage(@PathVariable String id) {

        var headers = new HttpHeaders();
        headers.setLocation(storage.getLink(id));

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
