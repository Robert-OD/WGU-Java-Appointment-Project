package Controllers;

import Utils.AppointmentDatabase;
import Utils.Query;
import Utils.TimeDateFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML private TextArea totalCustomerTxtField;
    @FXML private TextArea consultantScheduleTxtArea;
    @FXML private TextArea typeByMonthTxtArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentTypeByMonth();
            customerTotalAppointments();
            consultantSchedules();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
    public void appointmentTypeByMonth() throws SQLException {
            try {
                String query = "SELECT appointment.type, COUNT(*) as 'Total' FROM appointment GROUP BY type";
                Query.makeQuery(query);
                ResultSet results = Query.getResult();
                StringBuilder reportOneText = new StringBuilder();
                reportOneText.append(String.format("%1$-55s %2$-55s \n", "Appointment Type", "Total"));
                reportOneText.append(String.join("", Collections.nCopies(100, "_")));
                reportOneText.append("\n");
                while(results.next()) {
                    reportOneText.append(String.format("%1$-55s %2$-60s \n", results.getString("type"), results.getInt("Total")));
                }
                typeByMonthTxtArea.setText(reportOneText.toString());
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }


        //String typeArray[] = new String[AppointmentDatabase.getAllAppointments().size() + 1];
        //HashMap<String, Integer> map = new HashMap<>();
        //
        //for (int i = 1; i <= AppointmentDatabase.getAllAppointments().size(); i++){
        //    typeArray[i] = "";
        //    for (int j = 1; j <= AppointmentDatabase.getAllAppointments().size(); j++){
        //        typeArray[j] = AppointmentDatabase.getAppointment(j).getType();
        //    }
        //}
//
        //for (int h = 1; h < typeArray.length; h++){
        //    if (map.containsKey(typeArray[h])){
//
        //        map.put(typeArray[h], map.get(typeArray[h]) + 1);
//
        //    } else {
        //        map.put(typeArray[h], 1);
        //    }
        //}
        //typeByMonthTxtArea.appendText("Number and types of appointments: \n");
        //typeByMonthTxtArea.appendText(map.toString().replace("{", "").replace("}", ""));
    }


    public void consultantSchedules() throws SQLException {

        StringBuilder reportTwoText = new StringBuilder();
        reportTwoText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-25s \n",
                "Consultant", "Appointment", "Customer", "Start"));
        reportTwoText.append(String.join("", Collections.nCopies(100, "_")));
        reportTwoText.append("\n");
        consultantScheduleTxtArea.appendText(reportTwoText.toString());

        for (int i = 1; i <= AppointmentDatabase.getAllAppointments().size(); i++){
            if (AppointmentDatabase.getAppointment(i) != null){
                consultantScheduleTxtArea.appendText(AppointmentDatabase.getAppointment(i).getUser().getUserName() + "                           " + AppointmentDatabase.getAppointment(i).getAppointmentId() + "                                " + AppointmentDatabase.getAppointment(i).getCustomer().getCustomerName() + "                       " + TimeDateFormat.convertToLocalTime(AppointmentDatabase.getAppointment(i).getStartTime()) + "\n");
            } else {
                i++;
            }
        }

    }


    public void customerTotalAppointments() throws SQLException {

        String sql = "SELECT customer.customerName, COUNT(*) as 'Total' FROM customer JOIN appointment ON customer.customerId = appointment.customerId GROUP BY customerName";
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        StringBuilder customerTotalText = new StringBuilder();
        customerTotalText.append(String.format("%1$-65s %2$-65s \n", "Customer", "Total Appointments"));
        customerTotalText.append(String.join("", Collections.nCopies(100, "_")));
        customerTotalText.append("\n");
        while (rs.next()){
                customerTotalText.append(String.format("%1$s %2$65d \n", rs.getString("customerName"), rs.getInt("Total")));
        }
        totalCustomerTxtField.setText(customerTotalText.toString());
    }


}























