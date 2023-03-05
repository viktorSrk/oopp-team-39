package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {


    @Test
    void setTitle() {
        var a = new Card("a");
        a.setTitle("b");
        assertEquals("b", a.title);
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
}