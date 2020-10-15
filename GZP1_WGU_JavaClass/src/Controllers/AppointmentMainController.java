package Controllers;

import Models.Appointment;
import Utils.AppointmentDatabase;
import Utils.MainData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentMainController implements Initializable {

    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> idCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> locationCol;
    @FXML private TableColumn<Appointment, String> contactCol;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, Date> timeCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentTable.setItems(MainData.getAppointments());
    }


    @FXML
    void addAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentAdd.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        AppointmentAddController controller = loader.<AppointmentAddController>getController();
        stage.show();
    }

    @FXML
    void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        MenuController controller = loader.<MenuController>getController();
        stage.show();
    }

    @FXML
    void deleteAppointment(ActionEvent event) {
        Appointment appointmentTemp = appointmentTable.getSelectionModel().getSelectedItem();
        if (appointmentTemp != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Confirmation");
            alert.setContentText("Are you sure you would like to delete this?");
            Optional<ButtonType> res = alert.showAndWait();
            if (res.get() == ButtonType.OK){
                MainData.removeAppointment(appointmentTemp);
                appointmentTable.setItems(MainData.getUserAppointments());
                AppointmentDatabase.deleteAppointment(appointmentTemp.getAppointmentId());
                appointmentTable.setItems(MainData.getAppointments());
            }
        }
    }

    @FXML
    void modifyAppointment(ActionEvent event) throws IOException, SQLException {
        Appointment appointmentTemp = appointmentTable.getSelectionModel().getSelectedItem();
        if (appointmentTemp != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentModify.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            AppointmentModifyController controller = loader.<AppointmentModifyController>getController();
            controller.setUp(appointmentTable.getSelectionModel().getSelectedItem());
            stage.show();
        }
    }
}
