package uet.ppvan.mangareader.controllers;

import java.io.IOException;
import lombok.AllArgsConstructor;
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
import uet.ppvan.mangareader.dto.ImageRequest;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.services.GoogleDriveStorageService;
import uet.ppvan.mangareader.services.ImageService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/image")
public class ImageStorageController {

    private final GoogleDriveStorageService driveStorageService;
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ObjectResponse> uploadImage(
        @RequestParam(name = "chapter_id") Integer id,
        @RequestParam(name = "file") MultipartFile file
    ) {
        try {
            String uri = driveStorageService.storeFile(file);
            String alt = file.getOriginalFilename();
            var imageRequest = new ImageRequest(uri, alt);
            imageService.saveImage(imageRequest, id);

            return ResponseEntity.status(HttpStatus.OK).body(
                new ObjectResponse(
                    ObjectResponse.SUCCESS,
                    "Image saved successfully.",
                    imageRequest
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

    @GetMapping(
        value = "/{filename}",
        produces = {  MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    @ResponseBody
    public byte[] viewImage(@PathVariable String filename) {
        try {
            return driveStorageService.readFileContent(filename);
        } catch (IOException e) {
            throw new RuntimeException("Not found");
        }
    }
}
