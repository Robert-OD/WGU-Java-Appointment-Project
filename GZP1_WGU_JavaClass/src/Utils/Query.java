package Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet result;
    private static Connection connection;

    public static void setConnection(Connection connectionIn){
        connection = connectionIn;
    }

    public static void makeQuery(String queryIn){
        query = queryIn;
        try {
            statement = connection.createStatement();
            if (query.toLowerCase().startsWith("select"))
                result = statement.executeQuery(query);
            if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update"))
                statement.executeUpdate(query);
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ResultSet getResult(){
        return result;
    }
}
