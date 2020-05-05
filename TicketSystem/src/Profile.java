import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Profile page for displaying the user's details.  Also gives the option to change email and password.
 * Eamonn Kearney 2020*/
public class Profile implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private Label labelJobRole;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelPassword;

    private String jobRole;

    /**Read logged in user's details from database and display them on screen.*/
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
            String email = rs.getString("Email");
            String password = rs.getString("Password");
            jobRole = rs.getString("JobRole");

            labelID.setText("User ID: "+ userID);
            labelName.setText("User Name: "+ forename +" "+ surname);
            labelEmail.setText("Email: "+ email);
            labelPassword.setText("Password: "+ password);
            labelJobRole.setText("Role: "+jobRole);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**To Dashboard - Determines which one based on User ID's Job Role*/
    @FXML
    public void Home() throws IOException {

        AnchorPane pane;
        switch (jobRole){
            case "Admin":
                pane = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
                rootPane.getChildren().setAll(pane);
                break;

            case "Advisor":
                pane = FXMLLoader.load(getClass().getResource("AdvisorDash.fxml"));
                rootPane.getChildren().setAll(pane);
                break;

            case "OfficeManager":
                pane = FXMLLoader.load(getClass().getResource("OfficeManagerDash.fxml"));
                rootPane.getChildren().setAll(pane);
                break;
        }
    }

    /**To Login screen*/
    @FXML
    public void Logout() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Change Email screen*/
    @FXML
    public void ChangeEmail() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("ChangeEmail.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Change Password screen*/
    @FXML
    public void ChangePassword() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}