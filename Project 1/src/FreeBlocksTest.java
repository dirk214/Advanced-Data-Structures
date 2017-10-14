import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * Tests the methods from the FreeBlocks type.
 * @author Andrew Herbert
 * @version Jul 12, 2017
 */
public class FreeBlocksTest {

    /**
     * This is the FreeBlocks instance used in testing.
     */
    FreeBlocks tester;
    
    /**
     * Sets up the testing variables.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tester = new FreeBlocks(0, 100);
    }

    /**
     * Test method for the constructor.
     */
    @Test
    public void testFreeBlocks() {
        assertNotNull(tester);
        assertEquals(tester.getFront(), 0);
        assertEquals(tester.getBack(), 100);
        assertFalse(tester.getOccupied());
    }

    /**
     * Test method for getFront.
     */
    @Test
    public void testGetFront() {
        assertEquals(tester.getFront(), 0);
    }

    /**
     * Test method for getBack.
     */
    @Test
    public void testGetBack() {
        assertEquals(tester.getBack(), 100);
    }

    /**
     * Test method for getOccupied.
     */
    @Test
    public void testGetOccupied() {
        assertFalse(tester.getOccupied());
    }

    /**
     * Test method for setFront.
     */
    @Test
    public void testSetFront() {
        assertEquals(tester.getFront(), 0);
        tester.setFront(10);
        assertEquals(tester.getFront(), 10);
    }

    /**
     * Test method for setBack.
     */
    @Test
    public void testSetBack() {
        assertEquals(tester.getBack(), 100);
        tester.setBack(200);
        assertEquals(tester.getBack(), 200);
    }

    /**
     * Test method for setOccupied.
     */
    @Test
    public void testSetOccupied() {
        assertFalse(tester.getOccupied());
        tester.setOccupied(true);
        assertTrue(tester.getOccupied());
    }

    /**
     * Test method for toString.
     */
    @Test
    public void testToString() {
        assertEquals(tester.toString(), "[0 , 100]");
    }
    
    /**
     * Tests the getRoom method.
     */
    @Test
    public void testGetRoom() {
        assertEquals(tester.getRoom(), 100);
        tester.setFront(50);
        assertEquals(tester.getRoom(), 50);
    }

}
