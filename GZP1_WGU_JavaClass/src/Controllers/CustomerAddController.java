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

public class CustomerAddController implements Initializable {

    @FXML private Button saveBtn;
    @FXML private TextField customerNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField address2TextField;
    @FXML private SplitMenuButton cityChoice;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField phoneTextField;
    @FXML private Label customerIdLabel;

    private int selectedCityId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIdLabel.setText(String.valueOf(MainData.getCustomers().size() + 1));
        cityChoice.setText("Pick a City");
        for (int i = 1; i <= MainData.getCities().size(); i++){
            City tempCity = MainData.getCity(i);
            MenuItem choice = new MenuItem(tempCity.getCityName());
            cityChoice.getItems().add(choice);
            choice.setOnAction((e) -> {
                selectedCityId = tempCity.getCityId();
                cityChoice.setText(tempCity.getCityName());
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            CustomerMainController controller = loader.<CustomerMainController>getController();
            stage.show();
        }
    }

    @FXML
    void saveButton(ActionEvent event) throws IOException {
        if (validEntries()) {
            Address addressTemp = new Address((MainData.getAddresses().size() + 1), addressTextField.getText(), address2TextField.getText(), MainData.getCity(selectedCityId), postalCodeTextField.getText(), phoneTextField.getText());
            Customer customerTemp = new Customer((MainData.getCustomers().size() + 1), customerNameTextField.getText(), addressTemp, true);
            AddressDatabase.addAddress(addressTemp);
            CustomerDatabase.addCustomer(customerTemp);
            MainData.addAddress(addressTemp);
            MainData.addCustomer(customerTemp);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerMain.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("One or more fields were left blank.");
            alert.setContentText("Please fill all fields before saving.");
            alert.showAndWait();
        }
    }

    public Boolean validEntries(){
        if (customerNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || address2TextField.getText().isEmpty() || cityChoice.getText().isEmpty() || postalCodeTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
