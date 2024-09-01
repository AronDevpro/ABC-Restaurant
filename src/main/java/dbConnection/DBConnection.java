package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection con;
    private static DBConnection dbConnection;

    private final String URL = "jdbc:mysql://localhost:3306/Restaurants";
    private final String USER = "root";
    private final String PASSWORD = "1234";

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        return dbConnection== null? dbConnection=new DBConnection():dbConnection;
    }
    public Connection getConnection() {
        return con;
    }
}
