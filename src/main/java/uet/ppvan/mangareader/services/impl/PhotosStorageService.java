package uet.ppvan.mangareader.services.impl;

import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.proto.BatchCreateMediaItemsResponse;
import com.google.photos.library.v1.proto.NewMediaItem;
import com.google.photos.library.v1.proto.NewMediaItemResult;
import com.google.photos.library.v1.upload.UploadMediaItemRequest;
import com.google.photos.library.v1.util.NewMediaItemFactory;
import com.google.photos.types.proto.MediaItem;
import com.google.rpc.Code;
import com.google.rpc.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uet.ppvan.mangareader.exceptions.UploadFileInterrupt;
import uet.ppvan.mangareader.services.StorageService;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotosStorageService implements StorageService {

    private final PhotosLibraryClient photosLibraryClient;

    @Override
    public String storeFile(MultipartFile file) {


        try {
            // Create a temp file in system.

            Path temp = Files.createTempFile(null, null);
            System.out.println(temp.getFileName());
//            Path temp = Files.createFile(Path.of("tmp"));            file.transferTo(temp);

            // Load to raf
            RandomAccessFile randomAccessFile = new RandomAccessFile(temp.toFile(), "r");


            // build the request and send it
            UploadMediaItemRequest request =
                UploadMediaItemRequest.newBuilder()
                    .setMimeType(Objects.requireNonNull(file.getContentType()))
                    .setDataFile(randomAccessFile)
                    .build();

            var response = photosLibraryClient.uploadMediaItem(request);
            if (response.getUploadToken().isPresent()) {
                String token = response.getUploadToken().get();

                var mediaItem = NewMediaItemFactory.createNewMediaItem(token, file.getName(), "description");


                List<NewMediaItem> newItems = List.of(mediaItem);
                BatchCreateMediaItemsResponse batch = photosLibraryClient.batchCreateMediaItems(newItems);
                for (var med: batch.getNewMediaItemResultsList()) {
                    if (med.getStatus().getCode() == Code.OK_VALUE) {
                        return med.getMediaItem().getId();
                    }
                }
            }

        } catch (IOException e) {
            throw UploadFileInterrupt.withMessage("Upload image not success, Please try again.");
        }


        return null;
    }

    @Override
    public void deleteFile(String id) {
        throw new UnsupportedOperationException("Google not allow this.");
    }

    @Override
    public byte[] readFileContent(String id) {
        throw new UnsupportedOperationException("No need to do this, please use #getLink(String)");
    }

    @Override
    @Cacheable("images")
    public URI getLink(String id) {
        var mediaItem = photosLibraryClient.getMediaItem(id);

        return URI.create(mediaItem.getBaseUrl() + "=w2048-h1024");
    }


    /**
     * Auto clear cache after ~ 60min
     * Because google base url expired in that time.
     */
    // FIXME: 11/28/22 Better to use providers that have expired time.
    @CacheEvict(value = "images", allEntries = true)
    @Scheduled(fixedDelay = 1000 * 60 * 50)
    public void clearCacheById() {
        log.info("Clear cache for images");

    }
}
