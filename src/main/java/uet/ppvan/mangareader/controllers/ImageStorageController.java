package uet.ppvan.mangareader.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.services.ImageStorageService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/image")
public class ImageStorageController {

    private final ImageStorageService imageStorageService;

    @PostMapping("/upload")
    public ResponseEntity<ObjectResponse> uploadImage(
        @RequestParam(name = "file") MultipartFile file
    ) {
        try {
            String imageUrl = imageStorageService.storeFile(file);

            return ResponseEntity.status(HttpStatus.OK).body(
                new ObjectResponse(
                    ObjectResponse.SUCCESS,
                    "image saved successfully.",
                    imageUrl
                )
            );

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();

            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ObjectResponse(
                    ObjectResponse.FAILED,
                    exception.getMessage(),
                    ""
                )
            );
        }

    }

    @GetMapping("/list")
    public List<String> listImage() {
        try {
            return imageStorageService.loadAll().toList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @GetMapping(
        value = "/{filename:.+}",
        produces = {  MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    @ResponseBody
    public byte[] viewImage(@PathVariable String filename) {
        try {
            return imageStorageService.readFileContent(filename);
        } catch (IOException e) {
            throw new RuntimeException("Not found");
        }
    }
}
