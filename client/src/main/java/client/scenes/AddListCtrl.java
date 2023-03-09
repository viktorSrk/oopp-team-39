package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class AddListCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @Inject
    public AddListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
}
