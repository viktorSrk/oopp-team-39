package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import static com.google.inject.Guice.createInjector;

public class BoardCtrl {

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

    public HBox getListsHBox() {
        return listsHBox;
    }


    public void loadLists() {
        var lists = server.getLists();
        var listsHBoxChildren = listsHBox.getChildren();
        listsHBoxChildren.remove(0, listsHBoxChildren.size() - 1);

        for (var list : lists) {
            Injector injector = createInjector(new MyModule());
            MyFXML fxml = new MyFXML(injector);

            var loadedPair = fxml.load(ListCtrl.class, "client", "scenes", "List.fxml");
            loadedPair.getKey().showName(list);
            loadedPair.getKey().loadCards();
            listsHBoxChildren.add(listsHBoxChildren.size() - 1, loadedPair.getValue());
        }
    }

    public void addListButton(){
        mainCtrl.showAddList();
    }

    public void back(){
        mainCtrl.showBoardList();
    }
}
