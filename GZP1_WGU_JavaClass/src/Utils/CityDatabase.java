package Utils;

import Models.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDatabase {

    public static City getCity(int cityIdIn) throws SQLException {
        String sql = "SELECT * FROM city WHERE cityId = " + cityIdIn;
        Query.makeQuery(sql);
        City cityRes;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int cityId = resultSet.getInt("cityId");
            String cityName = resultSet.getString("city");
            int countryId = resultSet.getInt("countryId");
            cityRes = new City(cityId, cityName, CountryDatabase.getCountry(countryId));
            return cityRes;
        }
        return null;
    }

    public static City getCity(String cityNameIn) throws SQLException {
        String sql = "SELECT * FROM city WHERE city = " + cityNameIn;
        Query.makeQuery(sql);
        City cityRes;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int cityId = resultSet.getInt("cityId");
            String cityName = resultSet.getString("city");
            int countryId = resultSet.getInt("countryId");
            cityRes = new City(cityId, cityName, CountryDatabase.getCountry(countryId));
            return cityRes;
        }
        return null;
    }

    public static ObservableList<City> getAllCities() throws SQLException {
        ObservableList<City> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM city";
        Query.makeQuery(sql);
        City cityRes;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int cityId = resultSet.getInt("cityId");
            String cityName = resultSet.getString("city");
            int countryId = resultSet.getInt("countryId");
            cityRes = new City(cityId, cityName, CountryDatabase.getCountry(countryId));
            customerList.add(cityRes);
        }
        return customerList;
    }
}




















