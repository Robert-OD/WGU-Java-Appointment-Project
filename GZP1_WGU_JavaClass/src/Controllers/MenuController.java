package Controllers;

import Models.Appointment;
import Utils.AppointmentDatabase;
import Utils.MainData;
import Utils.TimeDateFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private TableView<Appointment> allAppTable;
    @FXML private TableColumn<Appointment, Integer> allIdCol;
    @FXML private TableColumn<Appointment, String> allTitleCol;
    @FXML private TableColumn<Appointment, String> allLocationCol;
    @FXML private TableColumn<Appointment, String> allContactCol;
    @FXML private TableColumn<Appointment, String> allTypeCol;
    @FXML private TableColumn<Appointment, Date> allTimeCol;

    @FXML private RadioButton weekRadioBtn;
    @FXML private RadioButton monthRadioBtn;
    @FXML private RadioButton allRadioBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        allTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        allLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        allContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        allTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        allTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        try {
            allAppTable.setItems(AppointmentDatabase.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allRadioBtn.setSelected(true);
        try {
            upcomingAppointment();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    void appointmentsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentMain.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        stage.show();
    }

    @FXML
    void customersButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerMain.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        stage.show();
    }

    @FXML
    void exitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Confirm exit.");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK){
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
    }

    public void upcomingAppointment() throws SQLException {
        ZonedDateTime beforeWarning = ZonedDateTime.now();
        ZonedDateTime afterWarning = ZonedDateTime.now().plusMinutes(15);
        TimeDateFormat.convertToDateTimeFormat(beforeWarning);
        for (int i = 1; i <= AppointmentDatabase.getAllAppointments().size(); i++){
            if (AppointmentDatabase.getAppointment(i) != null) {
                if (AppointmentDatabase.getAppointment(i).getStartTime().isAfter(beforeWarning) && AppointmentDatabase.getAppointment(i).getStartTime().isBefore(afterWarning) && AppointmentDatabase.getAppointment(i).getUser().getUserName().equals(MainData.getUser().getUserName())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setHeaderText("There is an appointment in the next 15 minutes.");
                    alert.setContentText("There is an appointment with: " + AppointmentDatabase.getAppointment(i).getCustomer().getCustomerName());
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void monthlyView(ActionEvent event) throws SQLException {
        monthRadioBtn.setSelected(true);
        weekRadioBtn.setSelected(false);
        allRadioBtn.setSelected(false);
        allAppTable.setItems(AppointmentDatabase.getMonthlyAppointments());
    }

    @FXML
    void weeklyView(ActionEvent event) throws SQLException {
        monthRadioBtn.setSelected(false);
        weekRadioBtn.setSelected(true);
        allRadioBtn.setSelected(false);
        allAppTable.setItems(AppointmentDatabase.getWeeklyAppointments());
    }
    @FXML
    void viewAllAppointments(ActionEvent event) throws SQLException {
        monthRadioBtn.setSelected(false);
        weekRadioBtn.setSelected(false);
        allRadioBtn.setSelected(true);
        allAppTable.setItems(AppointmentDatabase.getAllAppointments());
    }
    @FXML
    void reportsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        stage.show();
    }
}
