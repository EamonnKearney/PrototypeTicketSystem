import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class ChangeEmail implements Initializable {

    //Logged in ID
    private int userID;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label feedback;

    @FXML
    private TextField newEmail;

    @FXML
    private TextField reNewEmail;

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
    public void Close(ActionEvent actionEvent) throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Apply() throws SQLException {

        //Assume success, replaced by error message if applicable
        feedback.setText("Success");
        Boolean valid = true;

        //Validation Checks
        String email = newEmail.getText();
        String reEmail = reNewEmail.getText();

        //Check that entries match
        if (!email.equals(reEmail)){
            feedback.setText("Entries do not match");
            valid = false;
        }

        //Check length
        else if (email.length()<7){
            feedback.setText("Invalid Email");
            valid = false;
        }

        //Editing Database
        if (valid==true){

            //Issue
            System.out.println("Email is valid, editing database.");

            SQLHandler.executeUpdate("UPDATE User SET Email= '"+email+"' WHERE UserID= '"+userID+"' ");

        }
    }
}
