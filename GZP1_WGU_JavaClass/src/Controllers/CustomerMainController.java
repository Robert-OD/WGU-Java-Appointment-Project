package Controllers;

import Models.Customer;
import Utils.AddressDatabase;
import Utils.CustomerDatabase;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerMainController implements Initializable {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerIdCol;
    @FXML private TableColumn<Customer, String> customerNameCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable.setItems(MainData.getCustomers());
    }


    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerAdd.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        CustomerAddController controller = loader.<CustomerAddController>getController();
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
    void deleteCustomer(ActionEvent event) throws SQLException {
        Customer customerTemp = customerTable.getSelectionModel().getSelectedItem();
        if (customerTemp != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Are you sure you want to delete?");
            Optional<ButtonType> res = alert.showAndWait();
            if (res.get() == ButtonType.OK) {
                CustomerDatabase.deleteCustomer(customerTemp.getCustomerId());
                AddressDatabase.deleteAddress(customerTemp.getAddress().getAddressId());
                MainData.removeCustomer(customerTemp);
                MainData.removeAddress(customerTemp.getAddress());
                customerTable.setItems(MainData.getCustomers());
            }
        }
    }


    @FXML
    void modifyCustomer(ActionEvent event) throws IOException {
        if (customerTable.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerModify.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            CustomerModifyController controller = loader.<CustomerModifyController>getController();
            controller.setUp(customerTable.getSelectionModel().getSelectedItem());
            stage.show();
        }
    }
}
