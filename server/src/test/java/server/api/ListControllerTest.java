package server.api;

import commons.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListControllerTest {

    private TestListRepository repo;

    private ListController sut;

    @BeforeEach
    public void setup() {
        repo = new TestListRepository();
        sut = new ListController(repo);
    }

    @Test
    void getAll() {
        var actual = sut.getAll();
        assertTrue(repo.calledMethods.contains("findAll"));
        assertEquals(new ArrayList<List>(), actual);
    }

    @Test
    void getListById() {
        sut.addList(new List("0"));
        sut.addList(new List("1"));
        assertTrue(repo.existsById((long)0));
        assertTrue(repo.existsById((long)1));
    }

    @Test
    void addList() {
        var list = new List("a");
        list.setId((long) 1);
        sut.addList(list);
        assertTrue(repo.lists.contains(list));
    }

    @Test
    void removeList() {
        var list = new List("a");
        list.setId((long)1);
        sut.addList(list);
        sut.removeList(list);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.lists.contains(list));
    }

    @Test
    void replaceList() {
        var list = new List("a");
        sut.addList(list);
        list.setTitle("b");
        sut.replaceList(list, list.getId());

        assertTrue(repo.getById(list.getId()).getTitle().equals("b"));
    }
}