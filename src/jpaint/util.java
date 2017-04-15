package jpaint;

import javafx.scene.control.Alert;
import jpaint.exceptions.AlertException;
import jpaint.exceptions.NoMessageException;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;

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
     * @throws AlertException Thrown if the alert doesn't show
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
     * @throws AlertException Thrown if the alert doesn't show
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
     * @throws AlertException Thrown if the alert doesn't show
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
        return Toolkit.getDefaultToolkit().getScreenSize();
    }


    /**
     * Defines the kind of message displayed in the log
     */
    enum LogType {
        ERROR, INFO, WARNING, SUCCESS
    }


    /**
     * Writes a message to <code>.jpaint_log</code> with {@link jpaint.util.LogType} <code>logType</code>}.
     * This has no association to {@link java.util.logging}
     * @param message The message to output.
     * @param logtype the type of message <code>message</code> is.
     * @since 1.0.3
     */
    public static void log(String message, LogType logtype){

        try {
            if (message == null) {
                throw new NoMessageException();
            }
        } catch (NoMessageException nme){
            System.err.println("Log message can't be empty");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String logmsg = "";
        logmsg += '[' + timeStamp + ']';


        if (logtype == LogType.ERROR) logmsg += " [ERROR]: " + message;
        else if (logtype == LogType.INFO) logmsg += " [INFO]: " + message;
        else if (logtype == LogType.WARNING) logmsg += " [WARN]: " + message;
        else if (logtype == LogType.SUCCESS) logmsg += " [SUCCESS]: " + message;
        else logmsg += message;

        // File IO writers
        BufferedWriter bw = null;
        FileWriter fw = null;
        PrintWriter out = null;

        try {

            File file = new File(".jpaint_log");

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);

            out.println(logmsg);

        } catch (IOException e) {

            log(e.getStackTrace().toString(), LogType.ERROR);

        } finally {
            try {
                if(out != null)
                    out.close();
            } catch (Exception e) {
                //exception handling left as an exercise for the reader
            }
            try {
                if(bw != null)
                    bw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            try {
                if(fw != null)
                    fw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }


    }





}


