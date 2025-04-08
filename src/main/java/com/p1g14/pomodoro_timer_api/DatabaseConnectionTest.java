package com.p1g14.pomodoro_timer_api;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;


public class DataseConnectionTest implements CommandLineRunner{

private final DataSource dataSource;

public DatabaseConnectionTest(DataSource dataSource) {
    this.dataSource = dataSource;
}

@Override
public void run(String... args) throws Exception {
    try (Connection conn = dataSource.getConnection()) {
        System.out.println("Connection successfully: " + conn.getMetaData().getURL());
    } catch (Exception e) {
        System.err.println("Connection unsuccessfully: " + e.getMessage());
    }
}
}