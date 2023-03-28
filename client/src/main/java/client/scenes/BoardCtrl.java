package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import static com.google.inject.Guice.createInjector;

public class BoardCtrl{

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private ObservableList<commons.List> data;

    @FXML
    private Button backButton;

    @FXML
    private HBox listsHBox;

    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void setWebsocketSessions() {
        server.registerForUpdates("/topic/list/add", commons.List.class, l -> {
            data.add(l);
            Platform.runLater(() -> {
                Injector injector = createInjector(new MyModule());
                MyFXML fxml = new MyFXML(injector);
                appendList(l, fxml);
            });
        });
        server.registerForUpdates("/topic/list/delete", commons.List.class, l -> {
            int i = data.indexOf(l);
            data.remove(l);
            Platform.runLater(() -> {
                listsHBox.getChildren().remove(i);
            });
        });
        server.registerForUpdates("/topic/list/update", Long.class , l -> {
            Platform.runLater(() -> {
                loadLists();
            });
        });
    }
    public HBox getListsHBox() {
        return listsHBox;
    }

    public void appendList(commons.List list, MyFXML fxml) {
        var loadedPair = fxml.load(ListCtrl.class, "client", "scenes", "List.fxml");
        loadedPair.getKey().setMainCtrl(mainCtrl);
        loadedPair.getKey().setCardList(list);
        loadedPair.getKey().showName();
        loadedPair.getKey().loadCards();
        listsHBox.getChildren().add( listsHBox.getChildren().size() - 1, loadedPair.getValue());
    }

    public void loadLists() {
        var lists = server.getLists();
        data = FXCollections.observableList(lists);
        var listsHBoxChildren = listsHBox.getChildren();
        listsHBoxChildren.remove(0, listsHBoxChildren.size() - 1);

        Injector injector = createInjector(new MyModule());
        MyFXML fxml = new MyFXML(injector);

        for (var list : lists) {
            appendList(list, fxml);
        }
    }

    public void addListButton(){
        mainCtrl.showAddList();
    }

    public void back() {
        mainCtrl.showBoardList();
    }

}
