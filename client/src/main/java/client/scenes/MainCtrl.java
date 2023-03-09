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
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;

    private ServerConnectCtrl serverConnectCtrl;
    private Scene serverConnect;

    private BoardListCtrl boardListCtrl;
    private Scene boardList;

    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                           Pair<AddQuoteCtrl, Parent> add,
                           Pair<ServerConnectCtrl, Parent> serverConnect,
                           Pair<BoardListCtrl, Parent> boardList) {
        this.primaryStage = primaryStage;
        this.serverConnectCtrl = serverConnect.getKey();
        this.serverConnect = new Scene(serverConnect.getValue());

        this.boardListCtrl = boardList.getKey();
        this.boardList = new Scene(boardList.getValue());

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());
//
//        showOverview();

        showServerConnect();
        primaryStage.show();
    }

    public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    public void showAdd() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    public void showServerConnect() {
        primaryStage.setTitle("Talio: Connect to a Server");
        primaryStage.setScene(serverConnect);
    }

    public void showBoardList() {
        primaryStage.setTitle("Talio: Boards");
        primaryStage.setScene(boardList);
    }
}