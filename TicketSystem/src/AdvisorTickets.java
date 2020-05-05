import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class AdvisorTickets implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label labelID;
    @FXML
    private Label labelName;
    private int userID;
    @FXML
    private TableView tableView;
    //Table data
    private ObservableList<ObservableList> data;
    @FXML
    private TextField inputField;
    @FXML
    private Label feedback;
    @FXML
    private ChoiceBox tableChoice;
    @FXML
    private Button refundButton;
    @FXML
    private Button paidButton;
    @FXML
    private Button unpaidButton;

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

        //Populate choice box
        tableChoice.setItems(FXCollections.observableArrayList("Tickets", "Sales", "Payments", "Refunds"));
        tableChoice.setOnAction(this::ChangeTable);
        tableChoice.setValue("Tickets");

        ShowAll();

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

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Blanks() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorBlanks.fxml"));
        rootPane.getChildren().setAll(pane);

    }

    public void ChangeStatus(boolean status){
        //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        long convertedID = Long.parseLong(selectedID);
        String SQL = "";
        if (status == true){
            SQL = "UPDATE Payments SET Paid = TRUE WHERE PaymentID = '"+convertedID+"' ";
        }
        else{
            SQL = "UPDATE Payments SET Paid = FALSE WHERE PaymentID = '"+convertedID+"' ";
        }

        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Update Table
        ShowAll();
    }

    @FXML
    public void Paid() {
        ChangeStatus(true);
    }

    @FXML
    public void Unpaid()  {
        ChangeStatus(false);
    }

    @FXML
    public void Refund() {

        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        long ticketID = Long.parseLong(selectedID);

        try {
            //Retrieve Sale ID
            ResultSet rs = SQLHandler.execute("SELECT SaleID FROM Sales WHERE TicketID = '"+ticketID+"'");
            rs.next();
            int saleID = rs.getInt("SaleID");

            rs = SQLHandler.execute("SELECT PaymentID FROM Tickets WHERE TicketID = '"+ticketID+"'");
            rs.next();
            int paymentID = rs.getInt("PaymentID");


            //SQL - Set status of ticket
            String ticketSQL = "UPDATE Payments Set Refunded = TRUE WHERE PaymentID = '"+paymentID+"' ";
            SQLHandler.executeUpdate(ticketSQL);

            //SQL - Create refund record
            int refundID = GenerateCode.RandomID();
            String refundSQL = "INSERT INTO Refunds VALUES ( '"+refundID+"' , '"+saleID+"' , '"+paymentID+
                    "' , '"+ LocalDate.now() +"' , '"+userID+"'   ) ";
            SQLHandler.executeUpdate(refundSQL);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void Search() {

        //Get the input data
        String inputFieldText = inputField.getText();

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "";
        switch (tableChoice.getValue().toString()){
            case "Tickets":
                SQL = "SELECT * FROM Tickets WHERE AssignedAdvisor = '"+userID+"' AND " +
                        "( TicketID = '"+ inputFieldText +
                        "' OR TicketType = '" + inputFieldText +
                        "' OR CustomerID = '" + inputFieldText +
                        "' OR PaymentID = '" + inputFieldText +
                        "' OR Coupon1 = '" + inputFieldText +
                        "' OR Coupon2 = '" + inputFieldText +
                        "' OR Coupon3 = '" + inputFieldText +
                        "' OR Coupon4 = '" + inputFieldText +
                        "' )";
                break;
            case "Payments":
                SQL = "SELECT * FROM Payments WHERE AssignedAdvisor = '"+userID+"' AND " +
                        "( PaymentID = '"+ inputFieldText +
                        "' OR PaymentType = '" + inputFieldText +
                        "' OR CardNumber = '" + inputFieldText +
                        "' OR CardExpiry = '" + inputFieldText +
                        "' OR CardSecurity = '" + inputFieldText +
                        "' OR Subtotal = '" + inputFieldText +
                        "' OR Discount = '" + inputFieldText +
                        "' OR Total = '" + inputFieldText +
                        "' OR Paid = '" + inputFieldText +
                        "' OR Deadline = '" + inputFieldText +
                        "' OR Refunded = '" + inputFieldText +
                        "' )";
                break;
            case "Sales":
                SQL = "SELECT * FROM Sales WHERE AssignedAdvisor = '"+userID+"' AND " +
                        "( SaleID = '"+ inputFieldText +
                        "' OR TicketID = '" + inputFieldText +
                        "' OR PaymentID = '" + inputFieldText +
                        "' )";
                break;
            case "Refunds":
                SQL = "SELECT * FROM Refunds WHERE AssignedAdvisor = '"+userID+"' AND " +
                        "( RefundID = '"+ inputFieldText +
                        "' OR SaleID = '" + inputFieldText +
                        "' OR PaymentID = '" + inputFieldText +
                        "' )";
                break;
        }


        data = FXCollections.observableArrayList();

        ResultSet rs;
        try {
            rs = SQLHandler.execute(SQL);

            //Add columns
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
            }

            //Data added to observable list
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
    }

    @FXML
    public void ShowAll() {
        //The default
        //Searches through all relevant records and retrieves those with AssignedAdvisor set to the current user.

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL ="";
        if (tableChoice.getValue().toString().equals("Tickets")){
            SQL = "SELECT * FROM "+tableChoice.getValue().toString()+" WHERE AssignedAdvisor = '"+userID+"'";
        }
        else{
            SQL = "SELECT * FROM "+tableChoice.getValue().toString()+" WHERE AdvisorID = '"+userID+"' ";
        }

        data = FXCollections.observableArrayList();

        ResultSet rs;
        try {
            rs = SQLHandler.execute(SQL);
            //Add columns
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }

            //Data added to observable list
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
    }

    public void ChangeTable(Event event){

        switch (tableChoice.getValue().toString()){
            case "Tickets":
                refundButton.setDisable(false);
                paidButton.setDisable(true);
                unpaidButton.setDisable(true);
                break;
            case "Payments":
                refundButton.setDisable(true);
                paidButton.setDisable(false);
                unpaidButton.setDisable(false);
                break;
            case "Sales":
            case "Refunds":
                refundButton.setDisable(true);
                paidButton.setDisable(true);
                unpaidButton.setDisable(true);
                break;
        }

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL ="";
        if (tableChoice.getValue().toString().equals("Tickets")){
            SQL = "SELECT * FROM "+tableChoice.getValue().toString()+" WHERE AssignedAdvisor = '"+userID+"'";
        }
        else{
            SQL = "SELECT * FROM "+tableChoice.getValue().toString()+" WHERE AdvisorID = '"+userID+"' ";
        }
        data = FXCollections.observableArrayList();

        ResultSet rs;
        try {
            rs = SQLHandler.execute(SQL);
            //Add columns
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList,
                        String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableView.getColumns().addAll(col);
            }

            //Data added to observable list
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
    }

}
