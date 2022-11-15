package uet.ppvan.mangareader.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
@Profile("!default")
public class DriveManager {
    private static final String APPLICATION_NAME = "Testing";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    @Value("${config.credentials}")
    private String CREDENTIALS_FILE;

    @Bean
    public Drive getInstance() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final var credentials = new HttpCredentialsAdapter(getCredentials(HTTP_TRANSPORT));

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * {deprecated}
     * @param HTTP_TRANSPORT
     * @return
     * @throws IOException
     */
//    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//        // Load client secrets.
//        InputStream in = getClass().getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost("127.0.0.1").setPort(8089).build();
//
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }

    private GoogleCredentials getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        try (InputStream in = new ByteArrayInputStream(CREDENTIALS_FILE.getBytes(StandardCharsets.UTF_8))) {

            final GoogleCredentials credential = GoogleCredentials.fromStream(in)
                    .createScoped(SCOPES);

            return credential;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        throw new AssertionError("Unauthorized google.");
    }
}
