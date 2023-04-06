package client.scenes;

import client.utils.FrontEndUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminPasswordCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField password;

    @Inject
    public AdminPasswordCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void cancel() {
        password.clear();
        mainCtrl.closeAdminPassword();
    }

    public void login() {
        // TODO: Better way to store/generate password
        if (!password.getText().equals("test")) {
            // TODO
        }
        else {
            FrontEndUtils.errorPopUp("Wrong password", "The password you filled in was not correct!");
        }

        password.clear();
        mainCtrl.closeAdminPassword();
    }
}

