package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @OneToMany(cascade = CascadeType.PERSIST)
    private java.util.List<Card> cards;
    private int numberOfCards;

    /**
     * Creates a new List. List for cards is initialized and number_of_cards set to 0
     * @param title the title of the new List
     */
    public List(String title) {
        this.title = title;
        this.cards = new ArrayList<Card>();
        this.numberOfCards = 0;
    }

    /**
     * No argument constructor of a List. Sets title to "New List".
     */
    public List() {
        this.title = "New List";
        this.cards = new ArrayList<Card>();
        this.numberOfCards = 0;
    }

    /**
     * Sets the List ID
     * @param id the new id for this list
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the List
     * @return the ID of this list
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the Title of this List
     * @return the title of this list
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this list
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the ArrayList of cards of this List
     * @return the ArrayList of cards of this list
     */
    public java.util.List<Card> getCards() {
        return cards;
    }

    /**
     * Sets the ArrayList of Cards to a new List. number_of_cards gets updated too
     * @param cards the new ArrayList of cards
     */
    public void setCards(java.util.List<Card> cards) {
        this.cards = cards;
        this.numberOfCards = cards.size();
    }

    /**
     * Gets the amount of cards of this List
     * @return the amount of cards of this list
     */
    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     * Adds one card to the end of the list
     * @param card the card to be added
     */
    public void addCard(Card card) {
        this.cards.add(card);
        this.numberOfCards++;
    }

    /**
     * Adds one card to the list at a specific position
     * @param card the card that is to be added to the list
     * @param index the index where the card is to be inserted in
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
     */
    public void addCard(Card card, int index) throws IndexOutOfBoundsException {
        this.cards.add(index, card);
        this.numberOfCards++;
    }

    /**
     * Removes the specified card from the list. number_of_cards also gets decreased
     * @param card the card that is to be removed
     */
    public void removeCard(Card card) {
        this.cards.remove(card);
        this.numberOfCards--;
    }

    /**
     * Removes the card at the specified index
     * @param index the index from the card that is to be removed
     * @return the removed card
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
     */
    public Card removeCard(int index) throws IndexOutOfBoundsException {
        if (index >= this.cards.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            this.numberOfCards--;
            return this.cards.remove(index);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        List list = (List) o;

        return new EqualsBuilder().append(numberOfCards, list.numberOfCards)
                .append(id, list.id)
                .append(title, list.title)
                .append(cards, list.cards)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id).append(title)
                .append(cards)
                .append(numberOfCards)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, MULTI_LINE_STYLE)
                .append("id", id)
                .append("title", title)
                .append("cards", cards)
                .append("number_of_cards", numberOfCards)
                .toString();
    }
}
