package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

public class CardCtrl {


    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private Card getCard() {
        return card;
    }

    private Card card;

    public static DataFormat getCardDataFormat() {
        return cardDataFormat;
    }

    private static final DataFormat cardDataFormat = new DataFormat("Card");

    @FXML
    private TextField titleTextField;

    @FXML
    private Button openButton;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane anchorPane;

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

    public void setOnDragDetected(MouseEvent event) {
        System.out.println("drag detected");
        Dragboard db = anchorPane.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.put(cardDataFormat, getCard().getId());
        db.setContent(content);
        anchorPane.setStyle("-fx-border-color: #ff6969");

        event.consume();
    }

    public void setOnDragDone(DragEvent event) {
        System.out.println("drag done");
        if (event.getTransferMode() == TransferMode.MOVE) {
//          String cardData = (String) event.getDragboard().getContent(getCardDataFormat());
//          var arr = cardData.split(" ");
//          int oldIndex = Integer.parseInt(arr[1]);
//            int oldIndex = this.cardList.getCardIndex(card.getId());
//            int newIndex = (int) ((event.getY()/193.0) - 1);
//            if (newIndex < 0) newIndex = 0;
//            if (newIndex >= cardList.getCards().size()) {
//                newIndex = card.getList().getCards().size() - 1;
//            }
//            cardList.move(card.getId(), newIndex);
//            mainCtrl.moveCard(card, oldIndex, newIndex);


//            mainCtrl.refresh();

        }
        anchorPane.setStyle("-fx-border-color: transparent");
        anchorPane.setStyle("-fx-border-style: solid");
        event.consume();

    }

    public void delete() {
        server.send("/app/card/delete", card);
    }

    public void showName(Card card) {
        titleTextField.setText(card.getTitle());
    }

    public void changeTitle(){
        var text = titleTextField.getText();
        card.setTitle(text);
        server.replaceCard(card, card.getId());
    }
}
