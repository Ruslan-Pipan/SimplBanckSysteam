package dao;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBank {
    private static final String URL = "jdbc:mysql://localhost:3306/bank?useSSL=false&useUnicode=true&serverTimezone=UTC";
    private static final String PASSWORD = "Ruslan12619";
    private static final String USER_NAME = "root";

    public static Connection getConn() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL,USER_NAME,PASSWORD);
    }
}