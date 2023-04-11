package client.scenes;

import client.utils.FrontEndUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class BoardListCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private boolean isAdmin = false;

    private ObservableList<Board> listOfBoards;
    private Map<String ,ArrayList<Board>> joinedBoards = new HashMap<>();

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
    private MenuItem adminLogIn;
    @FXML
    private TextField boardSearch;

    private final ContextMenu contextMenu;

    @Inject
    public BoardListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(event -> {
            Board board = boardTable.getSelectionModel().getSelectedItem();
            server.send("/app/boards/delete", board);
        });
        contextMenu.getItems().add(delete);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBoardName.setCellValueFactory(b -> new SimpleStringProperty(b.getValue().getName()));
        colBoardId.setCellValueFactory(b -> new SimpleStringProperty(
                String.valueOf(b.getValue().getId())));
        boardTable.setRowFactory(r -> {
            TableRow<Board> row = new TableRow<>();
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }

    public void setWebSocketSessions() {
        server.registerForUpdates("/topic/boards/update", Board.class, b -> {
            Platform.runLater(this::loadBoards);
        });

        server.registerForUpdates("/topic/boards/delete", Board.class, b -> {
            joinedBoards.remove(b);
            Platform.runLater(this::loadBoards);
        });
    }

    //small start for when we implement multiBoards
    public void refresh() {
        try {
            var boards = server.getBoards();
        }
        catch (Exception e) {
        }
    }

    public void loadBoards() {
        if (!joinedBoards.containsKey(server.getHttpUrl())) {
            joinedBoards.put(server.getHttpUrl(), new ArrayList<>());
        }
        ArrayList<Board> joinedOnServer = joinedBoards.get(server.getHttpUrl());
        boardTable.getItems().clear();
        if (isAdmin) {
            List<Board> boards = server.getBoards();
            for (Board board : boards) {
                boardTable.getItems().add(board);
            }
        }
        else {
            for (Board board : joinedOnServer) {
                boardTable.getItems().add(board);
            }
        }
    }

    // since we first do the single-board approach this will do for now
    public void open() {
        try {
            int i = Integer.parseInt(boardSearch.getText());
            Board board = server.getBoardById(i);
            mainCtrl.showBoard(board);
            boardSearch.clear();
            if (!joinedBoards.containsKey(server.getHttpUrl())) {
                joinedBoards.put(server.getHttpUrl(), new ArrayList<>());
            }
            ArrayList<Board> joinedOnServer = joinedBoards.get(server.getHttpUrl());
            if (!joinedOnServer.contains(board)) {
                joinedOnServer.add(board);
                joinedBoards.put(server.getHttpUrl(), joinedOnServer);
            }
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
        if (!isAdmin) {
            mainCtrl.showAdminPassword();
        }
        else {
            setAdmin(false);
        }
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        changeMenu();
        loadBoards();
    }

    public void changeMenu() {
        if (isAdmin) {
            adminLogIn.setText("Log Out");
        }
        else {
            adminLogIn.setText("Log In");
        }
    }

    public void addToJoinedBoards(Board board) {
        if (!joinedBoards.containsKey(server.getHttpUrl())) {
            joinedBoards.put(server.getHttpUrl(), new ArrayList<>());
        }
        ArrayList<Board> joinedOnServer = joinedBoards.get(server.getHttpUrl());
        joinedOnServer.add(board);
        joinedBoards.put(server.getHttpUrl(), joinedOnServer);
    }
}
