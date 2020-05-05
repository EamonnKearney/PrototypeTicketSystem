import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Discounts Screen - Viewing and editing Discount plans for customers
 * Eamonn Kearney 2020*/
public class OfficeManagerDiscounts implements Initializable {

    //Use this to keep the ID of the selected record in Customer table (OfficeManagerCustomers)
    private static int CustomerID;

    public static int getCustomerID() {
        return CustomerID;
    }

    public static void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private Label customerIDLabel;

    @FXML
    private CheckBox flexibleCheck;

    @FXML
    private HBox fixedForm;

    @FXML
    private HBox flexibleForm;

    @FXML
    private TextField discountIDField;

    @FXML
    private Spinner<Integer> fixedDiscountSpinner;

    @FXML
    private Spinner<Integer> domesticSpinner;

    @FXML
    private Spinner<Integer> internationalSpinner;

    @FXML
    private Spinner<Integer> miscSpinner;

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

        //SPINNERS
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);

        StringConverter conv = new StringConverter<Integer>() {

            @Override
            public String toString(Integer value) {
                return value.toString()+"%";
            }

            @Override
            public Integer fromString(String string) {
                String valueWithoutUnits = string.replaceAll("%", "").trim();
                if (valueWithoutUnits.isEmpty()) {
                    return 0 ;
                } else {
                    return Integer.valueOf(valueWithoutUnits);
                }
            }

        };

        valueFactory.setConverter(conv);
        valueFactory1.setConverter(conv);
        valueFactory2.setConverter(conv);
        valueFactory3.setConverter(conv);

        fixedDiscountSpinner.setEditable(true);
        fixedDiscountSpinner.setPromptText("0-100");
        fixedDiscountSpinner.setValueFactory(valueFactory);
        domesticSpinner.setEditable(true);
        domesticSpinner.setPromptText("0-100");
        domesticSpinner.setValueFactory(valueFactory1);
        internationalSpinner.setEditable(true);
        internationalSpinner.setPromptText("0-100");
        internationalSpinner.setValueFactory(valueFactory2);
        miscSpinner.setEditable(true);
        miscSpinner.setPromptText("0-100");
        miscSpinner.setValueFactory(valueFactory3);

        //Check box
        flexibleCheck.setOnAction(event -> {
            try {
                SwitchFixedFlex();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        //Let only one form appear at a time - either fixed discount or flexible discount
        try {
            SwitchFixedFlex();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Present either the fixed discount form or flexible discount form
    public void SwitchFixedFlex() throws SQLException, ClassNotFoundException {
        if (flexibleCheck.isSelected()){
            fixedForm.setDisable(true);
            fixedForm.setVisible(false);
            flexibleForm.setDisable(false);
            flexibleForm.setVisible(true);
        }
        else {
            fixedForm.setDisable(false);
            fixedForm.setVisible(true);
            flexibleForm.setDisable(true);
            flexibleForm.setVisible(false);
        }

        PopulateForms();
    }

    @FXML
    public void Apply(){

        String UpdateUsers = "";
        String UpdateCommissions = "";

        UpdateUsers = "UPDATE User SET Discount = TRUE ";

        int discountID = Integer.parseInt(discountIDField.getText());
        int customerID = getCustomerID();
        int fixedDiscount;
        int domesticDiscount;
        int internationalDiscount;
        int miscDiscount;
        String type;

        if (flexibleCheck.isSelected()){
            //FLEX Selected --> Flexible form
            fixedDiscount = 0;
            domesticDiscount = domesticSpinner.getValue();
            internationalDiscount = internationalSpinner.getValue();
            miscDiscount = miscSpinner.getValue();
            type = "Flexible";
        }
        else {
            //FLEX Not Selected --> Fixed form
            fixedDiscount = fixedDiscountSpinner.getValue();
            domesticDiscount = 0;
            internationalDiscount = 0;
            miscDiscount = 0;
            type = "Fixed";
        }

        UpdateCommissions = "UPDATE Discounts SET DiscountID = '"+discountID+"', CustomerID = '"+customerID+"', Type = '"+
                type+"', FixedDisc = '" +fixedDiscount+"', DomesticDisc = '"+domesticDiscount+"', InternationalDisc = '"+
                internationalDiscount+"', MiscDisc = '"+miscDiscount+"'  ";

        try {
            SQLHandler.executeUpdate(UpdateUsers);
            SQLHandler.executeUpdate(UpdateCommissions);
            feedback.setText("Success");
        } catch (SQLException e) {
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
    public void Back() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerCustomers.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void PopulateForms() throws SQLException, ClassNotFoundException {

        //Populating forms with any existing discounts.
        ResultSet rs = SQLHandler.execute("SELECT * FROM Discounts WHERE CustomerID = '"+getCustomerID()+"' ");

        //IF RECORD ALREADY EXISTS - Set all of the form values to the existing ones.  Allows OM to view the data.
        if (rs.isBeforeFirst()){
            rs.next();

            //Discount details for populating forms
            int discountID = rs.getInt("DiscountID");
            int customerID = rs.getInt("CustomerID");
            String type = rs.getString("Type");
            int fixedDiscount = rs.getInt("FixedDisc");
            int domesticDiscount = rs.getInt("DomesticDisc");
            int internationalDiscount = rs.getInt("InternationalDisc");
            int miscDiscount = rs.getInt("MiscDisc");

            discountIDField.setText(String.valueOf(discountID));
            fixedDiscountSpinner.getValueFactory().setValue(fixedDiscount);
            domesticSpinner.getValueFactory().setValue(domesticDiscount);
            internationalSpinner.getValueFactory().setValue(internationalDiscount);
            miscSpinner.getValueFactory().setValue(miscDiscount);

            customerIDLabel.setText("Customer ID: "+ customerID);
        }

    }

    @FXML
    public void GenerateID(){
        discountIDField.setText(String.valueOf(GenerateCode.RandomID()));
    }
}
