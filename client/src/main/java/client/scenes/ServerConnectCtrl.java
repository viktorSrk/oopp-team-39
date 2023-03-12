package client.scenes;

import client.utils.FrontEndUtils;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.rmi.ConnectException;
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
            ServerUtils.setSERVER(serverip.getText());
            ServerUtils.testURL();//test if given URL is actually working
            mainCtrl.showOverview();
        }
        catch (Exception e) {
            FrontEndUtils.ErrorPopUp("Couldn't connect to the URL:", e.getMessage());
        }
    }
}
