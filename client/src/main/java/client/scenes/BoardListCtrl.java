package client.scenes;

import client.utils.FrontEndUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BoardListCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private boolean isAdmin = false;

    private ObservableList<Board> listOfBoards;

    @FXML
    private TableView<Board> boardTable;
    @FXML
    private TableColumn<Board, String> colBoardName;
    @FXML
    private TableColumn<Board, String> colBoardId;
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
    @FXML
    private TextField boardSearch;

    @Inject
    public BoardListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBoardName.setCellValueFactory(b -> new SimpleStringProperty(b.getValue().getName()));
        colBoardId.setCellValueFactory(b -> new SimpleStringProperty(
                String.valueOf(b.getValue().getId())));
    }

    //small start for when we implement multiBoards
    public void refresh() {
        if (isAdmin) {
            List<Board> boards = server.getBoards();
            for (Board board : boards) {
                boardTable.getItems().add(board);
            }

            return;
        }

        try {
            var boards = server.getBoards();
        }
        catch (Exception e) {
        }
    }

    // since we first do the single-board approach this will do for now
    public void open() {
        try {
            int i = Integer.parseInt(boardSearch.getText());
            Board board = server.getBoardById(i);
            mainCtrl.showBoard(board);
        }
        catch (Exception e) {
            FrontEndUtils.errorPopUp("not found", e.getMessage());
        }
    }

    //goes back to the Server Connect menu
    public void cancel() {
        mainCtrl.showServerConnect();
        //TO DO
    }

    public void addBoard() {
        mainCtrl.showAddBoard();
        //TO DO
    }

    public void deleteBoard() {
        //TO DO
    }

    public void showInfo() {
        //TO DO
    }

    public void adminButton() {
        mainCtrl.showAdminPassword();
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
