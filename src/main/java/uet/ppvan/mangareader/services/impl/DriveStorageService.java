package uet.ppvan.mangareader.services.impl;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uet.ppvan.mangareader.exceptions.InvalidUploadFile;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.exceptions.UploadFileInterrupt;
import uet.ppvan.mangareader.services.StorageService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Profile("!default")
@RequiredArgsConstructor
public class DriveStorageService implements StorageService {

    private final Drive googleDrive;
    @Value("${config.drive-folder}")
    private String driveFolderID;

    private static final Logger logger = LoggerFactory.getLogger(DriveStorageService.class);

    @Override
    public String storeFile(MultipartFile file) {
        try {
            validateUploadFile(file);
            File metaData = new File();
            metaData.setParents(Collections.singletonList(driveFolderID));
            metaData.setName(UUID.randomUUID().toString());
            metaData.setMimeType(file.getContentType());

            File uploadedFile = googleDrive.files()
                .create(metaData, new InputStreamContent(
                    file.getContentType(),
                    new ByteArrayInputStream(file.getBytes())
                ))
                .setFields("id,webContentLink")
                .execute();
            return uploadedFile.getId();
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
            throw UploadFileInterrupt.withMessage("Image upload interrupted.");
        }
    }

    private void validateUploadFile(MultipartFile file) {

        if (file.getContentType() == null) {
            throw new InvalidUploadFile("Unknown filetype");
        }

        if (file.isEmpty()) {
            throw new InvalidUploadFile("File can't be empty.");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new InvalidUploadFile("Not an image file.");
        }
    }

    @Override
    public void deleteFile(String imageURI) {
        try {
            googleDrive.files().delete(imageURI).execute();
        } catch (IOException e) {
            throw ResourceNotFound.imageNotFound(imageURI);
        }
    }



    @Override
    public byte[] readFileContent(String imageURI) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            googleDrive.files().get(imageURI).executeMediaAndDownloadTo(outputStream);

            return outputStream.toByteArray();
        } catch (IOException exception) {
            throw ResourceNotFound.imageNotFound(imageURI);
        }
    }

    @Override
    public URI getLink(String id) {
        try {
            File fileInfo = googleDrive.files().get(id)
                .setFields("id,webContentLink,webViewLink").execute();
            return URI.create(fileInfo.getWebContentLink());
        } catch (IOException exception) {
            throw ResourceNotFound.imageNotFound(id);
        }
    }

    private List<File> getAllGoogleDriveFiles() throws IOException {
        FileList result = googleDrive.files().list()
            .setFields("nextPageToken, files(id, name, parents, mimeType)")
            .execute();
        return result.getFiles();
    }

    private String createNewFolder(String folderName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = googleDrive.files().create(fileMetadata).setFields("id").execute();
        return file.getId();
    }

    private String uploadFile(java.io.File file) throws IOException {
        File nGDriveFile = new File();
        nGDriveFile.setParents(List.of(driveFolderID)).setName("testing");
        FileContent content = new FileContent("image/jpg", file);
        File uploadedFile = googleDrive.files().create(nGDriveFile, content)
            .setFields("id,webViewLink,webContentLink").execute();

        return uploadedFile.getWebContentLink();
    }
}
