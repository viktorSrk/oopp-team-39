package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        sut.add();
        assertTrue(repo.calledMethods.contains("save"));
    }

    //because for non-multi-board application, it automatically adds 1 board,
    //thus the size of boards being +1 then expected
    @Test
    public void getAllBoardsTest() {
        sut.add();
        sut.add();
        assertEquals(3, sut.getAll().size());
    }

    @Test
    public void getByIdTest() {
        sut.add();
        sut.add();
        assertTrue(repo.existsById((long)0));
        assertTrue(repo.existsById((long)1));
    }

    @Test
    public void getByIdNotExistsTest() {
        sut.add();
        sut.add();
        assertFalse(repo.existsById((long)30));
    }
}
