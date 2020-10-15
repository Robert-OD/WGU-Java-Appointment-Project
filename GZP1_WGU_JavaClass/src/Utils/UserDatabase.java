package Utils;

import Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {
    public static User getUser(int userIdIn) throws SQLException {
        String sql = "SELECT * FROM user WHERE userId = '" + userIdIn + "'";
        Query.makeQuery(sql);
        User userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int userId = resultSet.getInt("userId");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            userResult = new User(userId, username, password);
            return userResult;
        }
        return null;
    }

    public static User getUser(String usernameIn) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = '" + usernameIn + "'";
        Query.makeQuery(sql);
        User userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int userId = resultSet.getInt("userId");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            userResult = new User(userId, username, password);
            return userResult;
        }
        return null;
    }
}
