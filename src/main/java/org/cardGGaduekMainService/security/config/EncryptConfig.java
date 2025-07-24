package org.cardGGaduekMainService.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"org.cardGGaduekMainService.security"})
@PropertySource({"classpath:/application-secret.properties"})
public class EncryptConfig {
    @Value( "${spring.security.aes.symmetricKey}" )
    private String symmetricKey;

    @Value( "${spring.security.aes.salt}")
    private String salt;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(symmetricKey, salt);
    }
}
