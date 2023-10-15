package com.music.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static final String PROPERTIES_FILE = "/database.properties";

    private static String DATABASE_URL;
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;
    private static BasicDataSource dataSource;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load PostgreSQL JDBC Driver.", e);
        }
    }


    static {
        try (InputStream is = JDBCUtils.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties props = new Properties();
            props.load(is);

            DATABASE_URL = props.getProperty("database.url");
            DATABASE_USER = props.getProperty("database.user");
            DATABASE_PASSWORD = props.getProperty("database.password");

            dataSource = new BasicDataSource();
            dataSource.setUrl(DATABASE_URL);
            dataSource.setUsername(DATABASE_USER);
            dataSource.setPassword(DATABASE_PASSWORD);
            // set the size of the connection poll
            dataSource.setInitialSize(10); // 初始连接数
            dataSource.setMaxTotal(100);    // 最大连接数


        } catch (Exception e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}
