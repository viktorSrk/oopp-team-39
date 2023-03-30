package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AddCardCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private TextField taskTextField;

    @FXML
    private ListView<String> tasksListView;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    private final ObservableList<String> tasks = FXCollections.observableArrayList();

    private commons.List list;


    @Inject
    public AddCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void setList(List list) {
        this.list = list;
    }

    public void back() {
        clearFields();
        mainCtrl.closeAddCard();
    }

    private void clearFields() {
        descriptionTextField.clear();
        titleTextField.clear();
    }

    @FXML
    public void addTask() {
        System.out.println("Button Add Task has been clicked!");
        String task = taskTextField.getText();
        if(!task.isEmpty()) {
            tasks.add(task);
            tasksListView.setItems(FXCollections.observableList(tasks));
            taskTextField.clear();
        }
    }

    @FXML
    public void add() {
        System.out.println("Button Add has been clicked!");
        String name = titleTextField.getText();
        Card card = new Card(name, list);
        this.server.send("/app/cards/add/" + list.getId(), card);
        mainCtrl.closeAddCard();
    }
}

