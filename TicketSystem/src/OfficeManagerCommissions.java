import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class OfficeManagerCommissions implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private Label feedback;

    @FXML
    private TableView tableView;

    @FXML
    private TextField inputField;

    //Table data
    private ObservableList<ObservableList> data;

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

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void ShowAll() {

        String SQL = "SELECT * from Commissions ";
        Show(SQL);
    }

    @FXML
    public void Search() {

        //Get the input data
        String inputFieldText = inputField.getText();

        String SQL = "SELECT * FROM Commissions WHERE (CommissionID = '"+ inputFieldText +
                "' OR UserID = '"+ inputFieldText+ "' OR Percentage = '"+inputFieldText+"' ) ";

        Show(SQL);

    }

    public void Show(String SQL){

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate

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
    public void New() throws IOException {

        OfficeManagerNewCommission.setMode("Add");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerNewCommission.fxml"));
        rootPane.getChildren().setAll(pane);

    }

    @FXML
    public void Edit() throws IOException {

        //Checking if a record has been selected
        boolean recordSelected;
        int convertedID = 0;

        try{
            //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
            Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
            String selectedID = selected[0].toString();
            convertedID = Integer.parseInt(selectedID);
            recordSelected = true;
        }
        catch (Exception e){
            recordSelected = false;
        }

        if (recordSelected) {

            OfficeManagerNewCommission.setMode("Edit");
            OfficeManagerNewCommission.setID(convertedID);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerNewCommission.fxml"));
            rootPane.getChildren().setAll(pane);

        } else {
            feedback.setText("Select the record you wish to edit.");
        }

    }

    @FXML
    public void Delete() throws IOException {

        //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        long convertedID = Long.parseLong(selectedID);

        //Remove from database
        String SQL = "DELETE FROM Commissions WHERE CommissionID= '"+convertedID+"' ";

        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Remove from tableView and refresh results
        tableView.getItems().removeAll(selected);
        ShowAll();
    }

}
