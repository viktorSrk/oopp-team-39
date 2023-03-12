package client.scenes;

import client.utils.TestServerUtils;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BoardCtrlTest {

    private TestServerUtils testServerUtils;
    private BoardCtrl sut;

    @BeforeEach
    public void setup() {
        testServerUtils = new TestServerUtils();
        sut = new BoardCtrl(testServerUtils);
    }

    // TODO: Test refresh method
}