package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BoardTest {

    @Test
    public void getIdTest() {
        Board b1 = new Board();
        assertEquals(0, b1.getId());
    }

    @Test
    public void setIdTest() {
        Board b1 = new Board();
        b1.setId(1862);
        assertEquals(1862, b1.getId());
    }

    @Test
    public void equalsTest() {
        Board b1 = new Board();
        Board b2 = new Board();
        List l1 = new List();
        b1.getTaskLists().add(l1);
        b2.getTaskLists().add(l1);
        assertEquals(b1, b2);
    }

    @Test
    public void notEqualsTest() {
        Board b1 = new Board();
        Board b2 = new Board();
        List l1 = new List();
        b1.getTaskLists().add(l1);
        assertNotEquals(b1, b2);
    }

    @Test
    public void hashEqualsTest() {
        Board b1 = new Board();
        Board b2 = new Board();
        List l1 = new List();
        b1.getTaskLists().add(l1);
        b2.getTaskLists().add(l1);
        assertEquals(b1.hashCode(),b2.hashCode());
    }

    @Test
    public void hashNotEqualsTest() {
        Board b1 = new Board();
        Board b2 = new Board();
        List l1 = new List();
        b1.getTaskLists().add(l1);
        assertNotEquals(b1.hashCode(),b2.hashCode());
    }

    @Test
    public void testToString() {
        Board b1 = new Board();
        List l1 = new List();
        b1.getTaskLists().add(l1);
        assertEquals("commons.Board@"+ Integer.toHexString(System.identityHashCode(b1)) +"[Id=0,TaskLists=[" + l1 +"]]",
                b1.toString());
    }

    @Test
    void getListById() {
        Board b1 = new Board();
        List l1 = new List();
        List l2 = new List();
        l2.setId((long) 5);
        l1.setId((long) 3);
        b1.getTaskLists().add(l1);
        b1.getTaskLists().add(l2);
        assertEquals(b1.getListById(5), l2);

    }

    @Test
    void findCardInListById() {
        Board b1 = new Board();
        List l1 = new List();
        List l2 = new List();
        Card card1 = new Card("0");
        card1.setId(0);
        l2.setId((long) 5);
        l1.setId((long) 3);
        b1.getTaskLists().add(l1);
        b1.getTaskLists().add(l2);
        l1.addCard(card1);
        assertEquals(b1.findCardInListById(0), card1);
    }

    @Test
    void moveCard() {
        Board b1 = new Board();
        List l1 = new List();
        List l2 = new List();
        Card card1 = new Card("0", l1, 0);
        card1.setId(0);
        l2.setId((long) 5);
        l1.setId((long) 3);
        b1.getTaskLists().add(l1);
        b1.getTaskLists().add(l2);
        l1.addCard(card1);
        b1.moveCard(0, 5, 0);
        List l3 = new List();
        l3.setId((long)3);
        assertEquals(l1, l3);

    }
}
