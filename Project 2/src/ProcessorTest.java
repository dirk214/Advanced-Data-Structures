import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

/**
 * Tests the processor type.
 * @author Andrew Herbert
 * @version Jul 13, 2017
 */
public class ProcessorTest {

    /**
     * This is an instance of MemoryPool used to aid testing.
     */
    MemoryManager memory;
    
    /**
     * This is an instance of HashTable used to aid testing.
     */
    HashTable hasher;
    
    /**
     * This is a instance of Processor used to test the processor methods.
     */
    Processor tester;
    
    /**
     * Sets up the testing variables.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        memory = new MemoryManager(100);
        hasher = new HashTable(15, memory);
        File file = new File("proTest.txt");
        tester = new Processor(file, hasher);
    }

    /**
     * Test method for the constructor.
     */
    @Test
    public void testProcessor() {
        assertNotNull(tester);
    }

    /**
     * Test method for the process method.
     */
    @Test
    public void testProcess() {
        tester.process();
    }

}
