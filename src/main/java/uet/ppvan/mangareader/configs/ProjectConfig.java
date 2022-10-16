package uet.ppvan.mangareader.configs;


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

    @Value("${config.account-id}")
    String accountID;

    @Value("${config.host}")
    String hostName;

    @Bean
    public String driveFolderID() {
        return driveFolder;
    }
}
