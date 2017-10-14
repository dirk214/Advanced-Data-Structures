import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests the HeapSort methods.
 * @author Andrew Herbert
 * @version Aug 2, 2017
 */
public class HeapSortTest {

    /**
     * Test method for the main method.
     * @throws IOException 
     */
    @Test
    public void testMain() throws IOException {
        String[] fail2 = {"p3_input_sample.txt", "10"};
        HeapSort.main(fail2);
        assertNotNull("myStatFile.txt");
        String[] test3 = {"testHeavyBuffer.txt", "15", "myStatFile.txt"};
        HeapSort.main(test3);
        assertNotNull("myStatFile.txt");
        String[] test = {"p3_input_sample.txt", "15", "myStatFile.txt"};
        HeapSort.main(test);
        assertNotNull("myStatFile.txt");
        String[] test2 = {"p3_input_sample.bin", "4", "myStatFile.txt"};
        HeapSort.main(test2);
        assertNotNull("myStatFile.txt");
        HeapSort tester = new HeapSort();
        assertNotNull(tester);
    }

}
