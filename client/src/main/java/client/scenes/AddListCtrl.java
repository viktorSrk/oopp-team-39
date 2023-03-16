package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.List;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class AddListCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField title;
    @Inject
    public AddListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void cancel() {
        title.clear();
        mainCtrl.showBoard();
    }

    public void ok() {
        try {
            server.addList(getList());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        title.clear();
        mainCtrl.showBoard();
    }

    public List getList(){
        var l = new List(title.getText());
        return l;
    }
}

