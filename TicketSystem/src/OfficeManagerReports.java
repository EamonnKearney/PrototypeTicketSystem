import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

/**Credit to Mohamed Dafallah 2020*/
public class OfficeManagerReports implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    private int userID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Retrieve currently logged in user ID and Name

        try {
            ResultSet rs = SQLHandler.execute("SELECT ID FROM LoggedIn");

            rs.next();
            userID = rs.getInt("ID");

            rs = SQLHandler.execute("SELECT * FROM User WHERE UserID = '" + userID + "'");
            rs.next();
            String forename = rs.getString("Forename");
            String surname = rs.getString("Surname");

            labelID.setText("User ID: "+userID);
            labelName.setText("User Name: "+forename+" "+surname);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Logout() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Profile() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void Domestic() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Individual_Domestic_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

        /* Export to pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint,"/Users/mohameddafallah/JaspersoftWorkspace/MyReports/Interline_Sales_Report.pdf");
        */

            // View the report
            JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }

    @FXML
    public void Interline() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Interline_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

        /* Export to pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint,"/Users/mohameddafallah/JaspersoftWorkspace/MyReports/Interline_Sales_Report.pdf");
        */

            // View the report
            JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }

    @FXML
    public void TicketStock() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C://Users/44756/Documents/Computer Science/Stage 2/Team Project/MyReports/Ticket_Stock_Turnover_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

        /* Export to pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint,"/Users/mohameddafallah/JaspersoftWorkspace/MyReports/Interline_Sales_Report.pdf");
        */

            // View the report
            JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }

    @FXML
    public void PDFTicketStock() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Ticket_Stock_Turnover_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

            //Export to pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Ticket_Stock_Turnover_Report.pdf");


            // View the report
            //JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    public void Back() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("OfficeManagerDash.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void GlobalDomestic() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Global_Domest_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

        /* Export to pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint,"/Users/mohameddafallah/JaspersoftWorkspace/MyReports/Interline_Sales_Report.pdf");
        */

            // View the report
            JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }


    @FXML
    public void GlobalInterline() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Global_Interline_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

        /* Export to pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint,"/Users/mohameddafallah/JaspersoftWorkspace/MyReports/Interline_Sales_Report.pdf");
        */

            // View the report
            JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }




    @FXML
    public void PDFDomestic() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("/C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Individual_Domestic_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

              // Export to pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Individual_Domestic_Sales_Report.pdf");


            // View the report
            //JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }
    @FXML
    public void PDFInterline() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Interline_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

            // Export to pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Interline_Sales_Report.pdf");


            // View the report
            //JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    @FXML
    public void PDFGlobalDomestic() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Global_Domest_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

            //Export to pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Global_Domest_Sales_Report.pdf");


            // View the report
            //JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    @FXML
    public void PDFGlobalInterline() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "root", "AlanTuring");

            JasperReport jasperreport = JasperCompileManager.compileReport("C://Users/44756/Documents/Computer Science/Stage 2/Team Project/MyReports/Global_Interline_Sales_Report.jrxml");
            System.out.println("Done compiling");


            //JRDataSource jrDataSource = new JREmptyDataSource(10);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,null,c);

            System.out.println("Done filling jasper report");

            //Export to pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\44756\\Documents\\Computer Science\\Stage 2\\Team Project\\MyReports/Global_Interline_Sales_Report.pdf");


            // View the report
            //JasperViewer.viewReport(jasperPrint);


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }




}
