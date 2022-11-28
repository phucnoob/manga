package uet.ppvan.mangareader.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class PhotosManager {


    // This is directory, not a file.
    private final File DATA_STORE_DIR;
    private final JsonFactory JSON_FACTORY;

    private final String credentialPath;

    public PhotosManager(@Value("${config.credentials-path}") String credentialPath) {
        this.credentialPath = credentialPath;
        this.DATA_STORE_DIR = new File(credentialPath);
        this.JSON_FACTORY = GsonFactory.getDefaultInstance();
    }


    @Bean
    public PhotosLibraryClient photosLibraryClient() throws IOException, GeneralSecurityException {
        String credentialsPath = Path.of(credentialPath, "credentials.json").toString();

        List<String> scopes = List.of(
            "https://www.googleapis.com/auth/photoslibrary.sharing",
            "https://www.googleapis.com/auth/photoslibrary"
        );

        PhotosLibrarySettings settings =
            PhotosLibrarySettings.newBuilder()
                .setCredentialsProvider(
                    FixedCredentialsProvider.create(
                        getUserCredentials(credentialsPath, scopes))
                ).build();

        return PhotosLibraryClient.initialize(settings);
    }


    private Credentials getUserCredentials(String credentialsPath, List<String> selectedScopes)
        throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(new FileInputStream(credentialsPath)));
        String clientId = clientSecrets.getDetails().getClientId();
        String clientSecret = clientSecrets.getDetails().getClientSecret();

        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                selectedScopes)
                .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
                .setAccessType("offline")
                .build();
        int LOCAL_PORT = 9000;
        LocalServerReceiver receiver =
            new LocalServerReceiver.Builder().setPort(LOCAL_PORT).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return UserCredentials.newBuilder()
                   .setClientId(clientId)
                   .setClientSecret(clientSecret)
                   .setRefreshToken(credential.getRefreshToken())
                   .build();
    }
}
