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

/**Eamonn Kearney & Rafail Kaufman 2020*/
public class AdminBlankCreation implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private ChoiceBox typeChoiceBox;

    @FXML
    private TextField numberField;

    @FXML
    private Label labelFullNumber;

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

        //Choice box items
        typeChoiceBox.getItems().addAll("444 - International - Automatic",
                "440 - International - Manual",
                "420 - International - Automatic",
                "201 - Domestic - Automatic",
                "101 - Domestic - Automatic",
                "451 - Excess Baggage",
                "452 - Miscellaneous Service");
        typeChoiceBox.getSelectionModel().selectFirst();

        //Text Field - Blank Number
        numberField.setText("100000001");

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
    public void Back() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void generateNumber(){

        String randomNumber = GenerateCode.RandomBlankNumber();
        numberField.setText(randomNumber);
    }

    @FXML
    public void IncrementNumber(){

        String text = numberField.getText();
        int currentTextAsInt = Integer.parseInt(text);
        currentTextAsInt++;
        text = String.valueOf(currentTextAsInt);
        numberField.setText(text);

    }

    @FXML
    public void DecrementNumber(){

        String text = numberField.getText();
        int currentTextAsInt = Integer.parseInt(text);
        currentTextAsInt--;
        text = String.valueOf(currentTextAsInt);
        numberField.setText(text);

    }

    @FXML
    public void Submit() {

        //Retrieve type code (440, etc.)
        String typeCode = typeChoiceBox.getValue().toString().substring(0,3);

        //Retrieve rest of the blank number
        String blankNumber = numberField.getText();

        //Blank number, also ID, primary key of Blank table.
        long blankID = Long.parseLong(typeCode + blankNumber);

        String type = typeChoiceBox.getValue().toString();
        labelFullNumber.setText("Full Number: "+blankID);

        try {

            SQLHandler.executeUpdate("INSERT INTO Blank VALUES ( '"+blankID+"' , '"+type+
                    "' , '0' , 'Active'   )");
            feedback.setText("Success");

        }
        catch (NumberFormatException | SQLException e){
                feedback.setText("Error");
        }

    }

}