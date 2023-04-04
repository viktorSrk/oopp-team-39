package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

public class BoardControllerTest {
    private TestBoardRepository repo;

    private BoardController sut;

    @BeforeEach
    public void setup() {
        repo = new TestBoardRepository();
        sut = new BoardController(repo);
    }

    @Test
    public void databaseIsUsed() {
        sut.add(new Board("test"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    public void getAllBoardsTest() {
        sut.add(new Board("test"));
        sut.add(new Board("test2"));
        assertEquals(2, sut.getAll().size());
    }

    @Test
    public void getByIdTest() {
        Board saved1 = sut.add(new Board("test")).getBody();
        Board saved2 = sut.add(new Board("test2")).getBody();
        assertEquals(sut.getById(0L).getBody(), saved1);
        assertEquals(sut.getById(1L).getBody(), saved2);
    }

    @Test
    public void getByIdNotExistsTest() {
        sut.add(new Board("test"));
        sut.add(new Board("test2"));
        assertTrue(sut.getById((long)30).getStatusCode() == HttpStatus.BAD_REQUEST);
    }
}
