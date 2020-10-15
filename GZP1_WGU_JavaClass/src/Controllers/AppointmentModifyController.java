package Controllers;

import Models.Appointment;
import Models.Customer;
import Utils.AppointmentDatabase;
import Utils.MainData;
import Utils.TimeDateFormat;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentModifyController implements Initializable {

    @FXML private Label appointmentIdLabel;
    @FXML private Label customerLabel;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField contactTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField urlTextField;
    @FXML private DatePicker dateDatePicker;
    @FXML private SplitMenuButton timeChoice;

    private Customer selectedCustomer;
    private Appointment selectedAppointment;
    private ZonedDateTime selectedTime;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel Confirmation");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentMain.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        }
    }

    @FXML
    void saveButton(ActionEvent event) throws Exception {

        ZoneId systemDefault = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");

        ZonedDateTime start = ZonedDateTime.of(dateDatePicker.getValue() , LocalTime.from(selectedTime), systemDefault);
        ZonedDateTime end = ZonedDateTime.of(dateDatePicker.getValue() , LocalTime.from(selectedTime).plusMinutes(30), systemDefault);




        if(validEntries()) {
            selectedAppointment.setCustomer(selectedCustomer);
            selectedAppointment.setTitle(titleTextField.getText());
            selectedAppointment.setDescription(descriptionTextField.getText());
            selectedAppointment.setLocation(locationTextField.getText());
            selectedAppointment.setContact(contactTextField.getText());
            selectedAppointment.setType(typeTextField.getText());
            selectedAppointment.setUrl(urlTextField.getText());
            selectedAppointment.setStartTime(start);
            selectedAppointment.setEndTime(end);
            AppointmentDatabase.updateAppointment(selectedAppointment);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentMain.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("One or more fields were left blank.");
            alert.setContentText("Please fill all fields before saving.");
            alert.showAndWait();
        }
    }

    @FXML
    void selectCustomerBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerFromAppt.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        CustomerFromApptController controller = loader.<CustomerFromApptController>getController();
        controller.returnToModify();
        ZoneId systemDefault = ZoneId.systemDefault();
        Appointment appointmentTemp = new Appointment(selectedAppointment.getAppointmentId(), selectedCustomer, MainData.getUser(), titleTextField.getText(), descriptionTextField.getText(), locationTextField.getText(), contactTextField.getText(), typeTextField.getText(), urlTextField.getText(), null, null);
        controller.setUp(selectedAppointment, appointmentTemp, dateDatePicker.getValue(), timeChoice.getText(), timeChoice.getItems(), ZonedDateTime.of(dateDatePicker.getValue() , LocalTime.from(selectedTime), systemDefault));
        stage.show();
    }

    public void setUpPickedCustomer(Appointment modInput, Appointment changedAppointment, LocalDate localDate, String string, ObservableList observableList, ZonedDateTime zonedDateTime) throws SQLException {
        selectedAppointment = modInput;
        appointmentIdLabel.setText(String.valueOf(changedAppointment.getAppointmentId()));
        if (changedAppointment.getCustomer() != null){
            selectedCustomer = changedAppointment.getCustomer();
            customerLabel.setText(selectedCustomer.getCustomerName());
        }
        if (changedAppointment.getTitle() != null){
            titleTextField.setText(changedAppointment.getTitle());
        }
        if (changedAppointment.getDescription() != null){
            descriptionTextField.setText(changedAppointment.getDescription());
        }
        if (changedAppointment.getType() != null){
            typeTextField.setText(changedAppointment.getType());
        }
        if (changedAppointment.getContact() != null){
            contactTextField.setText(changedAppointment.getContact());
        }
        if (changedAppointment.getLocation() != null){
            locationTextField.setText(changedAppointment.getLocation());
        }
        if (changedAppointment.getUrl() != null){
            urlTextField.setText(changedAppointment.getUrl());
        }
        dateDatePicker.setValue(localDate);
        timeChoice.setText(string);
        if (zonedDateTime != null){
            selectedTime = zonedDateTime;
        }
        if (observableList.size() > 0){
            setDropdown();
        }
    }

    public void setUp(Appointment appointment) throws SQLException {
        selectedAppointment = appointment;
        appointmentIdLabel.setText(String.valueOf(appointment.getAppointmentId()));
        selectedCustomer = appointment.getCustomer();
        customerLabel.setText(selectedCustomer.getCustomerName());
        titleTextField.setText(appointment.getTitle());
        descriptionTextField.setText(appointment.getDescription());
        typeTextField.setText(appointment.getType());
        contactTextField.setText(appointment.getContact());
        locationTextField.setText(appointment.getLocation());
        urlTextField.setText(appointment.getUrl());
        dateDatePicker.setValue(selectedAppointment.getStartTime().toLocalDate());
        selectedTime = selectedAppointment.getStartTime();
        String minute = "" + selectedTime.getMinute();
        if (minute.length() == 1){
            minute = "0" + minute;
        }
        timeChoice.setText(selectedTime.getHour() + ":" + minute);
        setDropdown();
    }

    private void setDropdown() throws SQLException {
        LocalDate localDate = dateDatePicker.getValue();
        ArrayList<ZonedDateTime> busHours = TimeDateFormat.getBusinessHours(localDate);
        timeChoice.getItems().clear();
        for (int i = 0; i < busHours.size(); i++){
            ZonedDateTime zonedDateTime = busHours.get(i);

            MenuItem choice = new MenuItem();
            timeChoice.getItems().add(choice);
            String minute = "" + zonedDateTime.getMinute();
            if (minute.length() == 1){
                minute = "0" + minute;
            }
            choice.setText(zonedDateTime.getHour() + ":" + minute);
            choice.setOnAction((e) -> {
                String temp = "" + zonedDateTime.getMinute();
                if (temp.length() == 1){
                    temp = "0" + temp;
                }
                selectedTime = zonedDateTime;
                timeChoice.setText(zonedDateTime.getHour() + ":" + temp);
            });
        }
    }

    public void setCustomer(Customer customer){
        selectedCustomer = customer;
        customerLabel.setText(customer.getCustomerName());
    }

    public Boolean validEntries(){
        if (titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || contactTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || urlTextField.getText().isEmpty() || dateDatePicker.getValue() == null || timeChoice.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
