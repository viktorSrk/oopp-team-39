package client.scenes;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Board;

import jakarta.ws.rs.WebApplicationException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class BoardListCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Board> listOfBoards;

    @FXML
    private TableView<Board> table;

    @FXML
    private TextField boards;

    @Inject
    public BoardListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }
}
