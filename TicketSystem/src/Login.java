import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {
/**Controller Class of Login.fxml
 * First screen to load in application
 * Eamonn Kearney 2020
 * */

    //User Credentials
    String userID;
    String password;

    /**Such components are linked to the GUI, see associated FXML file.*/
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private Label feedbackLabel;

    /**Runs when this screen is loaded*/
    @Override
    public void initialize(URL url, ResourceBundle rb){

        //Clear feedback label
        feedbackLabel.setText("");

    }

    /**Runs when Login button pressed
     Checks database User table, where all staff details are stored.  Searches for a matching user ID and password.
     Based on the JobRole (Advisor, Admin, OM) of the record, it brings the user to a specific region of the application.
     */
    @FXML
    public void loginProcess() throws IOException {

        //Retrieve text data from form fields.
        userID = userField.getText();
        password = passField.getText();

        //Connect to database
        try {
            ResultSet rs = SQLHandler.execute("SELECT * FROM User WHERE UserID= '"+userID+"' AND Password= '"+password+"'  ");

            //Determine record exists (User ID and Password are correct)
            if (rs.isBeforeFirst()) {
                //A database table, 'LoggedIn' tracks which user is logged in at a given time.
                //The User ID of the user currently logged in is placed into this table.
                ResultSet loggedIns = SQLHandler.execute("SELECT * FROM LoggedIn");
                if (!loggedIns.next()) {
                    SQLHandler.INSERT("LoggedIn", new String[]{"ID"}, new String[] {userID},1);
                } else {
                    SQLHandler.executeUpdate("UPDATE LoggedIn SET ID = '" + userID + "' ");
                }
                //Determine role to direct user towards correct section of app.  Access Control.
                rs.first();
                String role = rs.getString("JobRole");

                AnchorPane pane;
                switch (role){
                    case "Advisor":
                        //Load Advisor Dashboard to access Advisor Section
                        pane = FXMLLoader.load(getClass().getResource("AdvisorDash.fxml"));
                        rootPane.getChildren().setAll(pane);
                        break;
                    case "Admin":
                        //Load Admin Dashboard to access Admin Section
                        pane = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
                        rootPane.getChildren().setAll(pane);
                        break;
                    case "OfficeManager":
                        //Load OM Dashboard to access OM Section
                        pane = FXMLLoader.load(getClass().getResource("OfficeManagerDash.fxml"));
                        rootPane.getChildren().setAll(pane);
                        break;
                }
            }
            else{
                //If User ID or associated password is not recognised, present error message.
                feedbackLabel.setText("User ID or Password incorrect");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**Retrieve Current User's details for display in page headers*/
    static void RetrieveCurrentUser(Label greeting, Label labelID, Label labelName) {
        try {
            ResultSet rs = SQLHandler.execute("SELECT ID FROM LoggedIn");

            rs.next();
            int userID = rs.getInt("ID");

            rs = SQLHandler.execute("SELECT * FROM User WHERE UserID = '" + userID + "' ");
            rs.next();
            String forename = rs.getString("Forename");
            String surname = rs.getString("Surname");

            greeting.setText("Hello "+ forename);

            labelID.setText("User ID: "+ userID);
            labelName.setText("User Name: "+forename+" "+surname);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
