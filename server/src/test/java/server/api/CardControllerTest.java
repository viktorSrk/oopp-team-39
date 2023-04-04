package server.api;

import commons.Board;
import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


class CardControllerTest {

    private TestCardRepository repo;

    private CardController sut;

    private commons.List testList;

    @BeforeEach
    public void setup() {
        TestBoardRepository boardRepo = new TestBoardRepository();
        BoardController boardSut = new BoardController(boardRepo);

        Board testBoard = boardSut.add().getBody();
        assertNotNull(testBoard);

        TestListRepository listRepo = new TestListRepository();
        ListController listSut = new ListController(listRepo, boardRepo);

        testList = listSut.addList(new commons.List("test"), testBoard.getId()).getBody();

        repo = new TestCardRepository();
        sut = new CardController(repo, listRepo);
    }

    @Test
    void getEmptyListOfAllCards() {
        var actual = sut.getAllCards();
        assertTrue(repo.calledMethods.contains("findAll"));
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void getNonEmptyListOfAllCards() {
        Card card = new Card("a");
        sut.addCard(card, testList.getId());
        var actual = sut.getAllCards();
        assertTrue(repo.calledMethods.contains("findAll"));
        assertEquals(List.of(card), actual);
    }

    @Test
    void cannotGetCardByNegativeId() {
        var actual = sut.getCardById(-1L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    void cannotGetCardByNonExistingId() {
        var actual = sut.getCardById(0L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    void getCardById() {
        Card card = new Card("a");
        sut.addCard(card, card.getId());
        var actual = sut.getCardById(card.getId());
        assertTrue(repo.calledMethods.contains("findById"));
        assertEquals(card, actual.getBody());
    }

    @Test
    void cannotAddNullCard() {
        var actual = sut.addCard(null, testList.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertEquals(Collections.emptyList(), repo.cards);
        assertEquals(Collections.emptyList(), repo.calledMethods);
    }

    @Test
    void cannotAddCardWithoutTitle() {
        var actual = sut.addCard(new Card(""), testList.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertEquals(Collections.emptyList(), repo.cards);
        assertEquals(Collections.emptyList(), repo.calledMethods);
    }

    @Test
    void cannotAddCardWithoutList() {
        var actual = sut.addCard(new Card("a"), null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertEquals(Collections.emptyList(), repo.cards);
        assertEquals(Collections.emptyList(), repo.calledMethods);
    }

    @Test
    void addCard() {
        var card = new Card("a");
        assertFalse(repo.calledMethods.contains("save"));

        var actual = sut.addCard(card, testList.getId());
        assertEquals(card, actual.getBody());
        assertEquals(List.of(card), repo.cards);
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    void cannotRemoveNullCard() {
        var actual = sut.removeCard(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    void cannotRemoveCardWithoutTitle() {
        Card card = new Card("");
        var actual = sut.removeCard(card);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    void cannotRemoveCardWithoutExistingId() {
        var actual = sut.removeCard(new Card("a"));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    void removeCard() {
        var card = new Card("a");
        Card saved = sut.addCard(card, testList.getId()).getBody();

        var actual = sut.removeCard(saved);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.cards.contains(saved));
        assertEquals(saved, actual.getBody());
    }

    @Test
    void cannotReplaceWithNullCard() {
        var actual = sut.replaceCard(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    void cannotReplaceWithCardWithoutTitle() {
        Card card = new Card("a");
        sut.addCard(card, testList.getId());

        card.setTitle("");
        var actual = sut.replaceCard(card);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void cannotReplaceWithNonExistingCard() {
        var actual = sut.replaceCard(new Card("b"));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    void replaceCard() {
        var card = new Card("a");
        sut.addCard(card, testList.getId());

        card.setTitle("b");
        var actual = sut.replaceCard(card);
        assertNotNull(actual.getBody());
        assertEquals(card, actual.getBody());
        assertTrue(repo.cards.contains(card));
        assertTrue(repo.calledMethods.contains("save"));
    }
}