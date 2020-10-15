package Utils;

import Models.*;
import javafx.collections.ObservableList;

public class MainData {

    private static User user;
    private static ObservableList<Appointment> userAppointments;
    private static ObservableList<Appointment> appointments;
    private static ObservableList<Customer> customers;
    private static ObservableList<Address> addresses;
    private static ObservableList<City> cities;
    private static ObservableList<Country> countries;

    public static void setUser(User userIn){
        user = userIn;
    }
    public static void setUserAppointments(ObservableList<Appointment> userAppointmentsIn){
        userAppointments = userAppointmentsIn;
    }
    public static void setAppointments(ObservableList<Appointment> appointmentsIn){
        appointments = appointmentsIn;
    }
    public static void setCustomers(ObservableList<Customer> customersIn){
        customers = customersIn;
    }
    public static void setAddresses(ObservableList<Address> addressesIn){
        addresses = addressesIn;
    }
    public static void setCities(ObservableList<City> citiesIn){
        cities = citiesIn;
    }
    public static void setCountries(ObservableList<Country> countriesIn){
        countries = countriesIn;
    }
    public static void addUserAppointment(Appointment appointmentIn){
        userAppointments.add(appointmentIn);
    }
    public static void addAppointment(Appointment appointmentIn){
        appointments.add(appointmentIn);
    }
    public static void addCustomer(Customer customerIn){
        customers.add(customerIn);
    }
    public static void addAddress(Address addressIn){
        addresses.add(addressIn);
    }
    public static void removeUserAppointment(Appointment appointment){
        userAppointments.remove(appointment);
    }
    public static void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
    }
    public static void removeCustomer(Customer customerIndex){
        customers.remove(customerIndex);
    }
    public static void removeAddress(Address addressIndex){
        addresses.remove(addressIndex);
    }
    public static User getUser(){
        return user;
    }
    public static ObservableList<Appointment> getUserAppointments(){
        return userAppointments;
    }
    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }
    public static ObservableList<Customer> getCustomers(){
        return customers;
    }
    public static Customer getCustomer(int id){
        for (int i = 0; i < customers.size(); i++){
            if (customers.get(i).getCustomerId() == id){
                return customers.get(i);
            }
        }
        return null;
    }
    public static ObservableList<Address> getAddresses(){
        return addresses;
    }
    public static Address getAddress(int id){
        for (int i = 0; i < addresses.size(); i++){
            if (addresses.get(i).getAddressId() == id){
                return addresses.get(i);
            }
        }
        return null;
    }
    public static ObservableList<City> getCities(){
        return cities;
    }
    public static City getCity(int id){
        for (int i = 0; i < cities.size(); i++){
            if (cities.get(i).getCityId() == id){
                return cities.get(i);
            }
        }
        return null;
    }
    public static ObservableList<Country> getCountries(){
        return countries;
    }
    public static Country getCountry(int id){
        for (int i = 0; i < countries.size(); i++){
            if (countries.get(i).getCountryId() == id){
                return countries.get(i);
            }
        }
        return null;
    }
}
