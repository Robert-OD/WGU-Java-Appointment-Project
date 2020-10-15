package Utils;

import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDatabase {

    public static Country getCountry(int countryIdIn) throws SQLException {
        String sql = "SELECT * FROM country WHERE countryId = " + countryIdIn;
        Query.makeQuery(sql);
        Country countryRes;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int countryId = resultSet.getInt("countryId");
            String country = resultSet.getString("country");
            countryRes = new Country(countryId, country);
            return countryRes;
        }
        return null;
    }

    public static Country getCountry(String countryNameIn) throws SQLException {
        String sql = "SELECT * FROM country WHERE country = '" + countryNameIn + "'";
        Query.makeQuery(sql);
        Country countryRes;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int countryId = resultSet.getInt("countryId");
            String country = resultSet.getString("country");
            countryRes = new Country(countryId, country);
            return countryRes;
        }
        return null;
    }

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM country";
        Query.makeQuery(sql);
        Country countryRes;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int countryId = resultSet.getInt("countryId");
            String country = resultSet.getString("country");
            countryRes = new Country(countryId, country);
            customerList.add(countryRes);
        }
        return customerList;
    }
}
