package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveCardMessageTest {

    List l = new List("list");
    Card card = new Card("#", l, 0);
    MoveCardMessage message = new MoveCardMessage(1, 2, 0, card);

    @Test
    void getListIdSource() {
        l.setId((long) 1);
        assertEquals(message.getListIdSource(), l.getId());

    }

    @Test
    void getListIdTarget() {
        l.setId((long) 2);
        assertEquals(message.getListIdTarget(), l.getId());
    }

    @Test
    void getIndex() {
        assertEquals(message.getIndex(), 0);
    }

    @Test
    void getCard() {
        assertEquals(message.getCard(), card);
    }

    @Test
    void setCard() {
        Card c = new Card("card", l, 1);
        message.setCard(c);
        assertEquals(message.getCard(), c);
    }
}