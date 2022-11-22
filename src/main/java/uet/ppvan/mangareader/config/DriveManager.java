package uet.ppvan.mangareader.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Manager to authenticate and authorize Google Drive.
 */
@Service
@Profile("!default")
@Slf4j
public class DriveManager {
    private static final String APPLICATION_NAME = "Testing";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    @Value("${config.credentials}")
    private String CREDENTIALS_FILE;

    /**
     * Get a single {@link Drive} instance.
     * Not safe to call in multi-thread context.
     * @return {@link Drive}
     * @throws GeneralSecurityException Google API Exception
     * @throws IOException Google API specific
     */
    @Bean
    public Drive getInstance() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final var credentials = new HttpCredentialsAdapter(getCredentials());

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Load credentials from ENV.
     * @return {@link GoogleCredentials} - The Credentials holder.
     */
    private GoogleCredentials getCredentials() {

        try (InputStream in = new ByteArrayInputStream(CREDENTIALS_FILE.getBytes(StandardCharsets.UTF_8))) {

            return GoogleCredentials.fromStream(in)
                    .createScoped(SCOPES);

        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
        throw new AssertionError("Unauthorized google.");
    }
}
