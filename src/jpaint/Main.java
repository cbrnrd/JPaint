package jpaint;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;

import java.awt.*;

import static jpaint.util.LogType;
import static jpaint.util.log;

/**
 * The class that loads the FXML file and shows it
 */
public class Main extends Application {

    @FXML
    private ColorPicker colorPicker;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("painter.fxml"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Scene scene = new Scene(root, width/1.5, height/1.5);
        primaryStage.setTitle("JPaint");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new javafx.scene.image.Image("https://cdn4.iconfinder.com/data/icons/proglyphs-design/512/Paint_Bucket-512.png"));
        log("Showing primaryStage", LogType.INFO);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
