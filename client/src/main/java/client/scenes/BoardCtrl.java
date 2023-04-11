package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.FrontEndUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Board;
import commons.Card;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import static com.google.inject.Guice.createInjector;

public class BoardCtrl{

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private ObservableList<commons.List> data;

    @FXML
    private Button backButton;

    @FXML
    private HBox listsHBox;

    @FXML
    private Text boardName;

    @FXML
    private Text boardId;

    private Board board;

    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void register() {
        server.registerForUpdatesPolling((Card c) -> {
            Platform.runLater(() -> {
                loadLists();
            });
        });
    }

    public void setBoard(Board board) {
        this.board = board;
        boardName.setText(board.getName());
        boardId.setText("#"+board.getId());
    }

    public void setWebsocketSessions() {
        server.registerForUpdatesSockets("/topic/boards/delete", Board.class , b-> {
            if (board.getId() == b.getId()) {
                Platform.runLater(() -> {
                    FrontEndUtils.errorPopUp("board deleted", "");
                    mainCtrl.showBoardList();
                });
            }
        });

        server.registerForUpdatesSockets("/topic/list/update", Card.class , l -> {
            Platform.runLater(() -> {
                loadLists();
            });
        });

        server.registerForUpdatesSockets("/topic/list/replace", commons.List.class, l -> {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == l.getId()) {
                    int j = i;
                    Platform.runLater(() -> {
                        Injector injector = createInjector(new MyModule());
                        MyFXML fxml = new MyFXML(injector);
                        listsHBox.getChildren().set(j, createList(l, fxml).getValue());
                    });
                    break;
                }

            }
        });
    }
    public HBox getListsHBox() {
        return listsHBox;
    }

    public Pair<ListCtrl, Parent> createList(commons.List list, MyFXML fxml) {
        var loadedPair = fxml.load(ListCtrl.class, "client", "scenes", "List.fxml");
        loadedPair.getKey().setMainCtrl(mainCtrl);
        loadedPair.getKey().setCardList(list);
        loadedPair.getKey().showName();
        loadedPair.getKey().loadCards();
        loadedPair.getKey().getFrame().prefHeightProperty().bind(listsHBox.heightProperty());
        return loadedPair;
    }

    public void loadLists() {
        board = server.getBoardById(board.getId());
        var lists = board.getTaskLists();
        data = FXCollections.observableList(lists);
        var listsHBoxChildren = listsHBox.getChildren();
        listsHBoxChildren.remove(0, listsHBoxChildren.size() - 1);

        Injector injector = createInjector(new MyModule());
        MyFXML fxml = new MyFXML(injector);

        for (var list : lists) {
            listsHBox.getChildren().add( listsHBox.getChildren().size() - 1,
                    createList(list, fxml).getValue());
        }
    }

    public void addListButton(){
        mainCtrl.showAddList(board);
    }

    public void back() {
        mainCtrl.showBoardList();
    }

    public void delete() {
        ButtonType answer = FrontEndUtils.confirmation();
        if (answer == ButtonType.OK) {
            server.send("/app/boards/delete", board);
            mainCtrl.showBoardList();
        }
    }

    public void stop(){
        server.stop();
    }
}
