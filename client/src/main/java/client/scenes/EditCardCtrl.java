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
        selectedCard = new Card("");
    }

    /**
     * Sets the values in the scene corresponding to the selected card, that is edited
     * @param selectedCard is the card that is to be shown in the pop-up
     */
    public void setCard(Card selectedCard) {
        if (selectedCard == null)
            throw new NullPointerException();
        this.selectedCard = selectedCard;
        titleTextField.setText(selectedCard.getTitle());
        descriptionTextField.setText(selectedCard.getDescription());
//        for (String e : selectedCard.getTasks()) {
//            tasks.add(e);
//        }
//        tasksListView.setItems(FXCollections.observableList(tasks));
//TODO: implement tasks for cards, uncomment once implemented
    }

    /**
     * goes back to board
     */
    public void back() {
        clearFields();
        mainCtrl.closeEditCard();
    }

    /**
     * clears all the fields in the scene
     */
    private void clearFields() {
        descriptionTextField.clear();
        titleTextField.clear();
    }

    /**
     * adds a task to the list of tasks in the card
     */
    @FXML
    public void addTask() {
        String task = taskTextField.getText();
        if(!task.isEmpty()) {
            tasks.add(task);
            tasksListView.setItems(FXCollections.observableList(tasks));
            taskTextField.clear();
        }
    }

    /**
     * confirms the changes that are made to the card
     */
    @FXML
    public void edit() {
        String name = titleTextField.getText();
        String description = descriptionTextField.getText();
        selectedCard.setTitle(name);
        selectedCard.setDescription(description);
//        selectedCard.setTasks(tasks); // TODO: uncomment once tasks are implemented
        // this.server.send("/app/cards/edit/" + selectedCard.getId(), selectedCard);
        this.server.replaceCard(selectedCard);
        clearFields();
        mainCtrl.closeAddCard();
    }
}

