package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CardCtrl {


    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private Card card;

    @FXML
    private TextField titleTextField;

    @FXML
    private Button openButton;

    @FXML
    private Button deleteButton;

    @Inject
    public CardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
    public void setCard(Card card) {
        this.card = card;
    }
//TODO: I need to initialise the title with onw form the database (probably in the contstructor)

    public void open(){
        //open the related card
    }

    public void delete() {
        server.send("/app/card/delete", card);
    }

    public void showName(Card card) {
        titleTextField.setText(card.getTitle());
    }
}
