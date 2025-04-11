package com.p1g14.pomodoro_timer_api;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("Connected to: " + metaData.getURL());
            System.out.println("DB User: " + metaData.getUserName());
            System.out.println("DB Product: " + metaData.getDatabaseProductName());
            System.out.println("DB Version: " + metaData.getDatabaseProductVersion());
        }

        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        System.out.println("Simple Query Test: SELECT 1 -> " + result);
    }
}
