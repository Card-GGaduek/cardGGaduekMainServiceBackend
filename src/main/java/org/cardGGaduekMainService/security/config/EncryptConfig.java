package org.cardGGaduekMainService.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"org.cardGGaduekMainService.security"})
public class EncryptConfig {
    private String symmetricKey = "h1FyM8VzQc0gHrVQzj9iiovlq5mU4Ig/vMHFUE7jDo0=";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(symmetricKey, "70726574657374");
    }
}
