package Controllers;

import Models.Appointment;
import Models.Customer;
import Utils.MainData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class CustomerFromApptController implements Initializable {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerIdCol;
    @FXML private TableColumn<Customer, String> customerNameCol;


    private Boolean returnAdd = true;
    private Appointment selectedApp;
    private Appointment changedApp;
    private LocalDate localDate;
    private String string;
    private ObservableList observableList;
    private ZonedDateTime time;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable.setItems(MainData.getCustomers());
    }

    @FXML
    private void backButton(ActionEvent event) throws IOException, SQLException {
        if(returnAdd){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentAdd.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene ((Pane) loader.load()));
            AppointmentAddController appointmentAddController = loader.<AppointmentAddController>getController();
            appointmentAddController.setUpAfterCustomerPicked(changedApp, localDate, string, observableList, time);
            stage.show();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentModify.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene ((Pane) loader.load()));
            AppointmentModifyController appointmentModifyController = loader.<AppointmentModifyController>getController();
            appointmentModifyController.setUpPickedCustomer(selectedApp, changedApp, localDate, string, observableList, time);
            stage.show();
        }
    }
    @FXML
    private void selectButton(ActionEvent event) throws IOException, SQLException {
        if(returnAdd){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentAdd.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene ((Pane) loader.load()));
            AppointmentAddController appointmentAddController = loader.<AppointmentAddController>getController();
            appointmentAddController.setCustomer(customerTable.getSelectionModel().getSelectedItem());
            try {
                appointmentAddController.setUpAfterCustomerPicked(changedApp, localDate, string, observableList, time);
            } catch (NullPointerException e){

            }
            stage.show();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentModify.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene ((Pane) loader.load()));
            AppointmentModifyController appointmentModifyController = loader.<AppointmentModifyController>getController();
            appointmentModifyController.setCustomer(customerTable.getSelectionModel().getSelectedItem());
            appointmentModifyController.setUpPickedCustomer(selectedApp, changedApp, localDate, string, observableList, time);
            stage.show();
        }
    }

    public void returnToAdd(){

    }
    public void returnToModify(){
        returnAdd = false;
    }
    public void setUp(Appointment changedAppointment){
        changedApp = changedAppointment;
    }
    public void setUp(Appointment changedAppointment, LocalDate localDateInput, String stringInput, ObservableList observableList, ZonedDateTime timeInput){
        changedApp = changedAppointment;
        localDate = localDateInput;
        string = stringInput;
        this.observableList = observableList;
        time = timeInput;
    }
    public void setUp(Appointment selectedAppointmentInput, Appointment changedAppointment, LocalDate localDateInput, String stringInput, ObservableList observableList, ZonedDateTime timeInput){
        selectedApp = selectedAppointmentInput;
        changedApp = changedAppointment;
        localDate = localDateInput;
        string = stringInput;
        this.observableList = observableList;
        time = timeInput;
    }
}
