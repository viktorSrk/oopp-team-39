package client.utils;

import javafx.scene.control.Alert;

public class FrontEndUtils {
    public static void ErrorPopUp (String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }
}
