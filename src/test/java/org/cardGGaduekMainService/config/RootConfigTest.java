package org.cardGGaduekMainService.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class RootConfigTest {

    @Test
    public void testConnection() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        DataSource dataSource = context.getBean(DataSource.class);

        try (Connection conn = dataSource.getConnection()) {
            System.out.println("DB 연결 성공: " + conn);
        } catch (Exception e) {
            System.out.println("DB 연결 실패");
            e.printStackTrace();
        }
    }

    @Test
    void sqlSessionFactory() {
    }

    @Test
    void transactionManager() {
    }
}