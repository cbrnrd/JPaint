package jpaint;

import javafx.scene.control.*;

import java.awt.*;
import java.awt.Label;
import java.util.Optional;

/**
 * A class containing utility methods for <code>Controller</code>
 */
public class util {


    /**
     * Displays a custom dialog to the end user
     *
     * @since 1.0.0
     * @param header The header text of the alert
     * @param s The String to display
     * @param title The title of the alert
     * @param alertType The <code>Alert.AlertType</code> for the dialog
     * @see   javafx.scene.control.Alert.AlertType
     */
    public static void alertUser(String header, String s, String title, Alert.AlertType alertType){
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
     * @param header The header of the alert (usually a summary)
     */
    public static void infoAlert(String s, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * Display an error alert with message <code>s</code>
     * @param s The message to display
     * @param header The header of the error (usually a summary)
     */
    public static void errorAlert(String s, String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public static void setGraphicToError(Alert alert){
        javafx.scene.control.Label label = new javafx.scene.control.Label();
        label.getStyleClass().addAll("alert", "error", "dialog-pane");
        alert.setGraphic(label);
    }

    public static boolean confirmExit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Would you like to exit?");
        alert.setHeaderText("Exit");
        util.setGraphicToError(alert);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the current screen size
     * @return Dimension
     */
    public static Dimension getScreenSize(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize;

    }

}
