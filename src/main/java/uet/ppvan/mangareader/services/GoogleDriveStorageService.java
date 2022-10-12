package uet.ppvan.mangareader.services;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class GoogleDriveStorageService implements IStorageService {

    private final Drive googleDrive;
    private final String driveFolderID;

    @Override
    public String storeFile(MultipartFile file) throws Exception {
        try {
            if (validateUploadFile(file)) {
                File metaData = new File();
                metaData.setParents(Collections.singletonList(driveFolderID));
                metaData.setName(UUID.randomUUID().toString());
                metaData.setMimeType(file.getContentType());

                File uploadedFile = googleDrive.files()
                    .create(metaData, new InputStreamContent(
                        file.getContentType(),
                        new ByteArrayInputStream(file.getBytes())
                ))
                    .setFields("id")
                    .execute();

                return uploadedFile.getId();
            } else {
                System.out.println("Error");
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }

    private boolean validateUploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        return true;
    }

    @Override
    public void deleteFile(String imageURI) throws Exception {
        googleDrive.files().delete(imageURI).execute();
    }



    @Override
    public byte[] readFileContent(String imageURI) throws IOException {

        if (imageURI == null) return null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        googleDrive.files().get(imageURI).executeMediaAndDownloadTo(outputStream);

        return outputStream.toByteArray();
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
