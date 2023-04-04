package server.api;

import commons.Board;
import commons.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListControllerTest {

    private TestListRepository repo;

    private TestBoardRepository boardRepo;

    private ListController sut;

    private BoardController boardSut;

    @BeforeEach
    public void setup() {
        boardRepo = new TestBoardRepository();
        boardSut = new BoardController(boardRepo);

        Board testBoard = boardSut.add().getBody();

        repo = new TestListRepository();
        sut = new ListController(repo, boardRepo);
    }

    @Test
    void getAll() {
        var actual = sut.getAll();
        assertTrue(repo.calledMethods.contains("findAll"));
        assertEquals(new ArrayList<List>(), actual);
    }

    @Test
    void getListById() {
        sut.addList(new List("0"), 0L);
        sut.addList(new List("1"), 0L);
        assertTrue(repo.existsById((long)1));
        assertTrue(repo.existsById((long)2));
    }

    @Test
    void addList() {
        var list = new List("a");
        list.setId((long) 1);
        sut.addList(list, 0L);
        assertTrue(repo.lists.contains(list));
    }

    @Test
    void removeList() {
        var list = new List("a");
        list.setId((long)1);
        sut.addList(list, 0L);
        sut.removeList(list);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.lists.contains(list));
    }

    @Test
    void replaceList() {
        var list = new List("a");
        sut.addList(list, 0L);
        list.setTitle("b");
        sut.replaceList(list, list.getId());

        assertTrue(repo.getById(list.getId()).getTitle().equals("b"));
    }
}