import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Central Menu for Admin tasks
 * Eamonn Kearney 2020*/
public class AdminDash implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label greeting;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Retrieve currently logged in user ID and Name
        Login.RetrieveCurrentUser(greeting, labelID, labelName);
    }

    /**Navigate to Profile page*/
    @FXML
    public void Profile() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Navigate to Login page*/
    @FXML
    public void Logout() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Navigate to Admin Blank Creation*/
    @FXML
    public void Blanks() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminBlankCreation.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Navigate to Admin Database Management*/
    @FXML
    public void Database() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminDatabase.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Navigate to Admin Blank Stock*/
    @FXML
    public void Stock() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminBlankStock.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Navigate to User Account Registration*/
    @FXML
    public void Register() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminCreateAccount.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
