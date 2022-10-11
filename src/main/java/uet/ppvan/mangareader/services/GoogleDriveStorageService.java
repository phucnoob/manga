package uet.ppvan.mangareader.services;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class GoogleDriveStorageService implements IStorageService {

    private final Drive googleDrive;
    private final String driveFolderID;

    @Override
    public String storeFile(MultipartFile file) throws Exception {

        file.getInputStream();
        return null;
    }

    @Override
    public Stream<String> loadAll() throws Exception {
        return getAllGoogleDriveFiles().stream()
            .map(File::getId);
    }

    @Override
    public byte[] readFileContent(String filename) throws IOException {
        return new byte[0];
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
