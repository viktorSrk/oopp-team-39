package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.CardList;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardCtrl implements Initializable {

    private final ServerUtils server;

    @Inject
    public BoardCtrl(ServerUtils server) {
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: When the data structures are finished
    }

    public void addList() {
        try {
            server.addList(new CardList());
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void addCard() {
        // TODO: mainCtrl.showAddCard();
    }
}
