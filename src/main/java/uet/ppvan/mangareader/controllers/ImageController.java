package uet.ppvan.mangareader.controllers;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import uet.ppvan.mangareader.upload.DriveStorageService;
import uet.ppvan.mangareader.services.ImageService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/image")
public class ImageController {

    private final DriveStorageService driveStorageService;
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ObjectResponse> uploadImage(
        @RequestParam(name = "chapter_id") Integer chapterId,
        @RequestParam(name = "files") MultipartFile[] files
    ) {
        List<ImageRequest> savedImages = new ArrayList<>(files.length);

        for (var file : files) {
            String uri = driveStorageService.storeFile(file);
            String alt = file.getOriginalFilename();
            var request = new ImageRequest(uri, alt);
            imageService.saveImage(request, chapterId);
            savedImages.add(request);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            new ObjectResponse(
                ObjectResponse.SUCCESS,
                "Images saved successfully.",
                savedImages
            )
        );
    }

    @GetMapping(
        value = "view/{filename}",
        produces = {  MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    @ResponseBody
    public byte[] viewImage(@PathVariable String filename) {
        return driveStorageService.readFileContent(filename);
    }

    @GetMapping(
        value = "/preview/{filename}"
    )
    public ResponseEntity<Object> previewImage(
        @PathVariable String filename
    ) {
        var uri = driveStorageService.getFileDownloadLink(filename);
        var headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ObjectResponse> deleteImage(
        @RequestParam(name = "image_id") String imageID
    ) {
        imageService.deleteImage(imageID);
        driveStorageService.deleteFile(imageID);

        return ResponseEntity.ok(
            new ObjectResponse(
                ObjectResponse.SUCCESS,
                "Delete image successfully.",
                ""
            )
        );
    }
}
