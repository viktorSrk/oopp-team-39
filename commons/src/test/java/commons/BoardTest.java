package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class BoardTest {
    @Test
    public void equalsTest() {
        Board b1 = new Board();
        Board b2 = new Board();
        List l1 = new List();
        b1.getTaskLists().add(l1);
        b2.getTaskLists().add(l1);
        assertTrue(b1.equals(b2));
    }

    @Test
    public void notEqualsTest() {
        Board b1 = new Board();
        Board b2 = new Board();
        List l1 = new List();
        b1.getTaskLists().add(l1);
        assertFalse(b1.equals(b2));
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
        assertEquals("commons.Board@4593ff34[Id=0,TaskLists=[commons.List@82c57b3]]",b1.toString());
    }
}
