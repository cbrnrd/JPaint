package painter;
/**
 * @author Carter Brainerd
 * @version 1.0.0 09 Apr 2017
 */
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.io.File;

public class Controller {

    /**
     * Pass objects through from <code>painter.fxml</code>
     */
    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox eraser;

    /**
     * Called automatically by the <code>FXMLLoader</code>
     * Allows for the actual painting to happen on the <code>Canvas</code>
     * @since 1.0.0
     */
    public void initialize(){
        GraphicsContext g = canvas.getGraphicsContext2D();

        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;

            if (eraser.isSelected()){
                g.clearRect(x, y, size, size);
            } else {
                g.setFill(colorPicker.getValue());
                g.fillRect(x, y, size, size);
            }
        });
    }

    /**
     * Saves a <code>png</code> snapshot of the image (as of 1.0.0, it's <code>paint.png</code>)
     * @since 1.0.0
     */
    public void onSave(){
        try{
            Image snapshot = canvas.snapshot(null, null);

            // Save image to file
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e){
            alertUser(null, "Unable to save. Error:" + e.getMessage(), "Error saving", Alert.AlertType.ERROR);
        }
    }

    /**
     * Exits out of the program
     * @since 1.0.0
     */
    public void onExit(){
        Platform.exit();
    }

    /**
     * Displays the "about" message using <code>alertUser</code>
     * @since 1.0.0
     */
    public void displayAbout(){
        String s = "Author: Carter Brainerd\n" +
                "Painter version: 1.0.0\n" +
                "Painter is a free and open source software written in JavaFX.\n" +
                "See the source here: https://github.com/thecarterb/Painter\n";
        alertUser("About Painter", s, "About Painter", Alert.AlertType.INFORMATION);
    }

    /**
     * Displays a custom dialog (MessageBox) to the end user
     *
     * @since 1.0.0
     * @param header The header text of the alert
     * @param s The String to display
     * @param title The title of the alert
     * @param alertType The <code>StageStyle</code> for the dialog
     * @see   javafx.stage.StageStyle
     */
    public void alertUser(String header, String s, String title, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
