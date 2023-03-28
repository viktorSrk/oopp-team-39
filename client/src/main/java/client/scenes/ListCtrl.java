package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import static com.google.inject.Guice.createInjector;

public class ListCtrl {

    private final ServerUtils server;

    private MainCtrl mainCtrl;

    @FXML
    private TextField titleTextField;

    @FXML
    private Button addCardButton;

    @FXML
    private Button deleteCardButton;

    @FXML
    private VBox cardsVBox;

    private commons.List cardList;


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
        var cards = server.getCards();
        var cardsVBoxChildren = cardsVBox.getChildren();
        cardsVBoxChildren.remove(0, cardsVBoxChildren.size());

        Injector injector = createInjector(new MyModule());
        MyFXML fxml = new MyFXML(injector);

        for (var card : cards) {
            var loadedPair = fxml.load(CardCtrl.class, "client", "scenes", "Card.fxml");
            loadedPair.getKey().showName(card);
            cardsVBoxChildren.add(loadedPair.getValue());
        }
    }

    public void delete() {
        server.send("/app/list/delete", cardList);
    }

    public void addCard() {
        mainCtrl.showAddCard();
    }
}
