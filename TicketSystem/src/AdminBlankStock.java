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

/**Admin can view a table of the ful blank stock and manage as necessary
 * Eamonn Kearney 2020*/
public class AdminBlankStock implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label labelID;
    @FXML
    private Label labelName;
    @FXML
    private TableView tableView;
    //Table data
    private ObservableList<ObservableList> data;
    @FXML
    private TextField inputField;
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
        //Populate Table
        ShowAll();
        feedback.setText("");
    }

    /**To User Profile screen*/
    @FXML
    public void Profile() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Login screen*/
    @FXML
    public void Logout() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Admin Dashboard*/
    @FXML
    public void Back() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**To Blank Creation screen*/
    @FXML
    public void Add() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminBlankCreation.fxml"));
        rootPane.getChildren().setAll(pane);

    }

    /**Change blank status (Voided, Blanklisted, Active, ...) */
    public void ChangeStatus(String status){
        //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        System.out.println(Long.valueOf(selectedID));
        long convertedID = Long.parseLong(selectedID);

        //Change Status to blacklisted
        String SQL = "UPDATE Blank SET Status = '"+status+"' WHERE BlankID= '"+convertedID+"' ";
        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Update Table
        ShowAll();
    }

    /**Void a selected blank*/
    @FXML
    public void Void() {

        ChangeStatus("Voided");

    }

    /**Mark a blank blacklisted*/
    @FXML
    public void Blacklist()  {

        ChangeStatus("Blacklisted");

    }

    /**Mark a blank active (default state)*/
    @FXML
    public void Active() {

        ChangeStatus("Active");
    }

    /**Return blank to airline by removing its advisor and setting status to "Returned" */
    @FXML
    public void ReturnToAirline() {

        //Change Status
        ChangeStatus("Returned");
        //Remove advisor
        //Retrieve selected blank from table
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        System.out.println(Long.valueOf(selectedID));
        long convertedID = Long.parseLong(selectedID);

        //Update Database: Set AssignedAdvisor in blank record to 0.  It will no longer appear in any tables where it may be sold.
        String SQL = "UPDATE Blank SET AssignedAdvisor = '0' WHERE BlankID = '"+convertedID+"' ";

        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Refresh table
        ShowAll();
    }

    /**Delete chosen record from Blank table*/
    @FXML
    public void Delete() {

        //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
        //Then mess around with types to get it into a numeric value
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        System.out.println(Long.valueOf(selectedID));
        long convertedID = Long.parseLong(selectedID);

        //Remove from database
        String SQL = "DELETE FROM Blank WHERE BlankID = '"+ convertedID+"' AND NOT Status = 'Sold'  ";

        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
            feedback.setText("Error: Ticket may be sold.");
        }
        //Remove from tableView
        tableView.getItems().removeAll(selected);
        ShowAll();
    }

    /**Search the blank table for a selected input value.  Searches all fields except Advisor(User)ID.*/
    @FXML
    public void Search() {

        //Get the input data
        String inputFieldText = inputField.getText();

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "SELECT * from Blank WHERE " +
                " BlankID = '"+ inputFieldText +
                "' OR Type = '" + inputFieldText +
                "' OR Status = '" + inputFieldText +
                "' ";
        data = FXCollections.observableArrayList();

        ResultSet rs;
        try {
            rs = SQLHandler.execute(SQL);
            //Add columns
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

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

    /**Display all blanks, reverses search.*/
    @FXML
    public void ShowAll() {
        //The default
        //Searches through all blank records and retrieves those with AssignedAdvisor set to the current user.

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "SELECT * from Blank";
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
}
