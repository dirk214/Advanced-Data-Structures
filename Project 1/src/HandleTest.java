import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is my testing class for the Handle type.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 */
public class HandleTest {
    
    /**
     * This is just a local handle used for testing.
     */
    Handle handle;
    
    /**
     * This is a local record to help testing handle.
     */
    Record record;
    
    /**
     * This sets up the handle field to test the methods.
     * @throws Exception If we encounter a problem with the handle set up.
     */
    @Before
    public void setUp() throws Exception {
        this.record = new Record(2, 3, "DC");
        this.handle =  new Handle(5, record);
    }
    
    /**
     * This tests the constructor of the handle type.
     */
    @Test
    public void testHandle() {
        assertNotEquals(this.handle, null);
    }
    
    /**
     * This tests the getLocation method of the Handle type.
     */
    @Test
    public void testGetLocation() {
        assertEquals(this.handle.getLocation(), 5);
    }
    
    /**
     * This tests the handles getRecord method.
     */
    @Test
    public void testGetRecord() {
        assertEquals(this.record, handle.getRecord());
    }
    
    /**
     * This tests the handles getIndex method.
     */
    @Test
    public void testGetIndex() {
        assertEquals(handle.getIndex(), 0);
    }
    
    /**
     * This tests the handles setIndex method.
     */
    @Test
    public void testSetIndex() {
        assertEquals(handle.getIndex(), 0);
        handle.setIndex(5);
        assertEquals(handle.getIndex(), 5);
    }
}
