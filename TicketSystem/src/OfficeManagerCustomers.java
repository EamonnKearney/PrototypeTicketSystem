import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class OfficeManagerCustomers implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    //Table data
    private ObservableList<ObservableList> data;

    @FXML
    private TableView tableView;

    @FXML
    private TextField searchField;

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
        //Populate table
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

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Searches db table for anything like the input search value.  Displays in table view.*/
    @FXML
    public void Search(){

        //Get the input data
        String inputFieldText = searchField.getText();

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "SELECT * FROM Customers WHERE (CustomerID = '"+ inputFieldText +
                "' OR Type = '" + inputFieldText +
                "' OR Forename = '" + inputFieldText +
                "' OR Surname = '" + inputFieldText +
                "' OR Email = '" + inputFieldText +
                "' ) ";
        data = FXCollections.observableArrayList();

        ResultSet rs = null;
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
    public void ShowAll(){

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "SELECT * from Customers ";
        data = FXCollections.observableArrayList();

        ResultSet rs = null;
        try {
            rs = SQLHandler.execute(SQL);

            //Add columns
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
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

    //Customer type
    @FXML
    public void SetCasual(){
        SetType("Casual");
    }

    //Customer type
    @FXML
    public void SetRegular(){
        SetType("Regular");
    }

    //Customer type
    @FXML
    public void SetValued(){
        SetType("Valued");
    }

    //Change type of customer
    public void SetType(String type){

        //Retrieve selected Customer from table
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        System.out.println(Long.valueOf(selectedID));
        long convertedID = Long.parseLong(selectedID);

        //Update Database: Set AssignedAdvisor in blank record to null

        String SQL = "UPDATE Customers SET Type = '"+type+"' WHERE CustomerID = '"+convertedID+"' ";

        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Refresh table
        ShowAll();
    }

    @FXML
    public void SetDiscount() throws IOException {

        //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
        //Then mess around with types to get it into a numeric value
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        int convertedID = Integer.parseInt(selectedID);

        OfficeManagerDiscounts.setCustomerID(convertedID);

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerDiscounts.fxml"));
        rootPane.getChildren().setAll(pane);

    }

}
