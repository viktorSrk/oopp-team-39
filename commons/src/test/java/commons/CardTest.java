package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testGetId() {
        var a = new Card("a");
        assertEquals(0, a.getId());
    }

    @Test
    void testSetId() {
        var a = new Card("a");
        a.setId(123);
        assertEquals(123, a.getId());
    }

    @Test
    void testTitle() {
        var a = new Card("a");
        a.setTitle("b");
        assertEquals("b", a.getTitle());
    }

    @Test
    void testGetList() {
        var l = new List();
        var a = new Card("a", l);
        assertEquals(l, a.getList());
    }

    @Test
    void testSetList() {
        var a = new Card("a");
        var l = new List();
        a.setList(l);
        assertEquals(l, a.getList());
    }

    @Test
    void testList() {
        var a = new Card("a");
        java.util.List<String> tasks = new ArrayList<>();
        tasks.add("0");
        tasks.add("1");
        tasks.add("2");
        a.setTasks(tasks);
        assertEquals(tasks, a.getTasks());
    }

    @Test
    void testEqualsHashCode() {
        var a = new Card("a");
        var b = new Card("a");
        assertEquals(a,b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testNotEqualsHashCode() {
        var a = new Card("a");
        var b = new Card("b");
        assertNotEquals(a,b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void hasToString() {
        var actual = new Card("a").toString();
        assertTrue(actual.contains(Card.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("title"));
    }

    @Test
    void testForObjectMapper() {
        assertNotNull(new Card());
    }
}