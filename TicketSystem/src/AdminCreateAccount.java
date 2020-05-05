import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class AdminCreateAccount implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private TextField forenameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private ChoiceBox roleChoiceBox;

    @FXML
    private TextField idField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label feedback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Retrieve currently logged in user ID and Name

        try {
            ResultSet rs = SQLHandler.execute("SELECT ID FROM LoggedIn");

            rs.next();
            int userID = rs.getInt("ID");

            rs = SQLHandler.execute("SELECT * FROM User WHERE UserID = '" + userID + "' ");
            rs.next();
            String forename = rs.getString("Forename");
            String surname = rs.getString("Surname");

            labelID.setText("User ID: "+ userID);
            labelName.setText("User Name: "+forename+" "+surname);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Populate choice box
        roleChoiceBox.setItems(FXCollections.observableArrayList("Advisor","Admin","Office Manager"));
    }

    @FXML
    public void Profile() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Logout() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Cancel() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Apply() {

        String forename = forenameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String jobRole = roleChoiceBox.getValue().toString();
        String id = idField.getText();
        String password = passwordField.getText();

        //Connect to db

        String SQL = "INSERT INTO User VALUES ('"+id+"','"+forename+"','"+surname+"','"+email+"','"+password+"','"+jobRole+"' ) ";

        try {
            SQLHandler.executeUpdate(SQL);
            feedback.setText("Account created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void GenerateID(){
        idField.setText(String.valueOf(GenerateCode.RandomID()));
    }
}
