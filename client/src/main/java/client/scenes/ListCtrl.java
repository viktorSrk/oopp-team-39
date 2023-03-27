package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ListCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField titleTextField;

    @FXML
    private Button addCardButton;

    @FXML
    private Button deleteCardButton;

    @FXML
    private VBox cardsVBox;


    @Inject
    public ListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void showName(commons.List list) {
        titleTextField.setText(list.getTitle());
    }
}
