import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class AdvisorSellBlank2 implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    //Success/Error feedback label
    @FXML
    private Label feedback;

    //Check Box
    @FXML
    private CheckBox cashCheckBox;

    //Form Components
    @FXML
    private HBox cardPayment;
    @FXML
    private TextField paymentIDField;
    @FXML
    private TextField saleIDField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private DatePicker cardExpiryPicker;
    @FXML
    private TextField cardSecurityField;
    @FXML
    private Label deadlineLabel;

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

        //Check box toggles whether card form appears
        cashCheckBox.setOnAction(event -> {
            CashPayment();
        });

        feedback.setText("");

        //Set Deadline depending on whether customer is regular/valued or not.
        LocalDate deadline;
        if (!AdvisorSellBlank3.getCustomerType().equals("Casual")){
            //Customer is regular or valued - Extra 30 days on payment deadline.
            deadline = LocalDate.now().plusMonths(1);
        } else {
            //Customer is casual - Must pay today.
            deadline = LocalDate.now();
        }

        AdvisorSellBlank3.setPayDeadline(deadline);
        deadlineLabel.setText("Payment deadline: "+deadline.toString());
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

        int paymentID;
        int saleID;
        String paymentType;
        String cardNumber;
        String cardExpiry;
        String cardSecurity;

        if (cashCheckBox.isSelected()){
            //CASH PAYMENT

            paymentID = Integer.parseInt(paymentIDField.getText());
            saleID = Integer.parseInt(saleIDField.getText());
            paymentType = "Cash";

            AdvisorSellBlank3.setPaymentID(paymentID);
            AdvisorSellBlank3.setSaleID(saleID);
            AdvisorSellBlank3.setPaymentType(paymentType);
            AdvisorSellBlank3.setCardNumber("N/A");
            AdvisorSellBlank3.setCardExpiry("N/A");
            AdvisorSellBlank3.setCardSecurity("N/A");

        } else {
            //CARD PAYMENT

            paymentID = Integer.parseInt(paymentIDField.getText());
            saleID = Integer.parseInt(saleIDField.getText());
            paymentType = "Card";
            cardNumber = cardNumberField.getText();
            cardExpiry = cardExpiryPicker.getValue().toString();
            cardSecurity = cardSecurityField.getText();

            AdvisorSellBlank3.setPaymentID(paymentID);
            AdvisorSellBlank3.setSaleID(saleID);
            AdvisorSellBlank3.setPaymentType(paymentType);
            AdvisorSellBlank3.setCardNumber(cardNumber);
            AdvisorSellBlank3.setCardExpiry(cardExpiry);
            AdvisorSellBlank3.setCardSecurity(cardSecurity);

        }

        //Carry details to next stage

        //Validation assigned to Testers was not completed.

        boolean valid = true;
        if (valid){
            AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorSellBlank3.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        else {
            feedback.setText("Invalid");
        }

    }

    public void CashPayment(){
        if (cashCheckBox.isSelected()){
            //When cash payment check box activated, card detail form removed.
            cardPayment.setDisable(true);
        }else {
            //When cash payment check box deactivated, card detail form returned.
            cardPayment.setDisable(false);
        }

    }


    @FXML
    public void GeneratePaymentID(){
        paymentIDField.setText(String.valueOf(GenerateCode.RandomID()));
    }

    @FXML
    public void GenerateSaleID(){
        saleIDField.setText(String.valueOf(GenerateCode.RandomID()));
    }

}
