import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the StatWriter class.
 * @author Andrew Herbert
 * @version Aug 7, 2017
 */
public class StatWriterTest {

    /**
     * Test method for the proper output method.
     */
    @Test
    public void testProperOutput() {
        StatWriter test = new StatWriter();
        assertNotNull(test);
        StatWriter.cacheHits = 10;
        StatWriter.cacheMisses = 15;
        StatWriter.diskReads = 20;
        StatWriter.diskWrites = 25;
        StatWriter.dataFileName = "TestingFile";
        StatWriter.sortExecutionTime = 700;
        String tester = "------  STATS ------\n" +
                "File name: TestingFile\n"
                + "Cache Hits: 10\n"
                + "Cache Misses: 15\n"
                + "Disk Reads: 20\n"
                + "Disk Writes: 25\n"
                + "Time to Sort: 700\n";
        assertEquals(StatWriter.properOutput(), tester);
    }

}
