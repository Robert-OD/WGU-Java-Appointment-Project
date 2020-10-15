package Controllers;

import Models.Address;
import Models.City;
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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerModifyController implements Initializable {

    @FXML private TextField customerNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField address2TextField;
    @FXML private SplitMenuButton cityChoice;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField phoneTextField;
    @FXML private Label customerIdLabel;

    private Customer selectedCustomer;
    private int selectedCityId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 1; i <= MainData.getCities().size(); i++){
            City cityTemp = MainData.getCity(i);

            MenuItem choice = new MenuItem(cityTemp.getCityName()   );
            cityChoice.getItems().add(choice);
            //Lambda used because the # of cities could change
            choice.setOnAction((e) -> {
                selectedCityId = cityTemp.getCityId();
                cityChoice.setText(cityTemp.getCityName());
            });
        }
    }


    @FXML
    void CancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerMain.fxml"));
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        }
    }

    @FXML
    void saveButton(ActionEvent event) throws IOException {

        if (validEntries()) {
            Address addressTemp = selectedCustomer.getAddress();
            addressTemp.setAddress(addressTextField.getText());
            addressTemp.setAddress2(address2TextField.getText());
            addressTemp.setCity(MainData.getCity(selectedCityId));
            addressTemp.setPostalCode(postalCodeTextField.getText());
            addressTemp.setPhone(phoneTextField.getText());
            selectedCustomer.setCustomerName(customerNameTextField.getText());
            selectedCustomer.setAddress(addressTemp);
            AddressDatabase.updateAddress(addressTemp);
            CustomerDatabase.updateCustomer(selectedCustomer);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerMain.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("One or more fields were left blank.");
            alert.setContentText("Please fill all fields before saving.");
            alert.showAndWait();
        }
    }

    public void setUp(Customer customer){
        selectedCustomer = customer;
        customerIdLabel.setText(String.valueOf(selectedCustomer.getCustomerId()));
        customerNameTextField.setText(selectedCustomer.getCustomerName());
        addressTextField.setText(selectedCustomer.getAddress().getAddress());
        address2TextField.setText(selectedCustomer.getAddress().getAddress2());
        postalCodeTextField.setText(selectedCustomer.getAddress().getPostalCode());
        phoneTextField.setText(selectedCustomer.getAddress().getPhone());
        cityChoice.setText(selectedCustomer.getAddress().getCity().getCityName());
    }

    public Boolean validEntries(){
        if (customerNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || address2TextField.getText().isEmpty() || cityChoice.getText().isEmpty() || postalCodeTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
