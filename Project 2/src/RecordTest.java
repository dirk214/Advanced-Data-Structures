import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the tester class for the record type.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 */
public class RecordTest {
    
    /**
     * This is the record used to test the record type.
     */
    Record record;
    
    /**
     * This sets up the testing cases.
     * @throws Exception if we encounter an error.
     */
    @Before
    public void setUp() throws Exception {
        this.record = new Record(5, 10, "DC");
    }
    
    /**
     * This tests the records constructor.
     */
    @Test
    public void testRecord() {
        Record tester = new Record(1, 2, "Blacksburg");
        assertNotNull(tester);
    }
    
}
