package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;


import java.awt.*;

public class CardCtrl {

    private ServerUtils serverCard;

    private MainCtrl mainCtrlCard;

    @FXML
    private TextField cardName;

    @FXML
    private TextArea cardDescription;


    @Inject
    public CardCtrl(ServerUtils serverCard, MainCtrl mainCtrlCard) {
        this.serverCard = serverCard;
        this.mainCtrlCard = mainCtrlCard;
    }

    public void back() {
        clearFields();
        mainCtrlCard.showOverview();
        //TODO: implement lists (and method showLists()) and switch from showOverview() to showLists()
    }

    private void clearFields() {
        cardDescription.clear();
        cardName.clear();
    }
//TODO: implements viewlists
}

