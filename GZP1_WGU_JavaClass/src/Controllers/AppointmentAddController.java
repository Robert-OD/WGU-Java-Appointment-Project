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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentAddController implements Initializable {

    @FXML private Label customerLabel;
    @FXML private Label appointmentIdLabel;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField contactTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField urlTextField;
    @FXML private DatePicker dateDatePicker;
    @FXML private SplitMenuButton timeChoice;

    private ZonedDateTime selectedTime;
    private Customer selectedCustomer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentIdLabel.setText("" + (MainData.getAppointments().size() + 1));
        timeChoice.setText("Pick a Time");
    }

    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentMain.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene ((Pane) loader.load()));
            AppointmentMainController appointmentScreenController = loader.<AppointmentMainController>getController();
            stage.show();
        }
    }

    @FXML
    private void saveButton(ActionEvent event) throws IOException, Exception {
        ZoneId systemDefault = ZoneId.systemDefault();
        if (validEntries()) {

            ZonedDateTime startTime = ZonedDateTime.parse(ZonedDateTime.of(dateDatePicker.getValue() , LocalTime.from(selectedTime), systemDefault).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            ZonedDateTime endTime = ZonedDateTime.parse(ZonedDateTime.of(dateDatePicker.getValue() , LocalTime.from(selectedTime).plusMinutes(30), systemDefault).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            Appointment tempAppointment = new Appointment(
                    MainData.getAppointments().size() + 1,
                    selectedCustomer, MainData.getUser(),
                    titleTextField.getText(),
                    descriptionTextField.getText(),
                    locationTextField.getText(),
                    contactTextField.getText(),
                    typeTextField.getText(),
                    urlTextField.getText(),
                    startTime,
                    endTime);

            MainData.addAppointment(tempAppointment);
            AppointmentDatabase.addAppointment(tempAppointment);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentMain.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            AppointmentMainController appointmentScreenController = loader.<AppointmentMainController>getController();
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("One or more fields were left blank.");
            alert.setContentText("Please fill all fields before saving.");
            alert.showAndWait();
        }
    }

    @FXML
    private void selectCustomerBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerFromAppt.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene ((Pane) loader.load()));
        CustomerFromApptController customerFromApptController = loader.<CustomerFromApptController>getController();
        customerFromApptController.returnToAdd();
        try {
            Appointment tempAppointment = new Appointment(MainData.getAppointments().size() + 1, selectedCustomer, MainData.getUser(), titleTextField.getText(), descriptionTextField.getText(), locationTextField.getText(), contactTextField.getText(), typeTextField.getText(), urlTextField.getText(), null, null);
            customerFromApptController.setUp(tempAppointment, dateDatePicker.getValue(), timeChoice.getText(), timeChoice.getItems(), selectedTime);
        } catch (NullPointerException e){

        }
        stage.show();
    }

    public void setCustomer(Customer customer){
        selectedCustomer = customer;
        customerLabel.setText("" + customer.getCustomerName());
    }

    @FXML
    private void datePickerAction(ActionEvent event) {
        selectedTime = null;
        timeChoice.setText("Pick a Time");
        setDropdown();
    }

    public void setUpAfterCustomerPicked(Appointment changedAppointment, LocalDate localDate, String string, ObservableList observableList, ZonedDateTime time){
        appointmentIdLabel.setText("" + changedAppointment.getAppointmentId());
        if(changedAppointment.getCustomer() != null){
            selectedCustomer = changedAppointment.getCustomer();
            customerLabel.setText("" + selectedCustomer.getCustomerName());
        }
        if(changedAppointment.getTitle() != null){
            titleTextField.setText(changedAppointment.getTitle());
        }
        if(changedAppointment.getDescription() != null){
            descriptionTextField.setText(changedAppointment.getDescription());
        }
        if(changedAppointment.getType() != null){
            typeTextField.setText(changedAppointment.getType());
        }
        if(changedAppointment.getContact() != null){
            contactTextField.setText(changedAppointment.getContact());
        }
        if(changedAppointment.getLocation() != null){
            locationTextField.setText(changedAppointment.getLocation());
        }
        if(changedAppointment.getUrl() != null){
            urlTextField.setText(changedAppointment.getUrl());
        }
        dateDatePicker.setValue(localDate);
        timeChoice.setText(string);
        if(time != null){
            selectedTime = time;
        }
        if(observableList.size() > 0){
            setDropdown();
        }
    }
    private void setDropdown(){
        LocalDate localDate = dateDatePicker.getValue();
        try {
            ArrayList<ZonedDateTime> businessHours = TimeDateFormat.getBusinessHours(localDate);
            timeChoice.getItems().clear();
            for (int i = 0; i < businessHours.size(); i++) {
                ZonedDateTime tempLocalDateTime = businessHours.get(i);
                MenuItem choice = new MenuItem();
                timeChoice.getItems().add(choice);
                String minute = "" + tempLocalDateTime.getMinute();
                if (minute.length() == 1) {
                    minute = "0" + minute;
                }
                choice.setText(tempLocalDateTime.getHour() + ":" + minute);
                //Lambda Function Is used because the number of time can be changed.
                choice.setOnAction((e) -> {
                    String tempMinute = "" + tempLocalDateTime.getMinute();
                    if (tempMinute.length() == 1) {
                        tempMinute = "0" + tempMinute;
                    }
                    selectedTime = tempLocalDateTime;
                    timeChoice.setText(tempLocalDateTime.getHour() + ":" + tempMinute);
                });
            }

        }catch (NullPointerException | SQLException e){

        }
    }
    public Boolean validEntries(){
        if (titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || contactTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || urlTextField.getText().isEmpty() || dateDatePicker.getValue() == null || timeChoice.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
