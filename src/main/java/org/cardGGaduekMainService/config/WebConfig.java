package org.cardGGaduekMainService.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.cardGGaduekMainService")
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    // 업로드 디렉토리 (Windows 예시: "C:/upload" , mac : "/User/seongbochoi/upload")
    private static final String LOCATION = "C:/upload";
    private static final long MAX_FILE_SIZE = 10L * 1024 * 1024; // 10MB
    private static final long MAX_REQUEST_SIZE = 20L * 1024 * 1024; // 20MB
    private static final int FILE_SIZE_THRESHOLD = 5 * 1024 * 1024; // 5MB

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class }; // DB, MyBatis 설정
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class }; // MVC, ViewResolver 설정
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // 모든 요청을 DispatcherServlet에서 처리
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return new Filter[] { encodingFilter };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                LOCATION,
                MAX_FILE_SIZE,
                MAX_REQUEST_SIZE,
                FILE_SIZE_THRESHOLD
        );
        registration.setMultipartConfig(multipartConfig);
    }
}
