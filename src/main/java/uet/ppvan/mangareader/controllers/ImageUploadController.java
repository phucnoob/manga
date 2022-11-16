package uet.ppvan.mangareader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uet.ppvan.mangareader.services.StorageService;
import uet.ppvan.mangareader.services.impl.DriveStorageService;
import uet.ppvan.mangareader.utils.ResponseFactory;

import java.io.File;
import java.io.IOException;

@RestController
@Profile("!default")
@RequestMapping("/api/v1/image")
public class ImageUploadController {

    public static final String API_END_POINTS = "/api/v2/image";

    private final StorageService storage;

    @Value("${config.host}")
    private String hostName;

    @Autowired
    public ImageUploadController(DriveStorageService storage) {
        this.storage = storage;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file) {
        String id = storage.storeFile(file);
        // <host><api_path>/<id>
//        String uri = String.format("%s%s/%s", hostName, API_END_POINTS, id);
        return ResponseEntity.ok(
            ResponseFactory.success(id)
        );
    }

    @GetMapping(value = "/{id}",
        produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    @Cacheable("images")
    public byte[] getImage(
        @PathVariable String id
    ) {

        return storage.readFileContent(id);
    }

    @GetMapping(value = "/test",
        produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public byte[] demo() throws IOException {
        return FileCopyUtils.copyToByteArray(new File("/home/ppvan/Pictures/04_63724bd269de8.jpg"));
    }
}
