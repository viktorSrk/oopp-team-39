package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    @Test
    void setId() {
        List test = new List();
        test.setId((long)1862);
        assertEquals(1862, test.getId());
    }

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
        assertThrows(IndexOutOfBoundsException.class, () -> test.removeCard(3));
    }

    @Test
    void equalsTest() {
        List l1 = new List();
        List l2 = new List();
        Card card = new Card("");
        l1.addCard(card);
        l1.setTitle("list");
        l1.setId((long)1862);
        l2.addCard(card);
        l2.setTitle("list");
        l2.setId((long)1862);
        assertEquals(l1, l2);
    }

    @Test
    void notEqualsTest() {
        List l1 = new List();
        List l2 = new List();
        Card card = new Card("");
        l1.addCard(card);
        assertNotEquals(l1, l2);

        l2.addCard(card);
        l1.setTitle("list");
        assertNotEquals(l1, l2);

        l2.setTitle("list");
        l1.setId((long)1862);
        assertNotEquals(l1, l2);
    }

    @Test
    void testSwap() {
        List test = new List();
        List control = new List();
        Card card1 = new Card("1");
        card1.setId(0);
        test.addCard(card1);
        test.addCard(new Card("2"));
        test.addCard(new Card("3"));
        test.addCard(new Card("4"));
        test.move(0, 3);
        control.addCard(new Card("2"));
        control.addCard(new Card("3"));
        control.addCard(new Card("4"));
        control.addCard(card1);
        assertEquals(test, control);
    }
}