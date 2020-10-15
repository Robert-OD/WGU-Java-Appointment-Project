package Utils;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentDatabase {

    public static Appointment getAppointment(int appointmentIdIn) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE appointmentId = " + appointmentIdIn;
        Query.makeQuery(sql);
        Appointment userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int appointmentId = resultSet.getInt("appointmentId");
            int customerId = resultSet.getInt("customerId");
            int userId = resultSet.getInt("userId");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String location = resultSet.getString("location");
            String contact = resultSet.getString("contact");
            String type = resultSet.getString("type");
            String url = resultSet.getString("url");
            ZonedDateTime start = TimeDateFormat.stringToZonedDateTime(resultSet.getString("start"));
            ZonedDateTime end = TimeDateFormat.stringToZonedDateTime(resultSet.getString("end"));
            userResult = new Appointment(appointmentId, CustomerDatabase.getCustomer(customerId), UserDatabase.getUser(userId), title, description, location, contact, type, url, start, end);
            return userResult;
        }
        return null;
    }
    public static Appointment getAppointment(String appointmentTitleIn) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE title = '" + appointmentTitleIn + "'";
        Query.makeQuery(sql);
        Appointment userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int appointmentId = resultSet.getInt("appointmentId");
            int customerId = resultSet.getInt("customerId");
            int userId = resultSet.getInt("userId");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String location = resultSet.getString("location");
            String contact = resultSet.getString("contact");
            String type = resultSet.getString("type");
            String url = resultSet.getString("url");
            ZonedDateTime start = TimeDateFormat.stringToZonedDateTime(resultSet.getString("start"));
            ZonedDateTime end = TimeDateFormat.stringToZonedDateTime(resultSet.getString("end"));
            userResult = new Appointment(appointmentId, CustomerDatabase.getCustomer(customerId), UserDatabase.getUser(userId), title, description, location, contact, type, url, start, end);
            return userResult;
        }
        return null;
    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointment";
        Query.makeQuery(sql);
        Appointment userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int appointmentId = resultSet.getInt("appointmentId");
            int customerId = resultSet.getInt("customerId");
            int userId = resultSet.getInt("userId");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String location = resultSet.getString("location");
            String contact = resultSet.getString("contact");
            String type = resultSet.getString("type");
            String url = resultSet.getString("url");



            ZonedDateTime start = TimeDateFormat.convertToLocalTime(resultSet.getString("start"));
            ZonedDateTime end = TimeDateFormat.convertToLocalTime(resultSet.getString("end"));


            userResult = new Appointment(appointmentId, CustomerDatabase.getCustomer(customerId), UserDatabase.getUser(userId), title, description, location, contact, type, url,  start, end);

            customerList.add(userResult);
        }

        return customerList;
    }



    //make it work in UTC then focus on changing time zones



    public static ObservableList<Appointment> getAllUserAppointments(int userIdIn) throws SQLException {
        ObservableList<Appointment> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointment WHERE userId = " + userIdIn;
        Query.makeQuery(sql);
        Appointment userResult;
        ResultSet resultSet = Query.getResult();
        while (resultSet.next()){
            int appointmentId = resultSet.getInt("appointmentId");
            int customerId = resultSet.getInt("customerId");
            int userId = resultSet.getInt("userId");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String location = resultSet.getString("location");
            String contact = resultSet.getString("contact");
            String type = resultSet.getString("type");
            String url = resultSet.getString("url");
            ZonedDateTime start = TimeDateFormat.stringToZonedDateTime(resultSet.getString("start"));
            ZonedDateTime end = TimeDateFormat.stringToZonedDateTime(resultSet.getString("end"));
            userResult = new Appointment(appointmentId, CustomerDatabase.getCustomer(customerId), UserDatabase.getUser(userId), title, description, location, contact, type, url, start, end);
            customerList.add(userResult);
        }
        return customerList;
    }

    public static void addAppointment(Appointment appointmentIn){
        int appointmentId = appointmentIn.getAppointmentId();
        int customerId = appointmentIn.getCustomer().getCustomerId();
        int userId = appointmentIn.getUser().getUserId();
        String title = appointmentIn.getTitle();
        String description = appointmentIn.getDescription();
        String location = appointmentIn.getLocation();
        String contact = appointmentIn.getContact();
        String type = appointmentIn.getType();
        String url = appointmentIn.getUrl();

        //String start = TimeDateFormat.convertToDateTimeFormat(appointmentIn.getStartTime());
        //String end = TimeDateFormat.convertToDateTimeFormat(appointmentIn.getEndTime());

        String start = TimeDateFormat.convertToDateTimeFormat(TimeDateFormat.convertToUTC(appointmentIn.getStartTime()));
        String end = TimeDateFormat.convertToDateTimeFormat(TimeDateFormat.convertToUTC(appointmentIn.getEndTime()));

        ZonedDateTime localDateTime = ZonedDateTime.now();
        String time = TimeDateFormat.convertToDateTimeFormat(localDateTime);
        String sql = "INSERT INTO appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (" +
                appointmentId + ", " + customerId + ", " + userId + ", '" + title + "', '" + description + "', '" + location + "', '" + contact + "', '" + type + "', '" + url + "', '" + start + "', '" + end + "', '" + time + "', '" + MainData.getUser().getUserName() + "', '" + time + "', '" + MainData.getUser().getUserName() + "');";
        Query.makeQuery(sql);
    }

    public static void updateAppointment(Appointment appointmentIn){
        int appointmentId = appointmentIn.getAppointmentId();


        int customerId = appointmentIn.getCustomer().getCustomerId();
        int userId = appointmentIn.getUser().getUserId();
        String title = appointmentIn.getTitle();
        String description = appointmentIn.getDescription();
        String location = appointmentIn.getLocation();
        String contact = appointmentIn.getContact();
        String type = appointmentIn.getType();
        String url = appointmentIn.getUrl();

        String start = TimeDateFormat.convertToDateTimeFormat(appointmentIn.getStartTime());
        String startRep = start.replace("T", " ");

        String end = TimeDateFormat.convertToDateTimeFormat(appointmentIn.getEndTime());
        ZonedDateTime localDateTime = ZonedDateTime.now();
        String time = TimeDateFormat.convertToDateTimeFormat(localDateTime);
        String sql = "UPDATE appointment SET appointmentId = " + appointmentId + ", customerId = " + customerId + ", userId = " + userId +
                ", title = '" + title + "', description = '" + description + "', location = '" + location + "', contact = '" + contact +
                "', type = '" + type + "', url = '" + url + "', start = '" + startRep + "', end = '" + end + "', lastUpdate = '" + time +
                "', lastUpdateBy = '" + MainData.getUser().getUserName() + "' WHERE appointmentId = " + appointmentId + ";";
        Query.makeQuery(sql);
    }

    public static void deleteAppointment(int appointmentId){
        String sql = "DELETE FROM appointment WHERE appointmentId = " + appointmentId + ";";
        Query.makeQuery(sql);
    }

    public static ObservableList<Appointment> getWeeklyAppointments() throws SQLException {
        ObservableList<Appointment> weekList = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of(ZoneId.systemDefault().toString());
        ZonedDateTime begin = ZonedDateTime.of(now, zoneId);
        ZonedDateTime endWeek = ZonedDateTime.of(now.plusWeeks(1), zoneId);
        Appointment userResult;
        String sql = "SELECT * FROM appointment";
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();

        for (int i = 1; i <= AppointmentDatabase.getAllAppointments().size(); i++){
            if (AppointmentDatabase.getAppointment(i) != null) {
                if (AppointmentDatabase.getAppointment(i).getStartTime().isAfter(begin) && AppointmentDatabase.getAppointment(i).getStartTime().isBefore(endWeek)) {
                    while (rs.next()) {
                        int appointmentId = rs.getInt("appointmentId");
                        int customerId = rs.getInt("customerId");
                        int userId = rs.getInt("userId");
                        String title = rs.getString("title");
                        String description = rs.getString("description");
                        String location = rs.getString("location");
                        String contact = rs.getString("contact");
                        String type = rs.getString("type");
                        String url = rs.getString("url");
                        ZonedDateTime start = TimeDateFormat.stringToZonedDateTime(rs.getString("start"));
                        ZonedDateTime end = TimeDateFormat.stringToZonedDateTime(rs.getString("end"));
                        userResult = new Appointment(appointmentId, CustomerDatabase.getCustomer(customerId), UserDatabase.getUser(userId), title, description, location, contact, type, url, start, end);
                        if (userResult.getStartTime().isBefore(endWeek) && userResult.getStartTime().isAfter(begin)) {
                            weekList.add(userResult);
                        }
                    }
                }
            }
        }
        return weekList;
    }
    public static ObservableList<Appointment> getMonthlyAppointments() throws SQLException {
        ObservableList<Appointment> monthList = FXCollections.observableArrayList();

        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of(ZoneId.systemDefault().toString());
        ZonedDateTime begin = ZonedDateTime.of(now, zoneId);
        ZonedDateTime endMonth = ZonedDateTime.of(now.plusMonths(1), zoneId);

        Appointment userResult;
        String sql = "SELECT * FROM appointment";
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        for (int i = 1; i <= AppointmentDatabase.getAllAppointments().size(); i++){
            if (AppointmentDatabase.getAppointment(i) != null) {
                if (AppointmentDatabase.getAppointment(i).getStartTime().isAfter(begin) && AppointmentDatabase.getAppointment(i).getStartTime().isBefore(endMonth)) {
                    while (rs.next()) {
                        int appointmentId = rs.getInt("appointmentId");
                        int customerId = rs.getInt("customerId");
                        int userId = rs.getInt("userId");
                        String title = rs.getString("title");
                        String description = rs.getString("description");
                        String location = rs.getString("location");
                        String contact = rs.getString("contact");
                        String type = rs.getString("type");
                        String url = rs.getString("url");
                        ZonedDateTime start = TimeDateFormat.stringToZonedDateTime(rs.getString("start"));
                        ZonedDateTime end = TimeDateFormat.stringToZonedDateTime(rs.getString("end"));
                        userResult = new Appointment(appointmentId, CustomerDatabase.getCustomer(customerId), UserDatabase.getUser(userId), title, description, location, contact, type, url, start, end);
                        if (userResult.getStartTime().isBefore(endMonth) && userResult.getStartTime().isAfter(begin)) {
                            monthList.add(userResult);
                        }
                    }
                }
            }
        }
        return monthList;
    }
}