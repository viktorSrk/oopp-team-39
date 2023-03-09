package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
