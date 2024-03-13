package com.Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DB {
    private static final String URL = "jdbc:sqlite:library.db";
    public static Connection connection;
    public static Statement statement;

    public static void createConnection() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection(URL);
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
