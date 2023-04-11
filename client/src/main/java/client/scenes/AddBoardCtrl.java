package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class AddBoardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private volatile boolean isNotUpdated = true;
    private boolean tryingToAdd = false;

    private volatile Board newBoard;

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
        if (tryingToAdd) return;
        try {
            tryingToAdd = true;
            server.registerForUpdatesSockets("/topic/boards/update", Board.class, b -> {
                if (tryingToAdd == true) {
                    Platform.runLater(() -> {
                        title.clear();
                        mainCtrl.closeAddBoardSuccess(b);
                        mainCtrl.showBoard(b);
                        tryingToAdd = false;
                    });
                }
            });
            server.send("/app/boards/add",  getBoard());
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Board getBoard() {
        return new Board(title.getText());
    }
}

