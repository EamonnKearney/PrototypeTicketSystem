import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class ChangePassword implements Initializable {

    //Logged in ID
    private int userID;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label feedback;

    @FXML
    private TextField newPass;

    @FXML
    private TextField reNewPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Retrieve currently logged in user ID and Name
        try {
            ResultSet rs = SQLHandler.execute("SELECT ID FROM LoggedIn");

            rs.next();
            userID = rs.getInt("ID");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        feedback.setText("");
    }

    @FXML
    public void Close() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Apply() {

        //Assume success, replaced by error message if applicable
        feedback.setText("Success");
        Boolean valid = true;

        //Validation Checks
        String password = newPass.getText();
        String rePassword = reNewPass.getText();

        //Check that entries match
        if (!password.equals(rePassword)){
            feedback.setText("Entries do not match");
            valid = false;
        }

        //Check length
        else if (password.length()>10 || password.length()<5){
            feedback.setText("Password must be between 5 and 10 characters");
            valid = false;
        }

        //Edit Database
        if (valid==true){

            System.out.println("Password is valid, editing database.");
            try {
                SQLHandler.executeUpdate("UPDATE User SET Password= '"+password+"' WHERE UserID= '"+userID+"' ");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}
