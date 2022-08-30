package com.revature.utils.database;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;
    private Connection connection;
    
    static{
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            Properties properties = new Properties();
            try {
                FileReader reader = new FileReader("webapps/nathan-chris-p1/WEB-INF/classes/database/db.properties");
                properties.load(reader);
                reader.close();
                connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (connection == null) throw new RuntimeException("Could not establish connection with the database!");
        return connection;
    }
}