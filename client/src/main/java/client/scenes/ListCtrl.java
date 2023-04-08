package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Board;
import commons.Card;
import commons.List;
import commons.MoveCardMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import static com.google.inject.Guice.createInjector;

public class ListCtrl {

    private final ServerUtils server;

    private MainCtrl mainCtrl;

    private static DataFormat cardDataFormat = CardCtrl.getCardDataFormat();

    @FXML
    private TextField titleTextField;

    @FXML
    private Button addCardButton;

    @FXML
    private Button deleteCardButton;

    @FXML
    private VBox cardsVBox;

    @FXML
    private AnchorPane frame;

    private commons.List cardList;

    public AnchorPane getFrame() {
        return frame;
    }

    @Inject
    public ListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void setMainCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void setCardList(List cardList) {
        this.cardList = cardList;
    }

    public void showName() {
        titleTextField.setText(cardList.getTitle());
    }

    public void loadCards() {
        var cards = cardList.getCards();
        var cardsVBoxChildren = cardsVBox.getChildren();
        cardsVBoxChildren.remove(0, cardsVBoxChildren.size());

        Injector injector = createInjector(new MyModule());
        MyFXML fxml = new MyFXML(injector);

        for (var card : cards) {
            var loadedPair = fxml.load(CardCtrl.class, "client", "scenes", "Card.fxml");
            loadedPair.getKey().setCard(card);
            loadedPair.getKey().register();
            loadedPair.getKey().showName(card);
            cardsVBoxChildren.add(loadedPair.getValue());
        }
    }

    public void setOnDragOver(DragEvent event) {
        System.out.println("drag over");
        if (event.getGestureSource() != cardsVBox.getChildren()
                && event.getDragboard().hasContent(cardDataFormat)) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void setOnDragEntered(DragEvent event) {
        System.out.println("drag entered");
        if (event.getGestureSource() != cardsVBox.getChildren() &&
                event.getDragboard().hasContent(cardDataFormat)) {
            cardsVBox.setStyle("-fx-border-color: #33c5ff");
        }

        event.consume();
    }

    public void setOnDragExited(DragEvent event) {
        System.out.println("drag exited");
        cardsVBox.setStyle("-fx-border-color: transparent");
        event.consume();
    }

    public void setOnDragDropped(DragEvent event) {
        System.out.println("drag dropped: " + event.getSceneY() + ", " + event.getSceneX());
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasContent(cardDataFormat)) {
            //113.0 is the size of the card's anchorpane, supposedly
            int index = (int) (((event.getSceneY() - 125.0)/110.0));
            var board = (Board) server.getBoards().stream()
                    .filter(x -> x.getId() == 1)
                    .toArray()[0];
                    //hard-coded board instance.
            long id = (long) db.getContent(cardDataFormat);
            Card c = board.findCardInListById(id);
            commons.List list2 = board.findListWithCard(c);
            var listIdSource = list2.getId();
            var listIdTarget = this.getCardList().getId();
            MoveCardMessage message = new MoveCardMessage(listIdSource, listIdTarget, index, c);

            server.send("/app/cards/move", message);

            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private commons.List getCardList() {
        return cardList;
    }

    public void delete() {
        server.send("/app/list/delete", cardList);
    }

    public void changeTitle(){
        var text = titleTextField.getText();
        cardList.setTitle(text);
        server.send("/app/list/replace",cardList);
    }
    public void addCard() {
        mainCtrl.showAddCard(cardList);
    }
}
