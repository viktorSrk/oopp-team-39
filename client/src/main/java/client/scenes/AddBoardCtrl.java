package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class AddBoardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField title;
    @Inject
    public AddBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void cancel() {
        title.clear();
        mainCtrl.closeAddBoard();
    }

    public void ok() {
        try {
            Board newBoard = getBoard();
            newBoard = server.addBoard(newBoard);
            if (newBoard == null) {
                throw new Exception("adding board failed");
            }
            title.clear();
            mainCtrl.closeAddBoard();
            mainCtrl.showBoard(newBoard);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
        }
    }

    private Board getBoard() {
        return new Board(title.getText());
    }
}

