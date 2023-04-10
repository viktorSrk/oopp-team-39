package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BoardControllerTest {
    private TestBoardRepository repo;

    private BoardController sut;

    @BeforeEach
    public void setup() {
        repo = new TestBoardRepository();
        sut = new BoardController(repo);
    }
    //because for non-multi-board application, it automatically adds 1 board,
    //thus the size of boards being +1 then expected
    @Test
    public void getAllBoardsTest() {
        Board board1 = sut.add(new Board("test")).getBody();
        Board board2 = sut.add(new Board("test2")).getBody();
        var actual = sut.getAll();
        assertNotNull(board1);
        assertNotNull(board2);
        assertEquals(List.of(board1, board2), actual);
        assertTrue(repo.calledMethods.contains("findAll"));
    }

    @Test
    public void getDefaultBoardByIdTest() {
        Board board1 = sut.add(new Board("test")).getBody();
        var actual = sut.getById(0L);
        assertNotNull(actual.getBody());
        assertEquals(0L, actual.getBody().getId());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    @Test
    public void databaseIsUsed() {
        sut.add(new Board("test"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    public void getByIdTest() {
        Board board = sut.add(new Board("test")).getBody();
        Board board2 = sut.add(new Board("test2")).getBody();
        var actual = sut.getById(0L);
        var actual2 = sut.getById(1L);
        assertEquals(board, actual.getBody());
        assertEquals(board2, actual2.getBody());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    @Test
    public void getByIdNotExistsTest() {
        assertEquals(BAD_REQUEST, sut.getById(1L).getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    public void getByNegativeId() {
        assertEquals(BAD_REQUEST, sut.getById(-1L).getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    public void add() {
        var actual = sut.add(new Board("test"));
        var actual2 = sut.add(new Board("test2"));
        assertNotNull(actual.getBody());
        assertNotNull(actual2.getBody());
        assertEquals(List.of(actual.getBody(), actual2.getBody()), repo.boards);
        assertTrue(repo.calledMethods.contains("save"));
    }
}
