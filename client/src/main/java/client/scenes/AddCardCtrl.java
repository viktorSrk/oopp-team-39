package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


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


    @Inject
    public AddCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void back() {
        clearFields();
        //mainCtrl.showBoard();
        mainCtrl.closeAddCard();
    }
    //TODO: change to other show (board or list)

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
        this.server.addCard(new Card(name));
        mainCtrl.closeAddCard();
    }
}

