package Utils;

import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

public class CustomerDatabase {
    public static Customer getCustomer(int customerIdIn) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId = '" + customerIdIn + "'";
        Query.makeQuery(sql);
        Customer userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int customerId = resultSet.getInt("customerId");
            String customerName = resultSet.getString("customerName");
            int addressId = resultSet.getInt("addressId");
            int activeInt = resultSet.getInt("active");
            Boolean active = false;
            if (activeInt == 1){
                active = true;
            }
            userResult = new Customer(customerId, customerName, AddressDatabase.getAddress(addressId), active);
            return userResult;
        }
        return null;
    }

    public static Customer getCustomer(String customerNameIn) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerName = '" + customerNameIn + "'";
        Query.makeQuery(sql);
        Customer userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int customerId = resultSet.getInt("customerId");
            String customerName = resultSet.getString("customerName");
            int addressId = resultSet.getInt("addressId");
            int activeInt = resultSet.getInt("active");
            Boolean active = false;
            if (activeInt == 1){
                active = true;
            }
            userResult = new Customer(customerId, customerName, AddressDatabase.getAddress(addressId), active);
            return userResult;
        }
        return null;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";
        Query.makeQuery(sql);
        Customer customerRes;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int customerId = resultSet.getInt("customerId");
            String customerName = resultSet.getString("customerName");
            int addressId = resultSet.getInt("addressId");
            int activeInt = resultSet.getInt("active");
            Boolean active = false;
            if (activeInt == 1){
                active = true;
            }
            customerRes = new Customer(customerId, customerName, AddressDatabase.getAddress(addressId), active);
            customerList.add(customerRes);
        }
        return customerList;
    }

    public static void addCustomer(Customer customer){
        int customerId = customer.getCustomerId();
        String customerName = customer.getCustomerName();
        int addressId = customer.getAddress().getAddressId();
        Boolean active = customer.getActive();
        int activeInt = 0;
        if (active){
            activeInt = 1;
        }
        ZonedDateTime localDateTime = ZonedDateTime.now();
        String time = TimeDateFormat.convertToDateTimeFormat(localDateTime);
        String sql = "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (" +
                customerId + ", '" + customerName + "', " + addressId + ", " + activeInt + ", '" + time + "', '" + MainData.getUser().getUserName() + "', '" + time + "', '" + MainData.getUser().getUserName() + "');";
        Query.makeQuery(sql);
    }

    public static void updateCustomer(Customer customerIn){
        String customerName = customerIn.getCustomerName();
        int addressId = customerIn.getAddress().getAddressId();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String time = TimeDateFormat.convertToDateTimeFormat(zonedDateTime);
        String sql = "UPDATE customer SET customerName = '" + customerName + "', addressId = " + addressId + ", lastUpdate = '" + time + "', lastUpdateBy = '" + MainData.getUser().getUserName() + "' WHERE customerId = " + customerIn.getCustomerId() + ";";
        Query.makeQuery(sql);
    }

    public static void deleteCustomer(int customerId){
        String sql = "DELETE FROM customer WHERE customerId = " + customerId;
        Query.makeQuery(sql);
    }
}
