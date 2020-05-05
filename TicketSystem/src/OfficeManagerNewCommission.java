import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class OfficeManagerNewCommission implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private TextField idField;

    @FXML
    private ChoiceBox advisorChoice;

    @FXML
    private ChoiceBox percentageChoice;

    @FXML
    private Label feedback;

    //Mode: Adding or Editing record?
    private static String mode = "";

    public static void setMode(String m) {
        mode = m;
    }

    //ID of selected record
    private static int recordID;

    public static int getID() {
        return recordID;
    }

    public static void setID(int ID) {
        OfficeManagerNewCommission.recordID = ID;
    }

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

        //Populate choice box for percentage
        percentageChoice.setItems(FXCollections.observableArrayList(15,12,10,9,8,7));

        //Populate choice box for advisor user ID
        //Populate choice box with advisors
        try {
            ResultSet rs = SQLHandler.execute("SELECT UserID FROM User WHERE JobRole = 'Advisor'");
            while (rs.next()){
                int advisorID = rs.getInt("UserID");
                //Add to choice box options
                advisorChoice.getItems().add(advisorID);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //If in edit mode, load in existing details into form
        try {
            ResultSet rs = SQLHandler.execute("SELECT * FROM Commissions WHERE CommissionID = '"+recordID+ "' ");
            rs.next();
            idField.setText(String.valueOf(rs.getInt("CommissionID")));
            advisorChoice.setValue(rs.getInt("UserID"));
            percentageChoice.setValue(rs.getInt("Percentage"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        feedback.setText("");
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

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerCommissions.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Apply() {

        //Get Commission ID from text field
        int commissionID = Integer.parseInt(idField.getText());

        //Get Advisor ID from choice box
        int userID = Integer.parseInt(advisorChoice.getValue().toString());

        //Get Percentage from spinner
        int percentage = Integer.parseInt(percentageChoice.getValue().toString());

        //Connect to db and establish whether this is a NEW or an EDIT to an existing one.
        String SQL = "";

        if (mode.equals("Add")){
            //ADD A NEW RECORD TO COMMISSIONS
            SQL = "INSERT INTO Commissions VALUES ('"+commissionID+"','"+userID+"','"+percentage+"' ) ";
        }else if (mode.equals("Edit")){
            //EDIT EXISTING COMMISSION RECORD
            SQL = "UPDATE Commissions SET CommissionID = '"+commissionID+"', UserID ='"+userID+
                    "', Percentage = '"+percentage+"'  ";
        }
        try {
            SQLHandler.executeUpdate(SQL);
            feedback.setText("Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void GenerateID(){
        idField.setText(String.valueOf(GenerateCode.RandomID()));
    }

}
