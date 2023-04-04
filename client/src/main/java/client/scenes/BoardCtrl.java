package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Card;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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
                listsHBox.getChildren().add( listsHBox.getChildren().size() - 1,
                        createList(l, fxml).getValue());
            });
        });
        
        server.registerForUpdates("/topic/list/delete", commons.List.class, l -> {
            int i = data.indexOf(l);
            data.remove(l);
            Platform.runLater(() -> {
                listsHBox.getChildren().remove(i);
            });
        });

        server.registerForUpdates("/topic/list/update", Card.class , l -> {
            Platform.runLater(() -> {
                loadLists();
            });
        });

        server.registerForUpdates("/topic/list/replace", commons.List.class, l -> {
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
        return loadedPair;
    }

    public void loadLists() {
        var lists = server.getLists();
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
        mainCtrl.showAddList();
    }

    public void back() {
        mainCtrl.showBoardList();
    }

}
