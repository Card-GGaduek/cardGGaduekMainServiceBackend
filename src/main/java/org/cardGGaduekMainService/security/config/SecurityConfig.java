package org.cardGGaduekMainService.security.config;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"org.cardGGaduekMainService.security"})
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAuthFilter jwtAuthFilter;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해서
                .allowedOrigins("http://localhost:5173")    // 허용할 origin
                .allowedMethods("*")
                .allowedHeaders("*");
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF(사이트 간 요청 위조) 비활성화 (API 서버인 경우 주로 사용). JWT 토큰을 이용해 인증하기 때문에 필요 없음.
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 요청 허용
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);    // UsernamePasswordAuthenticationFilter는 폼 로그인 요청 처리, JWT 기반 인증은 로그인 후 발급된 토큰을 검증해야 하므로 그보다 앞에서 처리되어야 함.
        return http.build();
    }

}
