import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the BufferPool methods.
 * @author Andrew Herbert
 * @version Aug 9, 2017
 */
public class BufferPoolTest {

    /**
     * The BufferPool for testing.
     */
    BufferPool pool;
    
    /**
     * File for testing.
     */
    File file = new File("p3_input_sample.txt");
    
    /**
     * Sets up the testing environment.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        pool = new BufferPool(file, 10);
    }

    /**
     * Test method for the FlushThePool method.
     * @throws IOException If problem with File is encountered.
     */
    @Test
    public void testFlushThePool() throws IOException {
        BufferNode testOne = new BufferNode();
        testOne.setChanged(false);        
        pool.flushThePool();
        testOne.setChanged(true);
        pool.flushThePool();
    }

    /**
     * Test method for the setTheRecord method.
     * @throws IOException If problem with File is encountered.
     */
    @Test
    public void testSetTheRecord() throws IOException {
        byte[] bytes = {};
        pool.setTheRecord(bytes, -1);
    }

    /**
     * Test method for the getTheRecord method.
     * @throws IOException If problem with File is encountered.
     */
    @Test
    public void testGetTheRecord() throws IOException {
        pool.getTheRecord(0);
    }

}
