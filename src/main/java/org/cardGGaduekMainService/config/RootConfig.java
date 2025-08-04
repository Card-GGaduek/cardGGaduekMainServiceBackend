package org.cardGGaduekMainService.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

// 일반 설정
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:/application.properties"})
@EnableScheduling
@MapperScan(basePackages = {
        "org.cardGGaduekMainService.member.mapper",
        "org.cardGGaduekMainService.cardbenefit.mapper",
        "org.cardGGaduekMainService.lab.mapper",
        "org.cardGGaduekMainService.transaction.mapper",
        "org.cardGGaduekMainService.store.mapper",
        "org.cardGGaduekMainService.notification.mapper",
        "org.cardGGaduekMainService.coupon.memberCoupon.mapper",
        "org.cardGGaduekMainService.coupon.couponProduct.mapper",
        "org.cardGGaduekMainService.cardProduct.mapper",
        "org.cardGGaduekMainService.card.mapper",
        "org.cardGGaduekMainService.card.benefit.mapper",
        "org.cardGGaduekMainService.product.booking.mapper",
        "org.cardGGaduekMainService.product.rooms.mapper",
        "org.cardGGaduekMainService.product.accommodation.mapper",
        "org.cardGGaduekMainService.product.categoryPageContent.mapper",
        "org.cardGGaduekMainService.cardPerformance.mapper",
        "org.cardGGaduekMainService.cardSummary.mapper",
        "org.cardGGaduekMainService.main.mapper",
        "org.cardGGaduekMainService.totalbenefit.mapper",
})
@ComponentScan(basePackages = {
        "org.cardGGaduekMainService.member.service",
        "org.cardGGaduekMainService.common.util",
        "org.cardGGaduekMainService.auth",
        "org.cardGGaduekMainService.payment.service",
        "org.cardGGaduekMainService.common.mail.service",
        "org.cardGGaduekMainService.lab.service",
        "org.cardGGaduekMainService.transaction.service",
        "org.cardGGaduekMainService.store.service",
        "org.cardGGaduekMainService.common.mail.service",
        "org.cardGGaduekMainService.coupon.couponProduct.service",
        "org.cardGGaduekMainService.coupon.memberCoupon.service",
        "org.cardGGaduekMainService.notification.service",
        "org.cardGGaduekMainService.card.service",
        "org.cardGGaduekMainService.card.benefit.service",
        "org.cardGGaduekMainService.cardbenefit.service",
        "org.cardGGaduekMainService.product.booking.service",
        "org.cardGGaduekMainService.product.categoryPageContent.service",
        "org.cardGGaduekMainService.product.rooms.service",
        "org.cardGGaduekMainService.product.accommodation.service",
        "org.cardGGaduekMainService.cardPerformance.service",
        "org.cardGGaduekMainService.cardSummary.service",
        "org.cardGGaduekMainService.cardProduct.service",
        "org.cardGGaduekMainService.place.service",
        "org.cardGGaduekMainService.main.service",
        "org.cardGGaduekMainService.totalbenefit.service",
})
public class RootConfig {

    @Value("${jdbc.driver}") String driver;
    @Value("${jdbc.url}") String url;
    @Value("${jdbc.username}") String username;
    @Value("${jdbc.password}") String password;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactory.setDataSource(dataSource());
      
        return (SqlSessionFactory) sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}

