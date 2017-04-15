package jpaint;
/**
 * @author Carter Brainerd
 * @version 1.0.2 10 Apr 2017
 */
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jpaint.exceptions.AlertException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static jpaint.util.*;



/**
 * The class with the FXML functionality methods
 */
@SuppressWarnings("JavaDoc") // For custom tags
public class PaintController {


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
    private final FileChooser fileChooser = new FileChooser();

    // For onOpen
    private final FileChooser openFileChooser = new FileChooser();

    // For setBrushBrush and setBrushPencil
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
        Dimension screenSize = getScreenSize();
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
                if (isBrushBrush) {
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
            infoAlert("Image saved to " + new File("paint.png").getAbsolutePath(), "Save successful.");
            log("Image saved to " + new File("paint.png").getAbsolutePath(), LogType.SUCCESS);

        } catch (Exception e){
            try {
                errorAlert("Unable to save. Error:" + e.getMessage(), "Error");
                log("Unable to save. Error:" + e.getStackTrace(), LogType.ERROR);
            } catch (AlertException ae){
                ae.printStackTrace();
            }
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
            String filepath = file.getAbsolutePath();

            // This is just a failsafe
            if (file != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                log("Image saved to " + filepath, LogType.SUCCESS);
            } else {
                infoAlert("Please choose a filename.", null);
            }
        } catch (Exception e){
            try {
                errorAlert("Error saving file.\nError: " + e.getMessage(), "Error");
                log("Unable to save file. Error: " + e.getStackTrace(), LogType.ERROR);
            } catch (AlertException ae){
                ae.printStackTrace();
            }
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
                infoAlert("Please choose a file.", null);
                log("Tried to open a file with a blank filename", LogType.WARNING);
            }
        } catch (Exception e){
            log("Couldn't open file. Error: " + e.getStackTrace().toString(), LogType.ERROR);
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
     * Displays the "about" message using <code>Util.alertUser(String, String, String, Alert.AlertType)</code>
     * @since 1.0.0
     */
    public void displayAbout(){
        String s = "Author: Carter Brainerd\n" +
                "JPaint version: 1.0.3\n" +
                "JPaint is a free and open source software written in JavaFX.\n" +
                "See the source here: https://github.com/thecarterb/JPaint\n";
        try {
            alertUser("About JPaint", s, "About JPaint", Alert.AlertType.INFORMATION);
        } catch (AlertException ae){
            log(ae.toString(), LogType.ERROR);
        }
    }


    /**
     * Opens a GitHub link to the source code of JPaint
     * @since 1.0.3
     * @throws AlertException If the error alert can't show
     * @throws IOException If the GitHub URL wasn't reachable
     */
    public void openSource() throws AlertException, IOException{
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/thecarterb/JPaint"));
            } catch (IOException | URISyntaxException e){
                errorAlert("Unable to open URL", "Error");
                log(e.getMessage(), LogType.ERROR);
            }
        } else {
            System.err.println("Unable to open source.");
            log("Desktops are not supported on your OS!", LogType.WARNING);
        }
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
     * Clears the <code>canvas</code>
     *
     * @since 1.0.3
     */
    public void onClear() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, 10000, 10000);
        log("Canvas cleared", LogType.INFO);
    }
}
