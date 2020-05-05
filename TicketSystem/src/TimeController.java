import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TimeController {

    /**Called when Advisors log in.  Checks for unpaid tickets past their deadline and alerts Advisor if one is found.
     * Eamonn Kearney 2020*/
    public static void CheckForUnpaid() {

        //Check for any unpaid tickets past their payment deadline.
        //Current date
        Date today = new Date();
        //Check each registered payment
        try {
            ResultSet rs = SQLHandler.execute("SELECT Paid, Deadline FROM Payments");
            boolean unpaidNotFound = true;
            while(rs.next() && unpaidNotFound ){
                Date deadline = rs.getDate("Deadline");
                boolean hasPaid = rs.getBoolean("Paid");
                if (deadline.before(today) && !hasPaid){
                    unpaidNotFound = false;
                    Alert alert = new Alert(AlertType.NONE);
                    alert.setAlertType(AlertType.WARNING);
                    alert.setContentText("There are unpaid tickets that have passed their deadlines.\nPlease review.");
                    alert.show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}