package client.utils;

import java.util.ArrayList;
import java.util.List;

public class TestServerUtils extends ServerUtils {

    public List<commons.List> lists = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    // TODO: I've hardcoded the lists field,
    //  since the add lists function is not fully integrated yet
    public TestServerUtils() {
        lists.add(new commons.List("a"));
        lists.add(new commons.List("b"));
    }

    @Override
    public void testURL() {
        return;
    }

    @Override
    public List<commons.List> getLists() {
        calledMethods.add("getLists");
        return lists;
    }


}
