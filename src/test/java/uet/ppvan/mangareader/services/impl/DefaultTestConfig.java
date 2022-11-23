package uet.ppvan.mangareader.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultTestConfig {
    @Bean
    @Qualifier("hello")
    String hello() {
        return "hello";
    }

}
