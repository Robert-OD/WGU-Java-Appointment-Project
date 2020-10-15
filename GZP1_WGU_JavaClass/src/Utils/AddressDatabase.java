package Utils;

import Models.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

public class AddressDatabase {

    public static Address getAddress(int addressIdIn) throws SQLException {
        String sql = "SELECT * FROM address WHERE addressId = '" + addressIdIn + "'";
        Query.makeQuery(sql);
        Address addressResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int addressId = resultSet.getInt("addressId");
            String address = resultSet.getString("address");
            String address2 = resultSet.getString("address2");
            int cityId = resultSet.getInt("cityId");
            String postalCode = resultSet.getString("postalCode");
            String phone = resultSet.getString("phone");

            addressResult = new Address(addressId, address, address2, MainData.getCity(cityId), postalCode, phone);
            return addressResult;
        }
        return null;
    }

    public static Address getAddress(String addressNameIn) throws SQLException {
        String sql = "SELECT * FROM address WHERE addressName = '" + addressNameIn + "'";
        Query.makeQuery(sql);
        Address addressResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int addressId = resultSet.getInt("addressId");
            String address = resultSet.getString("address");
            String address2 = resultSet.getString("address2");
            int cityId = resultSet.getInt("cityId");
            String postalCode = resultSet.getString("postalCode");
            String phone = resultSet.getString("phone");

            addressResult = new Address(addressId, address, address2, MainData.getCity(cityId), postalCode, phone);
            return addressResult;
        }
        return null;
    }

    public static ObservableList<Address> getAllAddresses() throws SQLException {
        ObservableList<Address> addressList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM address";
        Query.makeQuery(sql);
        Address addressResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int addressId = resultSet.getInt("addressId");
            String address = resultSet.getString("address");
            String address2 = resultSet.getString("address2");
            int cityId = resultSet.getInt("cityId");
            String postalCode = resultSet.getString("postalCode");
            String phone = resultSet.getString("phone");
            addressResult = new Address(addressId, address, address2, MainData.getCity(cityId), postalCode, phone);
            addressList.add(addressResult);
        }
        return addressList;
    }

    public static void addAddress(Address addressIn){
        int addressId = addressIn.getAddressId();
        String address = addressIn.getAddress();
        String address2 = addressIn.getAddress2();
        int cityId = addressIn.getCity().getCityId();
        String postalCode = addressIn.getPostalCode();
        String phone = addressIn.getPhone();
        ZonedDateTime dateTime = ZonedDateTime.now();
        String time = TimeDateFormat.convertToDateTimeFormat(dateTime);
        String sql = "INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (" + addressId +", ' " + address + "', '" + address2 + "', " + cityId + ", '" + postalCode + "', '" + phone + "', '" + time + "', '" + MainData.getUser().getUserName() + "', '" + time + "', '" + MainData.getUser().getUserName() + "');";
        Query.makeQuery(sql);
    }

    public static void updateAddress(Address addressIn){
        int addressId = addressIn.getAddressId();
        String address = addressIn.getAddress();
        String address2 = addressIn.getAddress2();
        int cityId = addressIn.getCity().getCityId();
        String postalCode = addressIn.getPostalCode();
        String phone = addressIn.getPhone();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String time = TimeDateFormat.convertToDateTimeFormat(zonedDateTime);
        String sql = "UPDATE address SET address = '" + address + "', address2 = '" + address2 + "', cityId = " + cityId + ", postalCode = '" + postalCode + "', phone = '" + phone + "', lastUpdate = '" + time + "', lastUpdateBy = '" + MainData.getUser().getUserName() + "' WHERE addressId = " + addressId + ";";
        Query.makeQuery(sql);
    }

    public static void deleteAddress(int addressId){
        String sql = "DELETE FROM address WHERE addressId = " + addressId + ";";
        Query.makeQuery(sql);
    }

}



















