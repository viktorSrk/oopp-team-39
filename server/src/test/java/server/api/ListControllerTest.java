package server.api;

import commons.Board;
import commons.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class ListControllerTest {

    private TestListRepository repo;

    private Board testBoard;

    private ListController sut;

    @BeforeEach
    public void setup() {
        TestBoardRepository boardRepo = new TestBoardRepository();
        BoardController boardSut = new BoardController(boardRepo);

        testBoard = boardSut.add().getBody();

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
    void cannotGetListByNegativeId() {
        var actual = sut.getListById(-1L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    void cannotGetListByNonExistingId() {
        var actual = sut.getListById(10L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    void getListById() {
        List list0 = new List("0");
        List list1 = new List("1");
        sut.addList(list0, testBoard.getId());
        sut.addList(list1, testBoard.getId());
        assertEquals(list0, sut.getListById(list0.getId()).getBody());
        assertEquals(list1, sut.getListById(list1.getId()).getBody());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    @Test
    void cannotAddNullList() {
        var actual = sut.addList(null, testBoard.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    void cannotAddListWithoutTitle() {
        List list = new List("");
        var actual = sut.addList(list, testBoard.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    void cannotAddListWithoutBoard() {
        List list = new List("a");
        var actual = sut.addList(list, null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    void addList() {
        var list = new List("a");
        assertFalse(repo.lists.contains(list));

        var actual = sut.addList(list, testBoard.getId());
        assertTrue(repo.lists.contains(list));
        assertEquals(list, actual.getBody());
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    void cannotRemoveNullList() {
        var actual = sut.removeList(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    void cannotRemoveListWithoutTitle() {
        var actual = sut.removeList(new List(""));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    void cannotRemoveNonExistingList() {
        var actual = sut.removeList(new List("a"));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    void removeList() {
        var list = new List("a");
        sut.addList(list, testBoard.getId());
        assertTrue(repo.lists.contains(list));

        var actual = sut.removeList(list);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.lists.contains(list));
        assertEquals(list, actual.getBody());
    }

    @Test
    void cannotReplaceWithNullList() {
        List list = new List("a");
        sut.addList(list, testBoard.getId());

        var actual = sut.replaceList(null, list.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void cannotReplaceWithListWithoutTitle() {
        List list = new List("a");
        sut.addList(list, testBoard.getId());

        var actual = sut.replaceList(new List(""), list.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void cannotReplaceNonExistingList() {
        var actual = sut.replaceList(new List("b"), 0L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    void replaceList() {
        var list = new List("a");
        sut.addList(list, testBoard.getId());
        var newList = new List("b");
        assertFalse(repo.lists.contains(newList));

        var actual = sut.replaceList(newList, list.getId());
        assertNotNull(actual.getBody());
        assertEquals(newList.getTitle(), actual.getBody().getTitle());
        assertEquals(list.getId(), actual.getBody().getId());
        assertTrue(repo.calledMethods.contains("save"));
    }
}