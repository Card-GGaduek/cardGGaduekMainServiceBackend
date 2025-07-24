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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// 일반 설정
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:/application.properties"})
@MapperScan(basePackages = {
        "org.cardGGaduekMainService.member.mapper", 
        "org.cardGGaduekMainService.lab.mapper", 
        "org.cardGGaduekMainService.transaction.mapper",
        "org.cardGGaduekMainService.store.mapper",
        "org.cardGGaduekMainService.notification.mapper"
})
@ComponentScan(basePackages = {
        "org.cardGGaduekMainService.member.service",
        "org.cardGGaduekMainService.common.util",
        "org.cardGGaduekMainService.auth",
        "org.cardGGaduekMainService.lab.service",
        "org.cardGGaduekMainService.transaction.service",
        "org.cardGGaduekMainService.store.service",
        "org.cardGGaduekMainService.common.mail.service"
})
public class RootConfig {
    @Value("${jdbc.driver}") String driver;
    @Value("${jdbc.url}") String url;
    @Value("${jdbc.username}") String username;
    @Value("${jdbc.password}") String password;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/*.xml"));
        sqlSessionFactory.setDataSource(dataSource());
        return (SqlSessionFactory) sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());

        return manager;
    }

}
