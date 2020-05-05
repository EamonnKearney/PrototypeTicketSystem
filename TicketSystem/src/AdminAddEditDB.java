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

/**Adding OR Editing database records - Form changes depending on which table and record were in question.
 * Eamonn Kearney 2020*/
public class AdminAddEditDB implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private Label feedback;

    @FXML
    private Label labelMode;

    //Which table is being affected
    private static String tableName = "";

    public static void setTableName(String table) {
        tableName = table;
    }

    //Mode: Adding or Editing record?
    private static String mode = "";


    public static void setMode(String m) {
        mode = m;
    }

    //ID of selected record
    private static int ID;

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        AdminAddEditDB.ID = ID;
    }

    //Labels
    @FXML
    private Label labelTableName;
    @FXML
    private Label labelColumn1;
    @FXML
    private Label labelColumn2;
    @FXML
    private Label labelColumn3;
    @FXML
    private Label labelColumn4;
    @FXML
    private Label labelColumn5;
    @FXML
    private Label labelColumn6;
    @FXML
    private Label labelColumn7;
    @FXML
    private Label labelColumn8;
    @FXML
    private Label labelColumn9;
    @FXML
    private Label labelColumn10;
    @FXML
    private Label labelColumn11;
    @FXML
    private Label labelColumn12;

    //Text Fields
    @FXML
    private TextField fieldColumn1;
    @FXML
    private TextField fieldColumn2;
    @FXML
    private TextField fieldColumn3;
    @FXML
    private TextField fieldColumn4;
    @FXML
    private TextField fieldColumn5;
    @FXML
    private TextField fieldColumn6;
    @FXML
    private TextField fieldColumn7;
    @FXML
    private TextField fieldColumn8;
    @FXML
    private TextField fieldColumn9;
    @FXML
    private TextField fieldColumn10;
    @FXML
    private TextField fieldColumn11;
    @FXML
    private TextField fieldColumn12;


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

        //Table
        labelTableName.setText("Table: "+tableName);
        //Mode
        if (mode.equals("Add")){
            labelMode.setText("Adding Record");
        }else if (mode.equals("Edit")){
            labelMode.setText("Editing Record");
        }

        //Populate labels

        switch (tableName){
            case "User":

                //Labels
                labelColumn1.setText("UserID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("Forename: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("Surname: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("Email: ");
                labelColumn4.setVisible(true);
                labelColumn5.setText("Password: ");
                labelColumn5.setVisible(true);
                labelColumn6.setText("Job Role: ");
                labelColumn6.setVisible(true);
                labelColumn7.setText("");
                labelColumn7.setVisible(false);
                labelColumn8.setText("");
                labelColumn8.setVisible(false);
                labelColumn9.setText("");
                labelColumn9.setVisible(false);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(true);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(true);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(true);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(false);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(false);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(false);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);

                break;
            case "Customers":

                //Labels
                labelColumn1.setText("CustomerID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("Type: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("Forename: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("Surname: ");
                labelColumn4.setVisible(true);
                labelColumn5.setText("Email: ");
                labelColumn5.setVisible(true);
                labelColumn6.setText("Discount: ");
                labelColumn6.setVisible(true);
                labelColumn7.setText("");
                labelColumn7.setVisible(false);
                labelColumn8.setText("");
                labelColumn8.setVisible(false);
                labelColumn9.setText("");
                labelColumn9.setVisible(false);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(true);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(true);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(true);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(false);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(false);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(false);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);

                break;
            case "Discounts":

                //Labels
                labelColumn1.setText("DiscountID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("CustomerID: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("Type: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("FixedDisc: ");
                labelColumn4.setVisible(true);
                labelColumn5.setText("DomesticDisc: ");
                labelColumn5.setVisible(true);
                labelColumn6.setText("InternationalDisc: ");
                labelColumn6.setVisible(true);
                labelColumn7.setText("MiscDisc");
                labelColumn7.setVisible(true);
                labelColumn8.setText("");
                labelColumn8.setVisible(false);
                labelColumn9.setText("");
                labelColumn9.setVisible(false);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(true);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(true);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(true);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(true);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(false);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(false);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);
                break;
            case "Commissions":
                //Labels
                labelColumn1.setText("CommissionID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("UserID: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("Percentage: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("");
                labelColumn4.setVisible(false);
                labelColumn5.setText("");
                labelColumn5.setVisible(false);
                labelColumn6.setText("");
                labelColumn6.setVisible(false);
                labelColumn7.setText("");
                labelColumn7.setVisible(false);
                labelColumn8.setText("");
                labelColumn8.setVisible(false);
                labelColumn9.setText("");
                labelColumn9.setVisible(false);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(false);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(false);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(false);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(false);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(false);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(false);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);
                break;
            case "Tickets":
                //Labels
                labelColumn1.setText("TicketID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("TicketType: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("CustomerID: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("PaymentID: ");
                labelColumn4.setVisible(true);
                labelColumn5.setText("AssignedAdvisor: ");
                labelColumn5.setVisible(true);
                labelColumn6.setText("Coupon1: ");
                labelColumn6.setVisible(true);
                labelColumn7.setText("Coupon2: ");
                labelColumn7.setVisible(true);
                labelColumn8.setText("Coupon3: ");
                labelColumn8.setVisible(true);
                labelColumn9.setText("Coupon4: ");
                labelColumn9.setVisible(true);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(true);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(true);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(true);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(true);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(true);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(true);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);
                break;
            case "Payments":
                //Labels
                labelColumn1.setText("PaymentID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("PaymentType: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("CardNumber: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("CardExpiry: ");
                labelColumn4.setVisible(true);
                labelColumn5.setText("CardSecurity: ");
                labelColumn5.setVisible(true);
                labelColumn6.setText("Subtotal: ");
                labelColumn6.setVisible(true);
                labelColumn7.setText("Discount: ");
                labelColumn7.setVisible(true);
                labelColumn8.setText("Total: ");
                labelColumn8.setVisible(true);
                labelColumn9.setText("Paid: ");
                labelColumn9.setVisible(true);
                labelColumn10.setText("Deadline: ");
                labelColumn10.setVisible(true);
                labelColumn11.setText("Refunded: ");
                labelColumn11.setVisible(true);
                labelColumn12.setText("AdvisorID: ");
                labelColumn12.setVisible(true);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(true);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(true);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(true);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(true);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(true);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(true);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(true);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(true);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(true);
                break;
            case "Sales":
                //Labels
                labelColumn1.setText("SaleID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("TicketID: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("PaymentID: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("SaleDate: ");
                labelColumn4.setVisible(true);
                labelColumn5.setText("AdvisorID: ");
                labelColumn5.setVisible(true);
                labelColumn6.setText("");
                labelColumn6.setVisible(false);
                labelColumn7.setText("");
                labelColumn7.setVisible(false);
                labelColumn8.setText("");
                labelColumn8.setVisible(false);
                labelColumn9.setText("");
                labelColumn9.setVisible(false);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(true);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(true);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(false);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(false);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(false);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(false);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);
                break;
            case "Refunds":
                //Labels
                //Labels
                labelColumn1.setText("RefundID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("SaleID: ");
                labelColumn2.setVisible(true);
                labelColumn3.setText("Amount: ");
                labelColumn3.setVisible(true);
                labelColumn4.setText("Date Refunded: ");
                labelColumn4.setVisible(true);
                labelColumn5.setText("Advisor ID: ");
                labelColumn5.setVisible(true);
                labelColumn6.setText("");
                labelColumn6.setVisible(false);
                labelColumn7.setText("");
                labelColumn7.setVisible(false);
                labelColumn8.setText("");
                labelColumn8.setVisible(false);
                labelColumn9.setText("");
                labelColumn9.setVisible(false);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(true);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(true);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(true);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(true);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(false);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(false);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(false);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(false);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);
                break;

            case "LoggedIn":
                //Labels
                labelColumn1.setText("ID: ");
                labelColumn1.setVisible(true);
                labelColumn2.setText("");
                labelColumn2.setVisible(false);
                labelColumn3.setText("");
                labelColumn3.setVisible(false);
                labelColumn4.setText("");
                labelColumn4.setVisible(false);
                labelColumn5.setText("");
                labelColumn5.setVisible(false);
                labelColumn6.setText("");
                labelColumn6.setVisible(false);
                labelColumn7.setText("");
                labelColumn7.setVisible(false);
                labelColumn8.setText("");
                labelColumn8.setVisible(false);
                labelColumn9.setText("");
                labelColumn9.setVisible(false);
                labelColumn10.setText("");
                labelColumn10.setVisible(false);
                labelColumn11.setText("");
                labelColumn11.setVisible(false);
                labelColumn12.setText("");
                labelColumn12.setVisible(false);

                //Text Fields
                fieldColumn1.setText("");
                fieldColumn1.setVisible(true);
                fieldColumn2.setText("");
                fieldColumn2.setVisible(false);
                fieldColumn3.setText("");
                fieldColumn3.setVisible(false);
                fieldColumn4.setText("");
                fieldColumn4.setVisible(false);
                fieldColumn5.setText("");
                fieldColumn5.setVisible(false);
                fieldColumn6.setText("");
                fieldColumn6.setVisible(false);
                fieldColumn7.setText("");
                fieldColumn7.setVisible(false);
                fieldColumn8.setText("");
                fieldColumn8.setVisible(false);
                fieldColumn9.setText("");
                fieldColumn9.setVisible(false);
                fieldColumn10.setText("");
                fieldColumn10.setVisible(false);
                fieldColumn11.setText("");
                fieldColumn11.setVisible(false);
                fieldColumn12.setText("");
                fieldColumn12.setVisible(false);
                break;
        }
        feedback.setText("");

        //Populate form with data to edit
        if (mode.equals("Edit")){
            try {
                PopulateForm();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
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

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminDatabase.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Apply changes - Add/Edit */
    @FXML
    public void Apply() {

        String SQL = "";

        if (mode.equals("Add")){
            //Adding record to database

            //SQL - INSERT NEW RECORD
            switch (tableName){
                case "User":
                    SQL = "INSERT INTO User VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"', '"+fieldColumn4.getText()+
                            "', '"+fieldColumn5.getText()+"', '"+fieldColumn6.getText()+"'   )";
                    break;
                case "Customers":
                    SQL = "INSERT INTO Customers VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"', '"+fieldColumn4.getText()+
                            "', '"+fieldColumn5.getText()+"', '"+fieldColumn6.getText()+"'   )";
                    break;
                case "Commissions":
                    SQL = "INSERT INTO Commissions VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"'   )";
                    break;
                case "Discounts":
                    SQL = "INSERT INTO Discounts VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"', '"+fieldColumn4.getText()+
                            "', '"+fieldColumn5.getText()+"', '"+fieldColumn6.getText()+"' , '"+fieldColumn7.getText()+"'   )";
                    break;
                case "Tickets":
                    SQL = "INSERT INTO Tickets VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"', '"+fieldColumn4.getText()+ "', '"+fieldColumn5.getText()+
                            "', '"+fieldColumn6.getText()+"' , '"+fieldColumn7.getText()+"' , '"+fieldColumn8.getText()+
                            "' , '"+fieldColumn9.getText()+"'  )";
                    break;
                case "Sales":
                    SQL = "INSERT INTO Sales VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"', '"+fieldColumn4.getText()+
                            "', '"+fieldColumn5.getText()+"'   )";
                    break;
                case "Refunds":
                    SQL = "INSERT INTO Refunds VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"', '"+fieldColumn4.getText()+
                            "', '"+fieldColumn5.getText()+"'   )";
                    break;
                case "Payments":
                    SQL = "INSERT INTO Payments VALUES ( '"+fieldColumn1.getText()+"' , '"+fieldColumn2.getText()+
                            "' , '"+fieldColumn3.getText()+"', '"+fieldColumn4.getText()+
                            "', '"+fieldColumn5.getText()+"', '"+fieldColumn6.getText()+"' , '"+fieldColumn7.getText()+"'   )";
                    break;
                case "LoggedIn":
                    SQL = "INSERT INTO LoggedIn VALUES ( '"+fieldColumn1.getText()+"' )";
                    break;
            }

        }else if (mode.equals("Edit")){

            //SQL - UPDATE EXISTING RECORD
            switch (tableName){
                case "User":
                    SQL = "UPDATE User SET UserID = '"+fieldColumn1.getText()+"' , Forename = '"+fieldColumn2.getText()+
                            "' , Surname = '"+fieldColumn3.getText()+"', Email = '"+fieldColumn4.getText()+
                            "', Password = '"+fieldColumn5.getText()+"', JobRole = '"+fieldColumn6.getText()+"'   ";
                    break;
                case "Customers":
                    SQL = "UPDATE Customers SET CustomerID = '"+fieldColumn1.getText()+"' , Type = '"+fieldColumn2.getText()+
                            "' , Forename = '"+fieldColumn3.getText()+"', Surname = '"+fieldColumn4.getText()+
                            "', Email ='"+fieldColumn5.getText()+"', Discount = '"+fieldColumn6.getText()+"'   ";
                    break;
                case "Commissions":
                    SQL = "UPDATE Commissions SET CommissionID = '"+fieldColumn1.getText()+"' , UserID = '"+fieldColumn2.getText()+
                            "' , Percentage = '"+fieldColumn3.getText()+"' ";
                    break;
                case "Discounts":
                    SQL = "UPDATE Discounts SET DiscountID = '"+fieldColumn1.getText()+"' , CustomerID = '"+fieldColumn2.getText()+
                            "' , Type = '"+fieldColumn3.getText()+"', FixedDisc = '"+fieldColumn4.getText()+
                            "', DomesticDisc = '"+fieldColumn5.getText()+"', InternationalDisc = '"+fieldColumn6.getText()+
                            "', MiscDisc = '"+fieldColumn7+"'   ";
                    break;
                case "Tickets":
                    SQL = "UPDATE Tickets SET TicketID = '"+fieldColumn1.getText()+"' , TicketType = '"+fieldColumn2.getText()+
                            "' , CustomerID = '"+fieldColumn3.getText()+"', PaymentID = '"+fieldColumn4.getText()+
                            "', AssignedAdvisor = '"+fieldColumn5.getText()+"', Coupon1 = '"+fieldColumn6.getText()+
                            "', Coupon2 = '"+fieldColumn7.getText()+"', Coupon3 = '"+fieldColumn8.getText()+
                            "', Coupon4 = '"+fieldColumn9+"'   ";
                    break;
                case "Sales":
                    SQL = "UPDATE Sales SET SaleID = '"+fieldColumn1.getText()+"' , TicketID = '"+fieldColumn2.getText()+
                            "' , PaymentID = '"+fieldColumn3.getText()+"', SaleDate = '"+fieldColumn4.getText()+
                            "', AdvisorID = '"+fieldColumn5.getText()+"' ";
                    break;
                case "Refunds":
                    SQL = "UPDATE Refunds SET RefundID = '"+fieldColumn1.getText()+"' , SaleID = '"+fieldColumn2.getText()+
                            "' , Amount = '"+fieldColumn3.getText()+"', DateRefunded = '"+fieldColumn4.getText()+
                            "', AdvisorID = '"+fieldColumn5.getText()+"'   ";
                    break;
                case "Payments":
                    SQL = "UPDATE Payments SET PaymentID = '"+fieldColumn1.getText()+"' , PaymentType = '"+fieldColumn2.getText()+
                            "' , CardNumber = '"+fieldColumn3.getText()+"', CardExpiry = '"+fieldColumn4.getText()+
                            "', CardSecurity = '"+fieldColumn5.getText()+"', Subtotal = '"+fieldColumn6.getText()+
                            "', Discount = '"+fieldColumn7.getText()+"', Total = '"+fieldColumn8.getText()+
                            "', Paid = '"+fieldColumn9.getText()+"', Deadline = '"+fieldColumn10.getText()+
                            "', Refunded = '"+fieldColumn11.getText()+"', AdvisorID = '"+fieldColumn12+"'    ";
                    break;
                case "LoggedIn":
                    SQL = "UPDATE LoggedIn SET ID = '"+fieldColumn1.getText()+"' ";
                    break;
            }

        }else {
            feedback.setText("Error");
        }

        //Edit Database, add record
        try {
            SQLHandler.executeUpdate(SQL);
            feedback.setText("Success");
        } catch (SQLException e) {
            e.printStackTrace();
            feedback.setText("Error");
        }

    }

    //EDIT RECORD - POPULATE FORM WITH EXISTING DATA
    public void PopulateForm() throws SQLException, ClassNotFoundException {

        ResultSet rs;

        switch (tableName){
            case "User":
                //Populating forms with any existing discounts.
                rs = SQLHandler.execute("SELECT * FROM User WHERE UserID = '"+ID+"' ");

                //IF RECORD ALREADY EXISTS - Set all of the form values to the existing ones.  Allows OM to view the data.
                if (rs.isBeforeFirst()){
                    rs.next();

                    fieldColumn1.setText(String.valueOf(rs.getInt("UserID")));
                    fieldColumn2.setText(rs.getString("Forename"));
                    fieldColumn3.setText(rs.getString("Surname"));
                    fieldColumn4.setText(rs.getString("Email"));
                    fieldColumn5.setText(rs.getString("Password"));
                    fieldColumn6.setText(rs.getString("JobRole"));

                }
                break;
            case "Customers":
                //Populating forms with any existing discounts.
                rs = SQLHandler.execute("SELECT * FROM Customers WHERE CustomerID = '"+ID+"' ");

                //IF RECORD ALREADY EXISTS - Set all of the form values to the existing ones.  Allows OM to view the data.
                if (rs.isBeforeFirst()){
                    rs.next();

                    fieldColumn1.setText(String.valueOf(rs.getInt("CustomerID")));
                    fieldColumn2.setText(rs.getString("Type"));
                    fieldColumn3.setText(rs.getString("Forename"));
                    fieldColumn4.setText(rs.getString("Surname"));
                    fieldColumn5.setText(rs.getString("Email"));
                    fieldColumn6.setText(String.valueOf(rs.getBoolean("Discount")));

                }
                break;
            case "Commissions":
                //Populating forms with any existing discounts.
                rs = SQLHandler.execute("SELECT * FROM Commissions WHERE CommissionID = '"+ID+"' ");

                //IF RECORD ALREADY EXISTS - Set all of the form values to the existing ones.  Allows OM to view the data.
                if (rs.isBeforeFirst()){
                    rs.next();

                    fieldColumn1.setText(String.valueOf(rs.getInt("CommissionID")));
                    fieldColumn2.setText(String.valueOf(rs.getInt("UserID")));
                    fieldColumn3.setText(String.valueOf(rs.getInt("Percentage")));

                }
                break;
            case "Discounts":
                //Populating forms with any existing discounts.
                rs = SQLHandler.execute("SELECT * FROM Discounts WHERE DiscountID = '"+ID+"' ");

                //IF RECORD exists - Set all of the form values to the existing ones.  Allows OM to view the data.
                if (rs.isBeforeFirst()){
                    rs.next();

                    fieldColumn1.setText(String.valueOf(rs.getInt("DiscountID")));
                    fieldColumn2.setText(String.valueOf(rs.getInt("CustomerID")));
                    fieldColumn3.setText(rs.getString("Type"));
                    fieldColumn4.setText(rs.getString("FixedDisc"));
                    fieldColumn5.setText(rs.getString("DomesticDisc"));
                    fieldColumn6.setText(rs.getString("InternationalDisc"));
                    fieldColumn7.setText(rs.getString("MiscDisc"));
                }
                break;
            case "LoggedIn":

                //Populating forms with any existing discounts.
                rs = SQLHandler.execute("SELECT * FROM LoggedIn WHERE ID = '"+ID+"' ");
                //IF RECORD ALREADY EXISTS - Set all of the form values to the existing ones.  Allows OM to view the data.
                if (rs.isBeforeFirst()){
                    rs.next();
                    fieldColumn1.setText(String.valueOf(rs.getInt("UserID")));
                }
                break;
        }
    }
}
