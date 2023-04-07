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

import commons.Board;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private Stage secondStage;

    private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;

    private ServerConnectCtrl serverConnectCtrl;
    private Scene serverConnect;

    private BoardListCtrl boardListCtrl;
    private Scene boardList;

    private AddBoardCtrl addBoardCtrl;

    private Scene addBoard;

    private BoardCtrl boardCtrl;
    private Scene board;

    private AddCardCtrl addCardCtrl;
    private Scene addCard;

    private EditCardCtrl editCardCtrl;
    private Scene editCard;

    private CardCtrl cardCtrl;
    private Scene card;

    private AddListCtrl addListCtrl;
    private Scene addList;

    private ListCtrl listCtrl;
    private Scene list;

    private AdminPasswordCtrl adminPasswordCtrl;
    private Scene adminPassword;

    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                           Pair<AddQuoteCtrl, Parent> add,
                           Pair<ServerConnectCtrl, Parent> serverConnect,
                           Pair<BoardListCtrl, Parent> boardList,
                           Pair<AddBoardCtrl, Parent> addBoard,
                           Pair<BoardCtrl, Parent> board,
                           Pair<AddCardCtrl, Parent> addCard,
                           Pair<EditCardCtrl, Parent> editCard,
                           Pair<AddListCtrl, Parent> addList,
                           Pair<CardCtrl, Parent> card,
                           Pair<ListCtrl, Parent> list,
                           Pair<AdminPasswordCtrl, Parent> adminPassword
    ) {
        this.primaryStage = primaryStage;
        this.serverConnectCtrl = serverConnect.getKey();
        this.serverConnect = new Scene(serverConnect.getValue());

        this.boardListCtrl = boardList.getKey();
        this.boardList = new Scene(boardList.getValue());

        this.addBoardCtrl = addBoard.getKey();
        this.addBoard = new Scene(addBoard.getValue());

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.boardCtrl = board.getKey();
        this.board = new Scene(board.getValue());

        this.addCardCtrl = addCard.getKey();
        this.addCard = new Scene(addCard.getValue());

        this.editCardCtrl = editCard.getKey();
        this.editCard = new Scene(editCard.getValue());

        this.cardCtrl = card.getKey();
        this.card = new Scene(card.getValue());

        this.addListCtrl = addList.getKey();
        this.addList = new Scene(addList.getValue());

        this.listCtrl = list.getKey();
        this.list = new Scene(list.getValue());

        this.adminPasswordCtrl = adminPassword.getKey();
        this.adminPassword = new Scene(adminPassword.getValue());

        this.secondStage = new Stage();

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
        boardListCtrl.loadBoards();
    }

    public void showAddBoard() {
        secondStage.setTitle("Talio: Add a Board");
        secondStage.setScene(addBoard);
        secondStage.show();
    }

    public void closeAddBoard() {
        secondStage.close();
    }

    public void showAdminPassword() {
        secondStage.setTitle("Talio: Admin Password");
        secondStage.setScene(adminPassword);
        secondStage.show();
    }

    public void closeAdminPassword() {
        secondStage.close();
    }

    public void showBoard(Board boardObject) {
        primaryStage.setTitle("Talio: Board");
        primaryStage.setScene(board);
        boardCtrl.setBoard(boardObject);
        boardCtrl.loadLists();
    }

    public void showAddCard(commons.List list) {
        secondStage.setTitle("Talio: addCard");
        secondStage.setScene(addCard);
        addCardCtrl.setList(list);
        secondStage.show();
    }
    public void closeAddCard() {
        secondStage.close();
    }

    public void showCard() {
        primaryStage.setTitle("Talio: Card");
        primaryStage.setScene(card);
    }

    public void showEditCard() {
        secondStage.setTitle("Talio: Edit Card");
        secondStage.setScene(editCard);
        editCardCtrl.refresh();
        secondStage.show();
    }

    public void closeEditCard() {
        secondStage.close();
    }

    public void showAddList(Board board) {
        secondStage.setTitle("Talio: AddList");
        secondStage.setScene(addList);
        secondStage.show();
        addListCtrl.setBoard(board);
    }

    public void closeAddList(){
        secondStage.close();
    }

    public void showList() {
        primaryStage.setTitle("Talio: List");
        primaryStage.setScene(list);
    }

    public void setWebsocketSessions() {
        boardListCtrl.setWebSocketSessions();
        boardCtrl.setWebsocketSessions();
    }

    public void setAdmin(boolean isAdmin) {
        boardListCtrl.setAdmin(isAdmin);
    }
}