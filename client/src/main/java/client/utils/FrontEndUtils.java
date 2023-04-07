package client.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class FrontEndUtils {
    public static void errorPopUp (String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }

    public static ButtonType confirmation () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("deleting");
        alert.setHeaderText("are you sure you want to delete this?");
        alert.showAndWait();
        return alert.getResult();
    }
}
