package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

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
        Board board0 = sut.getById(0L).getBody();
        Board board1 = sut.add().getBody();
        Board board2 = sut.add().getBody();
        var actual = sut.getAll();
        assertNotNull(board0);
        assertNotNull(board1);
        assertNotNull(board2);
        assertEquals(List.of(board0, board1, board2), actual);
        assertTrue(repo.calledMethods.contains("findAll"));
    }

    @Test
    public void getDefaultBoardByIdTest() {
        var actual = sut.getById(0L);
        assertNotNull(actual.getBody());
        assertEquals(0L, actual.getBody().getId());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    @Test
    public void getByIdTest() {
        Board board = sut.add().getBody();
        var actual = sut.getById(1L);
        assertEquals(board, actual.getBody());
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
        Board board = sut.getById(0L).getBody();
        assertNotNull(board);
        assertEquals(List.of(board), repo.boards);

        var actual = sut.add();
        assertNotNull(actual.getBody());
        assertEquals(List.of(board, actual.getBody()), repo.boards);
        assertTrue(repo.calledMethods.contains("save"));
    }
}
