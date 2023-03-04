package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


import static org.junit.jupiter.api.Assertions.*;

import commons.Card;
import org.springframework.http.ResponseEntity;

class CardControllerTest {

    private TestCardRepository repo;

    private CardController sut;

    @BeforeEach
    public void setup() {
        repo = new TestCardRepository();
        sut = new CardController(repo);
    }

    @Test
    void cannotAddNullCard() {
        Card card = null;
        var actual = sut.addCard(card);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }


    @Test
    void getAllCards() {
        var actual = sut.getAllCards();
        repo.calledMethods.contains("findAll");
        assertEquals(actual, repo.cards);
    }


    @Test
    void addCard() {
        var card = new Card("a");
        card.setId(1);
        sut.addCard(card);
        assertTrue(repo.cards.contains(card));

    }

    @Test
    void removeCard() {
        var card = new Card("a");
        card.setId(1);
        sut.addCard(card);
        sut.removeCard(card);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.cards.contains(card));

    }

    @Test
    void replaceCard() {
        var card = new Card("a");
        var card2 = new Card("b");
        sut.addCard(card);
        sut.replaceCard(card2, card.getId());

        assertFalse(repo.cards.contains(card));
        assertTrue(repo.cards.contains(card2));
    }
}