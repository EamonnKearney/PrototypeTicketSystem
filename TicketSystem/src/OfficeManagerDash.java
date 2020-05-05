import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Central Menu for OM tasks
 * Eamonn Kearney 2020*/
public class OfficeManagerDash implements Initializable {

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

    /**To Profile screen*/
    @FXML
    public void Profile() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Login screen*/
    @FXML
    public void Logout() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To OM Blank Assignment (To Advisors)*/
    @FXML
    public void Blanks() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerBlanks.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To OM Reports*/
    @FXML
    public void Reports() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerReports.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To OM Commission Management*/
    @FXML
    public void Commissions() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerCommissions.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To OM Customer view & Discounts*/
    @FXML
    public void Customers() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerCustomers.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
