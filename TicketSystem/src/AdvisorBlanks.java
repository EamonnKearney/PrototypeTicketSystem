import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Eamonn Kearney 2020*/
public class AdvisorBlanks implements Initializable {
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

        //Populate Table
        try {
            ShowAll();
        } catch (IOException e) {
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
    public void Back() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Sell() throws IOException {

        //Checking if a record has been selected
        boolean recordSelected;
        long convertedID = 0;
        try{
            //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
            Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
            String selectedID = selected[0].toString();
            convertedID = Long.valueOf(selectedID);
            System.out.println(convertedID);
            recordSelected = true;
        }
        catch (Exception e){
            recordSelected = false;
        }
        if (recordSelected) {

            AdvisorSellBlank3.setBlankID(convertedID);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorSellBlank.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            feedback.setText("Select the blank you wish to sell.");
        }
    }

    @FXML
    public void Tickets() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdvisorTickets.fxml"));
        rootPane.getChildren().setAll(pane);

    }

    public void ChangeStatus(String status){
        //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
        //Then mess around with types to get it into a numeric value
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
        try {
            ShowAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Void() {

        ChangeStatus("Voided");

    }

    @FXML
    public void Blacklist()  {

        ChangeStatus("Blacklisted");

    }

    @FXML
    public void ReturnToAirline() throws IOException {

        //Change Status
        ChangeStatus("Returned");

        //Remove advisor
        //Retrieve selected blank from table
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        System.out.println(Long.valueOf(selectedID));
        long convertedID = Long.parseLong(selectedID);

        //Update Database: Set AssignedAdvisor in blank record to null
        String SQL = "UPDATE Blank SET AssignedAdvisor = '0' WHERE BlankID = '"+convertedID+"' ";

        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Refresh table
        ShowAll();

    }

    @FXML
    public void Search() {

        //Get the input data
        String inputFieldText = inputField.getText();

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "SELECT BlankID, Type, Status from Blank WHERE AssignedAdvisor = '"+userID+"' AND " +
                "( BlankID = '"+ inputFieldText +
                "' OR Type = '" + inputFieldText +
                "' OR Status = '" + inputFieldText +
                "' )";
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
                System.out.println("Column [" + i + "] ");
            }

            //Data added to observable list
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);


    }

    @FXML
    public void ShowAll() throws IOException {
        //The default
        //Searches through all blank records and retrieves those with AssignedAdvisor set to the current user.

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "SELECT BlankID, Type, Status from Blank WHERE AssignedAdvisor = '"+userID+"' ";
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
