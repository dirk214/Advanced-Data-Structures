import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Tests the memMan type.
 * @author Andrew Herbert
 * @version Jul 13, 2017
 */
public class BinprogTest {

    /**
     * Test method for the main method in memMan.
     */
    @SuppressWarnings("static-access")
    @Test
    public void testMain() {
        Binprog tester = new Binprog();
        String[] temp = new String[3];
        temp[0] = "100";
        temp[1] = "10";
        temp[2] = "p2_input_sample.txt";
        tester.main(temp);
        temp[2] = null;
        tester.main(temp);
        assertNotNull(tester);
    }

}