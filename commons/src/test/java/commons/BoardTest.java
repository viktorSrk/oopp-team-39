package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BoardTest {

    @Test
    public void getIdTest() {
        Board b1 = new Board("test");
        assertEquals(0, b1.getId());
    }

    @Test
    public void setIdTest() {
        Board b1 = new Board("test");
        b1.setId(1862);
        assertEquals(1862, b1.getId());
    }

    @Test
    public void getNameTest() {
        Board b1 = new Board("test");
        assertEquals("test", b1.getName());
    }

    @Test
    public void getTaskListTest() {
        Board b1 = new Board("test");
        List l1 = new List();
        b1.getTaskLists().add(l1);
        java.util.List<List> taskListTest = new ArrayList<>();
        taskListTest.add(l1);
        assertEquals(taskListTest, b1.getTaskLists());
    }

    @Test
    public void setNameTest() {
        Board b1 = new Board("test");
        b1.setName("otherTest");
        assertEquals("otherTest", b1.getName());
    }

    @Test
    public void equalsTest() {
        Board b1 = new Board("test");
        Board b2 = new Board("test");
        List l1 = new List();
        b1.getTaskLists().add(l1);
        b2.getTaskLists().add(l1);
        assertEquals(b1, b2);
    }

    @Test
    public void notEqualsTest() {
        Board b1 = new Board("test");
        Board b2 = new Board("test2");
        List l1 = new List();
        b1.getTaskLists().add(l1);
        assertNotEquals(b1, b2);
    }

    @Test
    public void hashEqualsTest() {
        Board b1 = new Board("test");
        Board b2 = new Board("test");
        List l1 = new List();
        b1.getTaskLists().add(l1);
        b2.getTaskLists().add(l1);
        assertEquals(b1.hashCode(),b2.hashCode());
    }

    @Test
    public void hashNotEqualsTest() {
        Board b1 = new Board("test");
        Board b2 = new Board("otherTest");
        List l1 = new List();
        b1.getTaskLists().add(l1);
        assertNotEquals(b1.hashCode(),b2.hashCode());
    }

    @Test
    public void testToString() {
        Board b1 = new Board("test");
        List l1 = new List();
        b1.getTaskLists().add(l1);
        assertEquals("commons.Board@"+ Integer.toHexString(System.identityHashCode(b1)) +"[Id=0,Name=test,TaskLists=[" + l1 +"]]",
                b1.toString());
    }
}
