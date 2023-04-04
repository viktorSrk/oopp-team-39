package server.api;

import commons.Board;
import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


class CardControllerTest {

    private TestCardRepository repo;

    private TestListRepository listRepo;

    private TestBoardRepository boardRepo;

    private CardController sut;
    private ListController listSut;
    private BoardController boardSut;

    @BeforeEach
    public void setup() {
        boardRepo = new TestBoardRepository();
        boardSut = new BoardController(boardRepo);

        Board testBoard = boardSut.add().getBody();

        listRepo = new TestListRepository();
        listSut = new ListController(listRepo, boardRepo);

        listSut.addList(new commons.List("test"), testBoard.getId());

        repo = new TestCardRepository();
        sut = new CardController(repo, listRepo);
    }

    @Test
    void cannotAddNullCard() {
        Card card = null;
        var actual = sut.addCard(card, 1L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertTrue(repo.cards.size() == 0);
    }


    @Test
    void getAllCards() {
        var actual = sut.getAllCards();
        assertTrue(repo.calledMethods.contains("findAll"));
        var emptyList = new ArrayList<Card>();
        assertEquals(actual, emptyList);

        Card card = new Card("a");
        sut.addCard(card, 0L);
        actual = sut.getAllCards();
        assertTrue(actual.contains(card));
    }


    @Test
    void addCard() {
        var card = new Card("a");
        sut.addCard(card, 1L);
        assertTrue(repo.cards.contains(card));

    }

    @Test
    void removeCard() {
        var card = new Card("a");
        Card saved = sut.addCard(card, 0L).getBody();
        sut.removeCard(saved);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.cards.contains(saved));
    }

    @Test
    void replaceCard() {
        var card = new Card("a");
        sut.addCard(card, 1L);
        card.setTitle("b");
        sut.replaceCard(card);

        assertTrue(repo.getById(card.getId()).getTitle().equals("b"));
    }
}