package commons;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class CardList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @OneToMany(cascade = CascadeType.PERSIST)
    private ArrayList<Card> cards;
    private int number_of_cards;

    private static int number_of_lists = 0;

    /**
     * Creates a new List. List for cards is initialized and number_of_cards set to 0
     * @param title the title of the new List
     */
    public CardList(String title) {
        this.id = (long) number_of_lists;
        number_of_lists++;
        this.title = title;
        this.cards = new ArrayList<>();
        this.number_of_cards = 0;

        number_of_lists++;
    }

    /**
     * No argument constructor of a List. Sets title to "New List".
     */
    public CardList() {
        this.id = (long) number_of_lists;
        number_of_lists++;
        this.title = "New List";
        this.cards = new ArrayList<>();
        this.number_of_cards = 0;
    }
}
