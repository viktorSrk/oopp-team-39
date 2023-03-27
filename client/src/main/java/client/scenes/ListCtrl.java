package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import static com.google.inject.Guice.createInjector;

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

    public void loadCards() {
        var cards = server.getCards();
        var cardsVBoxChildren = cardsVBox.getChildren();
        cardsVBoxChildren.remove(0, cardsVBoxChildren.size());

        for (var card : cards) {
            Injector injector = createInjector(new MyModule());
            MyFXML fxml = new MyFXML(injector);

            var loadedPair = fxml.load(CardCtrl.class, "client", "scenes", "Card.fxml");
            loadedPair.getKey().showName(card);
            cardsVBoxChildren.add(loadedPair.getValue());
        }
    }
}
