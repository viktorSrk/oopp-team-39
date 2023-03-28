package client.scenes;

import client.utils.FrontEndUtils;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

public class ServerConnectCtrl implements Initializable{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField serverip;
    @FXML
    private Button connect;

    @Inject
    public ServerConnectCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*TO DO*/
    }

    public void connect() {
        try {
            ServerUtils.setServer(serverip.getText());
            ServerUtils.testURL();//test if given URL is actually working
            mainCtrl.setWebsocketSessions();
            mainCtrl.showBoardList();
        }
        catch (Exception e) {
            FrontEndUtils.errorPopUp("Couldn't connect to the URL:", e.getMessage());
        }
    }
}
