package org.cardGGaduekMainService.codef.config;

import org.cardGGaduekMainService.codef.service.CodefTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/application-secret.properties"})
public class CodefConfig {
    @Value("${codef.client.id}")
    private String clientId;

    @Value("${codef.client.secret}")
    private String clientSecret;

    @Bean
    public CodefTokenProvider codefTokenProvider() {
        return new CodefTokenProvider(clientId, clientSecret);
    }





}
