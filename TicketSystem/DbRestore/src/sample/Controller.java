package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Controller {
    @FXML

    public void restoreDB(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        String mysqlCom = String.format("/Applications/MAMP/Library/bin/mysql -u%s -p%s %s", "root", "root", "Team24");
        System.out.println(mysqlCom);
        String[] command = new String[]{"/bin/bash", "-c", mysqlCom};
        try {
            System.out.println(file.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Restore " + file + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                File backupFile = new File(file.getAbsolutePath());
                ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command));
                processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
                processBuilder.redirectInput(ProcessBuilder.Redirect.from(backupFile));
                Process process = processBuilder.start();
                process.waitFor();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Restore completed", ButtonType.OK);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.OK) {
                    System.out.println("DONE");
                }
            }

        } catch (
                IOException | InterruptedException e1) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR, "Restore failed. Please double check the restore file", ButtonType.OK);
            alert3.showAndWait();
            e1.printStackTrace();
        }
    }
}
