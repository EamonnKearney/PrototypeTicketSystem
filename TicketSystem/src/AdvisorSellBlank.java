import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class AdvisorSellBlank implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private Label feedback;

    //Form containers
    @FXML
    private HBox newForm;
    @FXML
    private VBox existingLabel;
    @FXML
    private VBox existingChoice;

    //Check Box
    @FXML
    private CheckBox existingCheckBox;

    //Choice Box
    @FXML
    private ChoiceBox custIDChoiceBox;

    //Form Text Fields
    @FXML
    private TextField custIDField;
    @FXML
    private TextField custForenameField;
    @FXML
    private TextField custSurnameField;
    @FXML
    private TextField custEmailField;

    @FXML
    private TextField couponField1;
    @FXML
    private TextField couponField2;
    @FXML
    private TextField couponField3;
    @FXML
    private TextField couponField4;

    @FXML
    private TextField subtotalField;

    @FXML
    private Label custLabel;

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

        feedback.setText("");

        //Check box toggles which form (New/Existing customer) appears
        existingCheckBox.setOnAction(event -> {
            ExistingCustomer();
        });
        //Set up customer forms so that only one appears at any given time.
        ExistingCustomer();

        feedback.setText("");

        //Populate Existing Customer Choice Box
        try {
            ResultSet rs = SQLHandler.execute("SELECT CustomerID FROM Customers");
            while (rs.next()){
                int customer = rs.getInt("CustomerID");
                //Add to choice box options
                custIDChoiceBox.getItems().add(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorBlanks.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Continue() throws IOException {

        //Capture Details - Customer
        int customerID=0;
        String customerForename="";
        String customerSurname="";
        String customerEmail="";
        String customerType="";

        if (existingCheckBox.isSelected()){
            //EXISTING CUSTOMER

            customerID = Integer.parseInt(custIDChoiceBox.getValue().toString());

            //Retrieve the rest of the customer details

            try {
                ResultSet rs = SQLHandler.execute("SELECT * FROM Customers WHERE CustomerID = '"+customerID+"'  ");
                rs.next();
                customerForename = rs.getString("Forename");
                customerSurname = rs.getString("Surname");
                customerEmail = rs.getString("Email");
                customerType = rs.getString("Type");


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            //NEW CUSTOMER
            customerID = Integer.parseInt(custIDField.getText());
            customerForename = custForenameField.getText();
            customerSurname = custSurnameField.getText();
            customerEmail = custEmailField.getText();
            customerType = "Casual";

        }

        //Capture details - Itinerary
        String coupon1 = couponField1.getText();
        String coupon2 = couponField2.getText();
        String coupon3 = couponField3.getText();
        String coupon4 = couponField4.getText();

        double subtotal = Double.parseDouble(subtotalField.getText());

        //Carry details to next stage
        boolean valid = true;
        if (valid){

            AdvisorSellBlank3.setCustomerID(customerID);
            AdvisorSellBlank3.setCustomerForename(customerForename);
            AdvisorSellBlank3.setCustomerSurname(customerSurname);
            AdvisorSellBlank3.setCustomerEmail(customerEmail);
            AdvisorSellBlank3.setCustomerType(customerType);

            AdvisorSellBlank3.setCoupon1(coupon1);
            AdvisorSellBlank3.setCoupon2(coupon2);
            AdvisorSellBlank3.setCoupon3(coupon3);
            AdvisorSellBlank3.setCoupon4(coupon4);

            AdvisorSellBlank3.setSubtotal(subtotal);

            AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorSellBlank2.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        else {
            feedback.setText("Invalid");
        }

    }

    public void ExistingCustomer(){
        if (existingCheckBox.isSelected()){
            //Show Existing customer form
            newForm.setDisable(true);
            existingLabel.setDisable(false);
            existingChoice.setDisable(false);
            custLabel.setText("CUSTOMER DETAILS - Existing Customer");
        }else {
            //Show New customer form
            newForm.setDisable(false);
            existingLabel.setDisable(true);
            existingChoice.setDisable(true);
            custLabel.setText("CUSTOMER DETAILS - New Customer");

        }

    }

    @FXML
    public void Generate(){

        String randomNumber = String.valueOf(GenerateCode.RandomID());
        custIDField.setText(randomNumber);
    }

}
