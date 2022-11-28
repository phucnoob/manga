package uet.ppvan.mangareader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uet.ppvan.mangareader.services.impl.PhotosStorageService;
import uet.ppvan.mangareader.utils.ResponseFactory;

@RestController
@Profile("!default")
@RequestMapping("/api/v1/image")
public class ImageUploadController {

    private final PhotosStorageService storage;

    @Value("${config.host}")
    private String hostName;

    @Autowired
    public ImageUploadController(PhotosStorageService storage) {
        this.storage = storage;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file) {
        String id = storage.storeFile(file);
        return ResponseEntity.ok(
            ResponseFactory.success(id)
        );
    }

    @GetMapping(value = "/{id}",
        produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> getImage(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                   .location(storage.getLink(id))
                   .build();
    }

    @GetMapping("/views/{id}")
    public String getPreview(@PathVariable String id) {

        return storage.getLink(id).toString();
    }
}
