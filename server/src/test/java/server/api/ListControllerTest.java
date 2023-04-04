package server.api;

import commons.Board;
import commons.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

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
        assertTrue(sut.getListById((long)1).getStatusCode() == HttpStatus.OK);
        assertTrue(sut.getListById((long)2).getStatusCode() == HttpStatus.OK);
    }

    @Test
    void getListByIdNonExistent() {
        sut.addList(new List("0"), 0L);
        sut.addList(new List("1"), 0L);
        assertTrue(sut.getListById((long)3).getStatusCode() == HttpStatus.BAD_REQUEST);
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

    @Test
    void wsAddMessageTest() {
        commons.List testList = new List("test");
        commons.List saved = sut.addMessage(testList, 0L);
        assertEquals(saved, sut.getListById(saved.getId()).getBody());
    }

    @Test
    void wsAddMessageWrongTest() {
        assertNull(sut.addMessage(null, 0L));
    }

    @Test
    void wsDeleteMessageTest() {
        commons.List testList = new List("test");
        commons.List saved = sut.addMessage(testList, 0L);

        saved = sut.removeMessage(saved);
        assertNotNull(saved);
    }

    @Test
    void wsDeleteMessageNonExistentTest() {
        commons.List testList = new List("test");

        commons.List saved = sut.removeMessage(testList);
        assertNull(saved);
    }

    @Test
    void wsReplaceMessageTest() {
        commons.List testList = new List("test");
        commons.List saved = sut.addMessage(testList, 0L);
        saved.setTitle("newTitle");
        saved = sut.replaceMessage(saved);
        assertNotNull(saved);
    }

    @Test
    void wsReplaceMessageNonExistentTest() {
        commons.List testList = new List("test");
        commons.List saved = sut.addMessage(testList, 0L);
        sut.removeMessage(saved);

        saved.setTitle("newTitle");
        saved = sut.replaceMessage(testList);
        assertNull(saved);
    }
}