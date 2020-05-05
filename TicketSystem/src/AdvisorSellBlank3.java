import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class AdvisorSellBlank3 implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    private int userID;

    @FXML
    private Label feedback;

    @FXML
    private Button confirmButton;

    //All Sale Values
    private static int customerID;
    private static String customerForename;
    private static String customerSurname;
    private static String customerEmail;
    private static String customerType;
    private static boolean customerDiscounted;
    private static boolean newCust;

    private static long blankID;
    private static String blankType;
    private static String coupon1;
    private static String coupon2;
    private static String coupon3;
    private static String coupon4;

    private static int saleID;
    private static int paymentID;
    private static String paymentType;
    private static String cardNumber;
    private static String cardExpiry;
    private static String cardSecurity;

    private static LocalDate payDeadline;
    private int commissionPercentage;

    private static double subtotal;

    private int discountID;
    private int discountAmount;

    //Setters

    public static void setCustomerID(int customerID) {
        AdvisorSellBlank3.customerID = customerID;
    }
    public static void setCustomerForename(String customerForename) {
        AdvisorSellBlank3.customerForename = customerForename;
    }
    public static void setCustomerSurname(String customerSurname) {
        AdvisorSellBlank3.customerSurname = customerSurname;
    }
    public static void setCustomerEmail(String customerEmail) {
        AdvisorSellBlank3.customerEmail = customerEmail;
    }
    public static void setCustomerType(String customerType) {
        AdvisorSellBlank3.customerType = customerType;
    }
    public static void setCustomerDiscounted(boolean customerDiscounted) {
        AdvisorSellBlank3.customerDiscounted = customerDiscounted;
    }
    public static void setNewCust(boolean newCust){
        AdvisorSellBlank3.newCust = newCust;
    }

    public static void setBlankID(long blankID) {
        AdvisorSellBlank3.blankID = blankID;
    }
    public static void setCoupon1(String coupon1) {
        AdvisorSellBlank3.coupon1 = coupon1;
    }
    public static void setCoupon2(String coupon2) {
        AdvisorSellBlank3.coupon2 = coupon2;
    }
    public static void setCoupon3(String coupon3) {
        AdvisorSellBlank3.coupon3 = coupon3;
    }
    public static void setCoupon4(String coupon4) {
        AdvisorSellBlank3.coupon4 = coupon4;
    }

    public static void setPaymentID(int paymentID) {
        AdvisorSellBlank3.paymentID = paymentID;
    }
    public static void setSaleID(int saleID) {
        AdvisorSellBlank3.saleID = saleID;
    }
    public static void setPaymentType (String paymentType) {
        AdvisorSellBlank3.paymentType = paymentType;
    }
    public static void setCardNumber(String cardNumber) {
        AdvisorSellBlank3.cardNumber = cardNumber;
    }
    public static void setCardExpiry(String cardExpiry) {
        AdvisorSellBlank3.cardExpiry = cardExpiry;
    }
    public static void setCardSecurity(String cardSecurity) {
        AdvisorSellBlank3.cardSecurity = cardSecurity;
    }
    public static void setPayDeadline(LocalDate payDeadline) {
        AdvisorSellBlank3.payDeadline = payDeadline;
    }

    public static void setSubtotal(double subtotal) {
        AdvisorSellBlank3.subtotal = subtotal;
    }

    //For determining payment deadline in previous stages.
    public static String getCustomerType() {
        return customerType;
    }


    //Labels
    @FXML
    private Label custIDLabel;
    @FXML
    private Label custNameLabel;
    @FXML
    private Label custEmailLabel;
    @FXML
    private Label custTypeLabel;

    @FXML
    private Label paymentIDLabel;
    @FXML
    private Label saleIDLabel;
    @FXML
    private Label paymentTypeLabel;
    @FXML
    private Label cardNoLabel;
    @FXML
    private Label cardExpiryLabel;
    @FXML
    private Label cardSecurityLabel;
    @FXML
    private Label deadlineLabel;

    @FXML
    private Label blankIDLabel;
    @FXML
    private Label blankTypeLabel;
    @FXML
    private Label coupon1Label;
    @FXML
    private Label coupon2Label;
    @FXML
    private Label coupon3Label;
    @FXML
    private Label coupon4Label;

    @FXML
    private Label subtotalLabel;
    @FXML
    private Label commissionLabel;
    @FXML
    private Label discountLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label agencyLabel;
    @FXML
    private Label advisorPayLabel;

    @FXML
    private CheckBox paidCheckBox;

    private double discountValue;
    private double total;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Retrieve currently logged in user ID and Name
        try {
            ResultSet rs = SQLHandler.execute("SELECT ID FROM LoggedIn");

            rs.next();
            userID = rs.getInt("ID");

            rs = SQLHandler.execute("SELECT * FROM User WHERE UserID = '" + userID + "' ");
            rs.next();
            String forename = rs.getString("Forename");
            String surname = rs.getString("Surname");

            labelID.setText("User ID: "+userID);
            labelName.setText("User Name: "+forename+" "+surname);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        feedback.setText("");

        //Searching database for data
        //Work out Blank type, Advisor Commission and Customer Discount by searching tables
        try {

            //BLANK TYPE
            ResultSet rs = SQLHandler.execute("SELECT Type FROM Blank WHERE BlankID = '"+blankID+"'  ");
            rs.next();
            blankType = rs.getString("Type");

            //ADVISOR COMMISSION
            rs = SQLHandler.execute("SELECT Percentage FROM Commissions WHERE UserID = '"+userID+"'  ");
            rs.next();
            commissionPercentage = rs.getInt("Percentage");

            //CUSTOMER DISCOUNT (VALUED ONLY)
            if (customerType.equals("Valued") && customerDiscounted){
                rs = SQLHandler.execute("SELECT * FROM Discounts WHERE CustomerID = '"+customerID+"'  ");
                rs.next();

                discountID = rs.getInt("DiscountID");
                String discountType = rs.getString("Type");

                switch (discountType){
                    case "Flexible":
                        switch (blankType) {
                            case "201 - Domestic - Automatic":
                            case "101 - Domestic - Automatic":
                                //Domestic discount

                                discountAmount = rs.getInt("DomesticDisc");

                                break;
                            case "444 - International - Automatic":
                            case "440 - International - Manual":
                            case "420 - International - Automatic":
                                //Interline discount

                                discountAmount = rs.getInt("InternationalDisc");

                                break;
                            case "451 - Excess Baggage":
                            case "452 - Miscellaneous Service":
                                //Misc Charge discount

                                discountAmount = rs.getInt("MiscDisc");

                                break;
                            default:
                                discountAmount = 0;
                                System.out.println("Error applying discount.  Incorrect Format!");
                                break;
                        }
                        break;
                    case "Fixed":
                        //FIXED DISCOUNT - SAME FOR ALL TYPES
                        discountAmount = rs.getInt("FixedDisc");
                        break;
                }

            } else {
                //Non valued customers are not eligible for discounts.
                discountLabel.setText("Customer discount: None");
                discountValue = 0;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //PRICING
        //Subtotal is the fare
        //Work out Advisor Commission:
        double commissionValue = subtotal * ((double) commissionPercentage / 100);
        //Work out Customer Discount:
        discountValue = subtotal * (discountValue /100);
        //Calculate total
        total = subtotal - discountValue;

        //Payable to Agency
        double agencyPayable = total - commissionValue;
        //Payable to Advisor
        double advisorPayable = commissionValue + 500;


        //Populate page with sale data
        custIDLabel.setText("Customer ID: "+customerID);
        custNameLabel.setText("Customer Name: "+customerForename+" "+customerSurname);
        custEmailLabel.setText("Customer Email: "+customerEmail);
        custTypeLabel.setText("Customer Type: "+customerType);

        blankIDLabel.setText("Ticket ID: "+blankID);
        blankTypeLabel.setText("Ticket Type: "+blankType);
        coupon1Label.setText("Coupon 1: "+coupon1);
        coupon2Label.setText("Coupon 2: "+coupon2);
        coupon3Label.setText("Coupon 3: "+coupon3);
        coupon4Label.setText("Coupon 4: "+coupon4);

        paymentIDLabel.setText("Payment ID: "+paymentID);
        paymentTypeLabel.setText("Payment Type: "+paymentType);
        saleIDLabel.setText("Sale ID: "+saleID);
        cardNoLabel.setText("Card Number: "+cardNumber);
        cardExpiryLabel.setText("Card Expiry: "+cardExpiry);
        cardSecurityLabel.setText("Card Security: "+cardSecurity);
        deadlineLabel.setText("Payment Deadline: "+payDeadline);

        subtotalLabel.setText("Subtotal Fare: "+subtotal);
        commissionLabel.setText("Advisor Commission: "+commissionPercentage+" ("+ commissionValue +") ");
        discountLabel.setText("Customer Discount: "+discountAmount+" ("+discountValue+") ");
        totalLabel.setText("Total Cost: "+total);
        agencyLabel.setText("Payable to Agency: "+ agencyPayable);
        advisorPayLabel.setText("Payable to Advisor: "+ advisorPayable);

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
    public void Confirm() throws SQLException {

        //Only confirm once for a sale process - no duplication.
        confirmButton.setDisable(true);

        //Apply to database

        boolean paid;
        //If customer paying now or later (Up to 30 days after sale).
        paid = paidCheckBox.isSelected();

        //Update Blank Record so it cant be sold twice
        String blankSQL = "UPDATE Blank Set Status = 'Sold' WHERE BlankID = '"+blankID+"' ";

        //Create a Ticket record
        String ticketSQL = "INSERT INTO Tickets VALUES ( '"+blankID+"' ,  '"+blankType+"' , '"+customerID+
                "', '"+paymentID+"', '"+userID+"' , '"+coupon1+"' , '"+coupon2+"', '"+coupon3+"' , '"+coupon4+"'  )";

        //Create a Sale record
        String saleSQL = "INSERT INTO Sales VALUES ( '"+saleID+"' , '"+blankID+
                "' , '"+paymentID+"', '"+LocalDate.now()+"' , '"+userID+"'  )";

        //Create a payment record
        String paymentSQL = "INSERT IGNORE INTO Payments VALUES ( '"+paymentID+"' , '"+paymentType+
                "' , '"+cardNumber+"', '"+cardExpiry+"', '"+cardSecurity+"' , '"+subtotal+
                "', '"+discountValue+"', '"+total+"', "+paid+" , '"+payDeadline+"',  FALSE , '"+userID+"'   )";

        //Create Reports - Individual and Global records

        double paymentCash;
        double paymentCard;

        if (paymentType.equals("Cash")){
            paymentCash = total;
            paymentCard = 0;
        } else {
            paymentCash = 0;
            paymentCard  = total;
        }

        double comm15;
        double comm12;
        double comm10;
        double comm7;
        double comm9;
        double comm5;
        switch (commissionPercentage){
            case 15:
                comm15 = subtotal * 0.15;
                comm12 = 0;
                comm10 = 0;
                comm7 = 0;
                comm9 = 0;
                comm5 = 0;
                break;
            case 12:
                comm15 = 0;
                comm12 = subtotal * 0.12;
                comm10 = 0;
                comm7 = 0;
                comm9 = 0;
                comm5 = 0;
                break;
            case 10:
                comm15 = 0;
                comm12 = 0;
                comm10 = subtotal * 0.1;
                comm7 = 0;
                comm9 = 0;
                comm5 = 0;
                break;
            case 7:
                comm15 = 0;
                comm12 = 0;
                comm10 = 0;
                comm7 = subtotal * 0.07;
                comm9 = 0;
                comm5 = 0;
                break;
            case 9:
                comm15 = 0;
                comm12 = 0;
                comm10 = 0;
                comm7 = 0;
                comm9 = subtotal * 0.09;
                comm5 = 0;
                break;
            case 5:
                comm15 = 0;
                comm12 = 0;
                comm10 = 0;
                comm7 = 0;
                comm9 = 0;
                comm5 = subtotal * 0.05;
                break;

            default:
                comm15 = 0;
                comm12 = 0;
                comm10 = 0;
                comm7 = 0;
                comm9 = 0;
                comm5 = 0;
        }

        switch (blankType){
            case "444 - International - Automatic":
            case "440 - International - Manual":
            case "420 - International - Automatic":
                SQLHandler.executeUpdate("INSERT IGNORE INTO IndividualInterlineReport VALUES ( '"+ GenerateCode.RandomID()+"' , '"+saleID+
                        "' , '"+blankID+"', '"+subtotal+"', '50' , '25', NULL , '"+paymentCash+"', '"+paymentCard+"' , '"+total+"', '"+comm15+
                        "' , ' "+comm12+"', '"+comm10+"', '"+comm7+"',  '250'   )");
                SQLHandler.executeUpdate("INSERT IGNORE INTO GlobalInterlineReport VALUES ( '"+userID+"' ,  '"+subtotal+
                        "' , '50', '25', NULL , '"+paymentCash+ "', '"+paymentCard+"', '"+total+"', '"+comm15+"' , '"+comm12+
                        "' , '"+comm10+"' , '"+comm7+"',  '250'   )");
                break;
            case "201 - Domestic - Automatic":
            case "101 - Domestic - Automatic":
                SQLHandler.executeUpdate("INSERT IGNORE INTO IndividualDomesticReport VALUES ( '"+ GenerateCode.RandomID()+"' , '"+saleID+
                        "' ,  '"+subtotal+"','"+paymentCard+"', '0', '0', '"+paymentCash+"','"+paymentCard+"', '60', '"+total+"', '"+comm9+
                        "' , '"+comm5+"'   )");
                SQLHandler.executeUpdate("INSERT IGNORE INTO GlobalDomesticReport VALUES ( '"+userID+"' , '"+subtotal+
                        "' , '60', '75', NULL,  '"+paymentCash+"', '"+paymentCard+"' , '"+total+"', '"+comm9+
                        "' , '"+comm5+"',  '250'   )");
                break;

        }



        //Commit to database
        try {
            SQLHandler.executeUpdate(blankSQL);
            SQLHandler.executeUpdate(ticketSQL);
            SQLHandler.executeUpdate(saleSQL);
            SQLHandler.executeUpdate(paymentSQL);

            //If this is a new customer, add a record to the database.
            if (newCust){
                String customerSQL = "INSERT  INTO Customers VALUES ( '"+customerID+"' , '"+customerForename+
                        "' , '"+customerSurname+"', '"+customerEmail+
                        "', '"+customerType+"', '"+customerDiscounted+"'   )";
                SQLHandler.executeUpdate(customerSQL);
                feedback.setText("Sale Successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
