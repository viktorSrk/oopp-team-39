package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
                appendList(l);
            });
        });
        server.registerForUpdates("/topic/list/delete", commons.List.class, l -> {
            int i = data.indexOf(l);
            data.remove(l);
            Platform.runLater(() -> {
                listsHBox.getChildren().remove(i);
            });
        });
    }
    public HBox getListsHBox() {
        return listsHBox;
    }

    public void appendList(commons.List list) {
        Text title = new Text(list.getTitle());
        Button delete = new Button("X");
        delete.setOnAction(event -> {
            Button source = (Button) event.getSource();
            server.send("/app/list/delete", source.getParent().getUserData());
        });

        Pane pane = new Pane(title, delete);
        pane.setUserData(list);
        pane.setPrefSize(200.0, 400.0);
        pane.setMinSize(200.0, 400.0);
        pane.setStyle("-fx-background-color: gray;");


        Button button = new Button();
        button.setMnemonicParsing(false);
        button.setText("Add Card");

        VBox vbox = new VBox(pane, button);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.minWidth(200.0);
        vbox.prefHeight(200.0);
        vbox.setSpacing(30.0);
        listsHBox.getChildren().add(listsHBox.getChildren().size() - 1, vbox);
    }

    public void refresh() {
        var lists = server.getLists();
        data = FXCollections.observableList(lists);
        var listsHBoxChildren = listsHBox.getChildren();
        listsHBoxChildren.remove(0, listsHBoxChildren.size() - 1);

        for (var list : data) {
            appendList(list);
        }
    }

    public void addListButton(){
        mainCtrl.showAddList();
    }

    public void back(){
        mainCtrl.showBoardList();
    }
}
