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

    private boolean isNotUpdated = true;
    private boolean tryingToAdd = false;

    Board newBoard;

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
            tryingToAdd = true;
            server.send("/app/boards/add",  getBoard());
            server.registerForUpdates("/topic/boards/update", Board.class, b -> {
                newBoard = b;
                isNotUpdated = false;
            });
            Thread waitForBoardThread = new Thread(() -> {
                long startTime = System.currentTimeMillis(); //set Timeout
                while (isNotUpdated && (System.currentTimeMillis()-startTime)<5000L) {
                }
                isNotUpdated = true;
                if ((System.currentTimeMillis()-startTime)>5000L) {
                    Platform.runLater(() -> {
                        var alert = new Alert(Alert.AlertType.ERROR);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setContentText("time out while adding Board");
                        alert.showAndWait();
                    });
                }
                else {
                    Platform.runLater(() -> {
                        title.clear();
                        mainCtrl.closeAddBoardSuccess(newBoard);
                        mainCtrl.showBoard(newBoard);
                    });
                }
                tryingToAdd = false;
            });

            waitForBoardThread.start();

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

