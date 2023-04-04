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

    //because for non-multi-board application, it automatically adds 1 board,
    //thus the size of boards being +1 then expected
    @Test
    public void getAllBoardsTest() {
        sut.add(new Board("test"));
        sut.add(new Board("test2"));
        assertEquals(3, sut.getAll().size());
    }

    @Test
    public void getByIdTest() {
        Board saved1 = sut.add(new Board("test")).getBody();
        Board saved2 = sut.add(new Board("test2")).getBody();
        assertEquals(sut.getById(1L).getBody(), saved1);
        assertEquals(sut.getById(2L).getBody(), saved2);
    }

    @Test
    public void getByIdNotExistsTest() {
        sut.add(new Board("test"));
        sut.add(new Board("test2"));
        assertTrue(sut.getById((long)30).getStatusCode() == HttpStatus.BAD_REQUEST);
    }
}
