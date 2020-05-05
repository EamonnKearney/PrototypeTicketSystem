import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**JavaFX Application
 * Eamonn Kearney 2020*/
public class Main extends Application{

    /**Runs first when an application is launched*/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); //Load FXML file (Contains GUI)
        Scene scene = new Scene(root); // JavaFX Scene
        stage.setTitle("Air Viva Ltd Ticket Sales System"); //Window Name
        stage.setScene(scene); //Adding Scene into window
        stage.show(); //Display
    }

    /**Main Method*/
    public static void main(String[] args) {
        launch(args); //Launch Application (start method)
    }
}

