package jpaint;

import javafx.scene.control.Alert;
import jpaint.exceptions.AlertException;

import java.awt.*;

/**
 * A class containing utility methods for <code>PaintController</code>
 */
public class util {


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
    public static void alertUser(String header, String s, String title, Alert.AlertType alertType) throws AlertException{
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
    public static void infoAlert(String s, String header) throws AlertException{
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
    public static void errorAlert(String s, String header) throws AlertException{

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.setHeaderText(header);
        alert.showAndWait();
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
