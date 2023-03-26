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


public class EditCardCtrl {

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

    private Card selectedCard; //TODO: parse the card that is clicked to selectedCard


    @Inject
    public EditCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void refresh() {
        titleTextField.setText(selectedCard.getTitle());
//        descriptionTextField.setText(selectedCard.getDescription()); //TODO: implement description for cards, uncomment once implemented
//        for (String e : selectedCard.getTasks()) { // TODO: implement tasks for cards, uncomment once implemented
//            tasks.add(e);
//        }
//        tasksListView.setItems(FXCollections.observableList(tasks));
    }

    public void back() {
        clearFields();
        mainCtrl.showBoard();
    }

    private void clearFields() {
        descriptionTextField.clear();
        titleTextField.clear();
    }

    @FXML
    public void addTask() {
        String task = taskTextField.getText();
        if(!task.isEmpty()) {
            tasks.add(task);
            tasksListView.setItems(FXCollections.observableList(tasks));
            taskTextField.clear();
        }
    }

    @FXML
    public void edit() {
        Card editedCard = selectedCard;
        editedCard.setTitle(titleTextField.getText());
//        editedCard.setDescription(descriptionTextField.getText()); // TODO: uncomment once description is implemented
//        editedCard.setTasks(tasks); // TODO: uncomment once tasks are implemented
        this.server.replaceCard(editedCard, selectedCard.getId());
    }
}

