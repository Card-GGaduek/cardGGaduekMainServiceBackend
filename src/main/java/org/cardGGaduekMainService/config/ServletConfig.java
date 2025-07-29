package org.cardGGaduekMainService.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
        "org.cardGGaduekMainService.common.mail.controller",
        "org.cardGGaduekMainService.coupon.memberCoupon.controller",
        "org.cardGGaduekMainService.card.controller",
        "org.cardGGaduekMainService.product.booking.controller"
        "org.cardGGaduekMainService.cardPerformance.controller",
        "org.cardGGaduekMainService.cardSummary.controller",
})
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**") // url이 /resources/로 시작하는 모든 경로    // ** --> 하위 폴더를 모두 포함  * --> 해당 레벨에서만 포함
                .addResourceLocations("/resources/"); // webapp/resources/경로로 매핑
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

}