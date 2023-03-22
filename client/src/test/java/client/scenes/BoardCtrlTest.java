package client.scenes;

import client.utils.TestServerUtils;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BoardCtrlTest {

    private TestServerUtils testServerUtils;

    private MainCtrl mainCtrl;
    private BoardCtrl sut;

    @BeforeEach
    public void setup() {
        testServerUtils = new TestServerUtils();
        mainCtrl = new MainCtrl();
        sut = new BoardCtrl(testServerUtils, mainCtrl);
    }

    // TODO: Test refresh method
}