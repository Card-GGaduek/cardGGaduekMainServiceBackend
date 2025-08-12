package org.cardGGaduekMainService.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// DispatcherServler (FrontController), WebMvc 관련 설정
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.cardGGaduekMainService.exception",
        "org.cardGGaduekMainService.member.controller",
        "org.cardGGaduekMainService.auth.controller",
        "org.cardGGaduekMainService.lab.controller",
        "org.cardGGaduekMainService.transaction.controller",
        "org.cardGGaduekMainService.store.controller",
        "org.cardGGaduekMainService.payment.controller",
        "org.cardGGaduekMainService.notification.controller",
        "org.cardGGaduekMainService.common.controller",
        "org.cardGGaduekMainService.coupon.memberCoupon.controller",
        "org.cardGGaduekMainService.product.booking.controller",
        "org.cardGGaduekMainService.product.rooms.controller",
        "org.cardGGaduekMainService.product.accommodation.controller",
        "org.cardGGaduekMainService.product.categoryPageContent.controller",
        "org.cardGGaduekMainService.card.controller",
        "org.cardGGaduekMainService.cardProduct.controller",
        "org.cardGGaduekMainService.cardPerformance.controller",
        "org.cardGGaduekMainService.cardSummary.controller",
        "org.cardGGaduekMainService.place.controller",
        "org.cardGGaduekMainService.main.controller",
        "org.cardGGaduekMainService.totalbenefit.controller",
        "org.cardGGaduekMainService.cardbenefit.controller",
        "org.cardGGaduekMainService.cardRecommend.controller",
        "org.cardGGaduekMainService.codef.controller",
})
@Configuration
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**") // url이 /resources/로 시작하는 모든 경로    // ** --> 하위 폴더를 모두 포함  * --> 해당 레벨에서만 포함
                .addResourceLocations("/resources/"); // webapp/resources/경로로 매핑
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 API 경로 허용
                .allowedOrigins("http://localhost:5173") // Vue 개발 서버 출처 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 요청 헤더 허용
                .allowCredentials(true); // 쿠키 포함 요청 허용 (필요 시)
    }
    // jsp view resolver 설정
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver(); // 템플릿 언어로 JSP엔진 사용하겟다
//
//        bean.setViewClass(JstlView.class);  // 템플릿 언어로 JSP엔진 사용하겟다
//        bean.setPrefix("/WEB-INF/views/");
//        bean.setSuffix(".jsp");
//
//        registry.viewResolver(bean);
//    }

    // /Users/seongbochoi/IdeaProjects/KB/08_spring/upload
    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver
                = new StandardServletMultipartResolver();
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // API 경로(/api/**)가 아닌 모든 요청을 index.html로 포워딩
        registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/index.html");
        registry.addViewController("/**/{path:[^\\.]*}").setViewName("forward:/index.html");
    }
}