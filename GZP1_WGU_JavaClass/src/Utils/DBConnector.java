package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String JDBC = "jdbc:mysql://wgudb.ucertify.com:3306/U07K0V?user=U07K0V";
    private static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    private static final String username = "U07K0V";
    private static final String password = "53689049140";

    public static Connection startConnection(){
        try {
            Class.forName(MYSQLJDBCDRIVER);
            connection = (Connection) DriverManager.getConnection(JDBC, username,password);
            Query.setConnection(connection);
        } catch (ClassNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
