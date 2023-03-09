package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BoardListCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Board> listOfBoards;

    @FXML
    private TableView<Board> boardTable;
    @FXML
    private TableColumn<Board, String> colBoard;

    @FXML
    private MenuBar menu;
    @FXML
    private MenuItem add;
    @FXML
    private MenuItem del;
    @FXML
    private MenuItem backToServerConnect;
    @FXML
    private MenuItem refresh;
    @FXML
    private MenuItem info;

    @Inject
    public BoardListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    public void refresh() {
        //TO DO
    }

    public void cancel() {
        //TO DO
    }

    public void addBoard() {
        //TO DO
    }

    public void deleteBoard() {
        //TO DO
    }

    public void showInfo() {
        //TO DO
    }
}
