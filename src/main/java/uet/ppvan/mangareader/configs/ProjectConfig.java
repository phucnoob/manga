package uet.ppvan.mangareader.configs;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {


    @Value("${config.drive-folder}")
    String driveFolder;
    @Value("${config.google-api-pass}")
    String googleAPIPass;

    @Value("${config.google-api-key}")
    String googleAPIKey;

    @Value("${config.drive-folder}")
    String accountID;

    @Bean
    public String driveFolderID() {
        return driveFolder;
    }
    @Bean
    public Drive getService(
        GoogleCredential googleCredential) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT,
            JacksonFactory.getDefaultInstance(), googleCredential)
            .setApplicationName("Testing")
            .build();
    }

    @Bean
    public GoogleCredential googleCredential() throws GeneralSecurityException, IOException {
        Collection<String> elenco = new ArrayList<String>();


        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(getClass().getResourceAsStream("/testing.p12"), googleAPIPass.toCharArray());
        PrivateKey privateKey = (PrivateKey) keystore.getKey(googleAPIKey, googleAPIPass.toCharArray());

        elenco.add("https://www.googleapis.com/auth/drive");
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        return new GoogleCredential.Builder()
            .setTransport(httpTransport)
            .setJsonFactory(jsonFactory)
            .setServiceAccountId(accountID)
            .setServiceAccountScopes(elenco)
            .setServiceAccountPrivateKey(privateKey)
            .build();
    }

}
