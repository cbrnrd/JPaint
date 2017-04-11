package painter;
/**
 * @author Carter Brainerd
 * @version 1.0.2 10 Apr 2017
 */
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Paint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;


/**
* The class with the FXML functionality methods
*/
@SuppressWarnings("JavaDoc")
public class Controller {


    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox eraser;

    @FXML
    private MenuButton brushSelectButton;

    // For onSaveAs
    final FileChooser fileChooser = new FileChooser();

    final FileChooser openFileChooser = new FileChooser();

    private boolean isBrushBrush;


    /**
     * Called automatically by the <code>FXMLLoader</code>.
     * Allows for the actual painting to happen on the <code>Canvas</code>
     * @since 1.0.0
     * @custom.Updated 1.0.2
     */
    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        setBrushBrush();
        // Get screen dimensions and set the canvas accordingly
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        canvas.setHeight(screenHeight/1.5);
        canvas.setWidth(screenWidth/1.5);

        canvas.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");  //Set the background to be translucent

        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if (eraser.isSelected()) {
                g.clearRect(x, y, size, size);
            } else {
                g.setFill(colorPicker.getValue());
                if(isBrushBrush) {
                    g.fillOval(x, y, size, size);
                } else {
                    g.fillRect(x, y, size, size);
                }
            }
        });

        canvas.setOnMouseClicked(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if (eraser.isSelected()) {
                g.clearRect(x, y, size, size);
            } else {
                g.setFill(colorPicker.getValue());
                if(isBrushBrush) {
                    g.fillOval(x, y, size, size);
                } else {
                    g.fillRect(x, y, size, size);
                }
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
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
            infoAlertUser("Image saved to " + new File("paint.png").getAbsolutePath());

        } catch (Exception e){
            alertUser("Error", "Unable to save. Error:" + e.getMessage(), "Error saving", Alert.AlertType.ERROR);
        }
    }


    /**
     * Opens a <code>FileChooser</code> window and saves the image as the inputted name.png
     * @see javafx.stage.FileChooser
     * @see javax.imageio.ImageIO
     * @since 1.0.1
     */
    public void onSaveAs(){
        Stage stage = new Stage(StageStyle.UTILITY);
        fileChooser.setTitle("Save Image As");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        try {

            Image snapshot = canvas.snapshot(null, null);
            File file = fileChooser.showSaveDialog(stage);

            // This is just a failsafe
            if (file != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
            } else {
                infoAlertUser("Please choose a filename.");
            }
        } catch (Exception e){
            alertUser(null, "Unable to save. \nError:" + e.getMessage(), "Error saving", Alert.AlertType.ERROR);
        }

    }

    /**
     * Opens a file and displays it on the <code>Canvas</code>
     * @since 1.0.1
     * @custom.Updated 1.0.2
     */
    public void onOpen(){
      GraphicsContext g = canvas.getGraphicsContext2D();

      Stage stage = new Stage(StageStyle.UTILITY);
      openFileChooser.setTitle("Open Image");

      // PNG file filter
      FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files", "*.png");
      openFileChooser.getExtensionFilters().add(pngFilter);

      // JPEG file filter
      FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPG files", "*.jpeg, *.jpg");
      openFileChooser.getExtensionFilters().add(jpegFilter);
      try{
        File openImageFile = openFileChooser.showOpenDialog(stage);
        InputStream fileStream = new FileInputStream(openImageFile);
        Image openImage = new Image(fileStream);

        if (openImageFile != null){
          g.drawImage(openImage, 0, 0);
        } else {
          infoAlertUser("Please choose a file.");
        }
      } catch (Exception e){
      alertUser(null, "Unable to open file. \nError:" + e.getMessage(), "Error opening", Alert.AlertType.ERROR);
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
     * Displays the "about" message using {@link #alertUser(String, String, String, Alert.AlertType)}
     * @since 1.0.0
     */
    public void displayAbout(){
        String s = "Author: Carter Brainerd\n" +
                "Painter version: 1.0.2\n" +
                "Painter is a free and open source software written in JavaFX.\n" +
                "See the source here: https://github.com/thecarterb/Painter\n";
        alertUser("About Painter", s, "About Painter", Alert.AlertType.INFORMATION);
    }

    /**
     * Setter for brush type (Changes it to circle)
     * @since 1.0.2
     */
    public void setBrushBrush(){
        isBrushBrush  = true;
        brushSelectButton.setText("Brush");
    }

    /**
     * Setter for brush type (Changes it to square)
     * @since 1.0.2
     */
    public void setBrushPencil(){
        isBrushBrush  = false;
        brushSelectButton.setText("Pencil");
    }

    /**
     * Displays a custom dialog to the end user
     *
     * @since 1.0.0
     * @param header The header text of the alert
     * @param s The String to display
     * @param title The title of the alert
     * @param alertType The <code>StageStyle</code> for the dialog
     * @see   javafx.scene.control.Alert.AlertType
     */
    public void alertUser(String header, String s, String title, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(s);
        alert.setResizable(false);
        alert.showAndWait();
    }


    /**
     * Shows an informational alert with message <code>s</code>
     * <p></p>
     * Simpler than {@link #alertUser(String, String, String, Alert.AlertType)}
     * @param s The message to display
     */
    public void infoAlertUser(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
