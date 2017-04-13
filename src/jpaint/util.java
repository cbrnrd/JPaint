package jpaint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
     * Used only for {@link #noWinDecor(Stage)}
     */
    private static class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");

            closeBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            this.getChildren().add(closeBtn);
        }
    }

    /**
     * Takes away all OS specific window borders (Minimize, Maximize, and Close buttons, etc.)
     * <p> </p>
     * This will eventually be used for a splash screen
     * @param initStage the stage to make have no window borders
     * @since 1.0.2
     */
    public static void noWinDecor(Stage initStage){
        initStage.initStyle(StageStyle.UNDECORATED);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: grey;");

        ToolBar toolBar = new ToolBar();

        int height = 25;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);
        toolBar.getItems().add(new WindowButtons());

        borderPane.setTop(toolBar);

        initStage.setScene(new Scene(borderPane, 300, 250));
        initStage.show();

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

    /**
     * Changed the default icon of <code>alert</code> to the JavaFX default "error" icon
     * @param alert The alert to change the icon of
     */
    public static void setGraphicToError(Alert alert){
        javafx.scene.control.Label label = new javafx.scene.control.Label();
        label.getStyleClass().addAll("alert", "error", "dialog-pane");
        alert.setGraphic(label);
    }

    /**
     * Display a custom <code>CONFIRMATION</code> dialog when exiting the program
     * @return boolean - Whether or not "YES" is clicked or not. True if yes, false if no.
     */
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
