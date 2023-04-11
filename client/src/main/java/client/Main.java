/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import client.scenes.*;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application { //

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        var board = FXML.load(BoardCtrl.class, "client", "scenes", "Board.fxml");

        var serverConnect = FXML.load(ServerConnectCtrl.class,
                "client", "scenes", "ServerConnect.fxml");
        var boardList = FXML.load(BoardListCtrl.class, "client", "scenes", "BoardList.fxml");
        var addBoard = FXML.load(AddBoardCtrl.class, "client", "scenes", "AddBoard.fxml");
        var addCard = FXML.load(AddCardCtrl.class, "client", "scenes", "AddCard.fxml");
        var editCard = FXML.load(EditCardCtrl.class, "client", "scenes", "EditCard.fxml");
        var addList = FXML.load(AddListCtrl.class, "client", "scenes", "AddList.fxml");
        var card = FXML.load(CardCtrl.class, "client", "scenes", "Card.fxml");
        var list = FXML.load(ListCtrl.class, "client", "scenes", "List.fxml");
        var adminPassword = FXML.load(AdminPasswordCtrl.class,
                "client", "scenes", "AdminPassword.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        primaryStage.setOnCloseRequest(e -> mainCtrl.stop());
        mainCtrl.initialize(primaryStage, serverConnect,
                boardList, addBoard, board, addCard, editCard, addList, card, list, adminPassword);
    }
}