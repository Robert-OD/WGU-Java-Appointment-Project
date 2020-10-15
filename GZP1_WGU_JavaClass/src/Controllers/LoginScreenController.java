package Controllers;

import Models.User;
import Utils.*;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordTextField;

    @FXML private Label loginLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button loginButton;
    @FXML private Label languageMessage;
    @FXML private Label messageLabel;

    private String errorHeader;
    private String errorTitle;
    private String errorText;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainData.setCountries(CountryDatabase.getAllCountries());
            MainData.setCities(CityDatabase.getAllCities());
            Locale locale = Locale.getDefault();
            rb = ResourceBundle.getBundle("Languages/login", locale);
            usernameLabel.setText(rb.getString("username") + ":");
            userNameTextField.setPromptText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password") + ":");
            passwordTextField.setPromptText(rb.getString("password"));
            loginLabel.setText(rb.getString("login"));
            loginButton.setText(rb.getString("login"));
            languageMessage.setText(rb.getString("language"));
            messageLabel.setText(rb.getString("message"));
            errorTitle = rb.getString("errortitle");
            errorHeader = rb.getString("errorheader");
            errorText = rb.getString("errortext");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    void loginButton(ActionEvent event) throws SQLException, IOException {
        if (userPassMatch(userNameTextField.getText(), passwordTextField.getText())){
            logInSuccess(event, userNameTextField.getText());
            Logger.loginLogger(MainData.getUser().getUserName());
        } else {
            Alert wrongPass = new Alert(Alert.AlertType.ERROR);
            wrongPass.setTitle(errorTitle);
            wrongPass.setHeaderText(errorHeader);
            wrongPass.setContentText(errorText);
            wrongPass.showAndWait();
            logInFail();
        }
    }
    private Boolean userPassMatch(String username, String password) throws SQLException {
        User res = UserDatabase.getUser(username);
        if(res != null){
            return password.equals(UserDatabase.getUser(username).getPassword());
        }
        return false;
    }
    private void logInSuccess(ActionEvent event, String usernameIn) throws SQLException, IOException {
        MainData.setUser(UserDatabase.getUser(usernameIn));
        MainData.setAddresses(AddressDatabase.getAllAddresses());
        MainData.setCustomers(CustomerDatabase.getAllCustomers());
        MainData.setAppointments(AppointmentDatabase.getAllAppointments());
        MainData.setUserAppointments(AppointmentDatabase.getAllUserAppointments(MainData.getUser().getUserId()));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Pane) loader.load()));
        stage.show();
    }
    private void logInFail(){
        userNameTextField.setText("");
        passwordTextField.setText("");
    }
}
