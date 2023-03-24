package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardCtrl implements Initializable {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private ObservableList<commons.List> data;

    @FXML
    private HBox listsHBox;

    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.registerForUpdates("/topic/list", commons.List.class, l -> {
                data.add(l);
                Platform.runLater(() -> {
                    appendList(l);
                });
        });
    }
    public HBox getListsHBox() {
        return listsHBox;
    }

    public void appendList(commons.List list) {
        Pane pane = new Pane();
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
        listsHBox.getChildren().add(0, vbox);
    }

    public void refresh() {
        var lists = server.getLists();
        data = FXCollections.observableList(lists);
        var listsHBoxChildren = listsHBox.getChildren();
        listsHBoxChildren.remove(0, listsHBoxChildren.size() - 1);

        for (var list : data) {
            Pane pane = new Pane();
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

            listsHBoxChildren.add(0, vbox);
        }
    }

    public void addListButton(){
        mainCtrl.showAddList();
    }
}
