import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Central Menu for Advisor tasks
 * Eamonn Kearney 2020*/
public class AdvisorDash implements Initializable {

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

        //Check for unpaid tickets past their deadline - Generate Alert
        TimeController.CheckForUnpaid();
    }

    /**To Profile Screen*/
    @FXML
    public void Profile() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Login Screen*/
    @FXML
    public void Logout() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Advisor Blank Management*/
    @FXML
    public void Blanks() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorBlanks.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Advisor Tickets*/
    @FXML
    public void Tickets() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorTickets.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Advisor Reports*/
    @FXML
    public void Reports() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorReports.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Advisor Customers Management*/
    @FXML
    public void Customers() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorCustomers.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
