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
}
