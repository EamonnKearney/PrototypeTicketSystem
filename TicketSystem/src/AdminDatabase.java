import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

/**Admin can view database tables, add records, delete, edit.
 * Eamonn Kearney 2020*/
public class AdminDatabase implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private ChoiceBox tableChoice;

    @FXML
    private TableView tableView;

    @FXML
    private Label feedback;

    private ObservableList<ObservableList> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Retrieve currently logged in user ID and Name
        SQLHandler sh = new SQLHandler();
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

        //Populate choice box
        tableChoice.setItems(FXCollections.observableArrayList("User", "Customers", "Commissions", "Discounts",
                "Blank", "Tickets", "Sales","Payments", "Refunds", "LoggedIn"));
        tableChoice.setOnAction(this::ChangeTable);
        tableChoice.setValue("User");

        //Table View
        tableView.setEditable(true);

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

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**Switches which database table is displayed in table View
     * @param event*/
    public void ChangeTable(Event event){

        //Depopulate
        tableView.getColumns().clear();

        //Repopulate
        String SQL = "SELECT * from "+tableChoice.getValue().toString()+"  ";
        data = FXCollections.observableArrayList();

        ResultSet rs = null;
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

    @FXML
    public void AddRow() throws IOException {

        AdminAddEditDB.setTableName(tableChoice.getValue().toString());
        AdminAddEditDB.setMode("Add");

        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminAddEditDB.fxml"));
        rootPane.getChildren().setAll(pane);

    }

    @FXML
    public void EditRow() throws IOException {

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

            AdminAddEditDB.setTableName(tableChoice.getValue().toString());
            AdminAddEditDB.setMode("Edit");
            AdminAddEditDB.setID(convertedID);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminAddEditDB.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            feedback.setText("Select the record you wish to edit.");
        }
    }

    /**Delete row from chosen db table.  Record is the one selected by user.*/
    @FXML
    public void DeleteRow(){

        //Determine selected row by turning the row into an array and retrieving the first element to get the primary key
        //Then mess around with types to get it into a numeric value
        Object[] selected = data.get(tableView.getSelectionModel().getSelectedIndex()).toArray();
        String selectedID = selected[0].toString();
        long convertedID = Long.parseLong(selectedID);

        //Remove from database
        String SQL = "";
        switch (tableChoice.getValue().toString()){
            case "User":
                SQL = "DELETE FROM User WHERE UserID= '"+convertedID+"'  ";
                break;
            case "Customers":
                SQL = "DELETE FROM Customers WHERE CustomerID= '"+convertedID+"'  ";
                break;
            case "Commissions":
                SQL = "DELETE FROM Commissions WHERE CommissionID= '"+convertedID+"'  ";
                break;
            case "Discounts":
                SQL = "DELETE FROM Discounts WHERE DiscountID= '"+convertedID+"'  ";
                break;
            case "Tickets":
                SQL = "DELETE FROM Tickets WHERE TicketID= '"+convertedID+"'  ";
                break;
            case "Sales":
                SQL = "DELETE FROM Sales WHERE SaleID= '"+convertedID+"'  ";
                break;
            case "Payments":
                SQL = "DELETE FROM Payments WHERE PaymentID= '"+convertedID+"'  ";
                break;
            case "Refunds":
                SQL = "DELETE FROM Refunds WHERE RefundID= '"+convertedID+"'  ";
                break;
        }

        try {
            SQLHandler.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Remove from tableView
        tableView.getItems().removeAll(selected);


    }

    //RESTORE AND BACKUP
    public void BackupDatabase() {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm:ss"));
        String backupPath = String.format("%s/%s.%s", "C:\\Users\\44756\\Documents\\BackupATS", currentDate, "sql");
        File backupFile = new File(backupPath);
        if (!backupFile.exists()) {
            try {
                backupFile.createNewFile();
                String mysqlCom = String.format("C:\\Program Files\\MySQL\\MySQL Workbench 8.0 CE\\mysqldump -u%s -p%s %s", "root", "AlanTuring", "dbtest");
                String[] command = new String[]{"cmd.exe", "/c", mysqlCom};
                ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command));
                processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
                processBuilder.redirectOutput(ProcessBuilder.Redirect.to(backupFile));
                Process process = processBuilder.start();
                process.waitFor();
                feedback.setText("Database Backup Complete");
            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }

        } else {
            feedback.setText("Backup already exists");
        }
    }

    public void restoreDatabase() throws InterruptedException, IOException {
        FileChooser fileChooser = new FileChooser();
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQL files (.sql)", ".sql");
        //fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);

        String mysqlCom = String.format("C:\\Program Files\\MySQL\\MySQL Workbench 8.0 CE\\mysql -u%s -p%s %s", "root", "AlanTuring", "dbtest");
        System.out.println(mysqlCom);
        String[] command = new String[]{"cmd.exe", "/c", mysqlCom};
        try {
            System.out.println(file.getAbsolutePath());
            File backupFile = new File(file.getAbsolutePath());
            ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command));
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectInput(ProcessBuilder.Redirect.from(backupFile));
            Process process = processBuilder.start();
            process.waitFor();
            feedback.setText("Restore Complete");
        } catch (
                IOException | InterruptedException e1) {
            feedback.setText("Restore failed. Please double check the restore file");
            e1.printStackTrace();
        }
    }

}