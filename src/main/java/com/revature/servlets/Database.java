package com.revature.servlets;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Database {
    private static Database instance;
    private Connection conn;
    private final Properties props = new Properties();

    private Database() throws IOException {
//        try {
            props.load(new FileReader("/usr/local/tomcat/webapps/test-app/WEB-INF/classes/db.properties"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() throws IOException {
        if (instance == null) instance = new Database();
        return instance;
    }

    private Connection getConnection0() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
        }
        if (conn == null) throw new RuntimeException("Could not connect to the database.");
        return conn;
    }

    public static Connection getConnection() throws SQLException, IOException {
        return getInstance().getConnection0();
    }
}
