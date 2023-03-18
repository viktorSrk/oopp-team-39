package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

//    @Test
//    void getId() {
//        List test = new List();
//        assertEquals(0, test.getId());
//        List test2 = new List();
//        assertEquals(1, test2.getId());
//    }

    @Test
    void getTitle() {
        List test = new List();
        List test2 = new List("abc");
        assertEquals("New List", test.getTitle());
        assertEquals("abc", test2.getTitle());
    }

    @Test
    void setTitle() {
        List test = new List();
        test.setTitle("def");
        assertEquals("def", test.getTitle());
    }

    @Test
    void cards() {
        ArrayList<Card> cards = new ArrayList<>();
        List test = new List();
        test.setCards(cards);
        assertEquals(cards, test.getCards());
        assertEquals(0, test.getNumberOfCards());
    }

    @Test
    void getNumber_of_cards() {
        List test = new List();
        assertEquals(0, test.getNumberOfCards());
        test.addCard(new Card(""));
        assertEquals(1, test.getNumberOfCards());
    }

    @Test
    void addCard() {
        List test = new List();
        Card card = new Card("0");
        test.addCard(card);
        assertEquals(1, test.getNumberOfCards());
        assertEquals(card, test.getCards().get(0));
    }

    @Test
    void testAddCard() {
        List test = new List();
        test.addCard(new Card("0"));
        test.addCard(new Card("2"));
        Card card = new Card("1");
        test.addCard(card, 1);
        assertEquals(card, test.getCards().get(1));
        assertEquals(3, test.getNumberOfCards());
    }

    @Test
    void removeCard() {
        List test = new List();
        Card card = new Card("");
        test.addCard(card);
        test.removeCard(card);
        assertEquals(0, test.getNumberOfCards());
        assertEquals(0, test.getCards().size());
    }

    @Test
    void testRemoveCard() {
        List test = new List();
        Card card = new Card("card");
        test.addCard(new Card(""));
        test.addCard(new Card(""));
        test.addCard(card);
        test.addCard(new Card(""));
        assertEquals(card, test.removeCard(2));
        assertEquals(3, test.getNumberOfCards());
    }
}