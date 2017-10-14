import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the BinTree methods.
 * @author Andrew Herbert
 * @version Jul 26, 2017
 */
public class BinTreeTest {

    /**
     * Testing instance of BinTree.
     */
    BinTree tester;
    
    /**
     * A instance of record for testing.
     */
    Record record; 
    
    /**
     * A different instance of record for testing.
     */
    Record record2;
    
    /**
     * A different instance of record for testing.
     */
    Record record3;
    
    /**
     * Sets up the testing environment.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tester = new BinTree();
        record = new Record(20, 20, "DC");
        record2 = new Record(60, 60, "Richmond");
        record3 = new Record(45, 30, "Blacksburg");
    }

    /**
     * Test method for BinTree's constructor.
     */
    @Test
    public void testBinTree() {
        assertNotNull(tester);
        assertEquals(tester.root, null);
    }

    /**
     * Test method for insert method.
     */
    @Test
    public void testInsert() {
        assertEquals(tester.root, null);
        assertTrue(tester.insert(record));
        tester.insert(record2);
        tester.insert(record3);
        assertFalse(tester.insert(null));
        Record temps = new Record(-5, 50, "EE");
        Record temps2 = new Record(10, -54, "GG");
        Record temps3 = new Record(10000, 54, "GG");
        Record temps4 = new Record(10, 5400, "GG");
        assertFalse(tester.insert(temps));
        assertFalse(tester.insert(temps2));
        assertFalse(tester.insert(temps3));
        assertFalse(tester.insert(temps4));
    }

    /**
     * Test method for find method.
     */
    @Test
    public void testFindRecord() {
        assertEquals(tester.root, null);
        tester.insert(record);
        assertEquals(tester.find(record), record);
        tester.insert(record2);
        assertEquals(tester.find(record2), record2);
        tester.insert(record3);
        assertEquals(tester.find(record3), record3);
    }

    /**
     * Test method for delete method.
     */
    @Test
    public void testDelete() {
        assertEquals(tester.root, null);
        tester.insert(record);
        assertTrue(tester.delete(record));
        assertFalse(tester.delete(record));
        tester.insert(record2);
        tester.insert(record3);
        assertFalse(tester.delete(record));
        assertTrue(tester.delete(record2));
        assertFalse(tester.delete(record2));
        tester.insert(record);
        tester.insert(record2);
        assertTrue(tester.delete(record3));
        assertTrue(tester.delete(record2));
        assertTrue(tester.delete(record));
        assertFalse(tester.delete(record3));
        assertFalse(tester.delete(record2));
        assertFalse(tester.delete(record));
        assertFalse(tester.delete(null));
        Record temps = new Record(-5, 50, "EE");
        Record temps2 = new Record(10, -54, "GG");
        Record temps3 = new Record(10000, 54, "GG");
        Record temps4 = new Record(10, 5400, "GG");
        assertFalse(tester.delete(temps));
        assertFalse(tester.delete(temps2));
        assertFalse(tester.delete(temps3));
        assertFalse(tester.delete(temps4));
    }

    /**
     * Test method for find in region method.
     */
    @Test
    public void testFindLongLongLongLong() {
        assertEquals(tester.root, null);
        tester.insert(record);
        tester.insert(record2);
        tester.insert(record3);
        ArrayList<Record> checker = tester.find(0, 100, 0, 100);
        assertTrue(checker.contains(record));
        assertTrue(checker.contains(record2));
        assertTrue(checker.contains(record3));
        assertEquals(tester.find(-500, -500, -500, -100), null);
        assertEquals(tester.find(2000, 2050, 2000, 2050), null);
        assertNotNull(tester.find(-50, 100, 200, 250));
        assertNotNull(tester.find(200, 2000, 200, 250));
        assertNotNull(tester.find(200, 260, -600, 1000));
        assertNotNull(tester.find(200, 243, 200, 3000));
    }

    /**
     * Test method for printTree method.
     */
    @Test
    public void testPrintTree() {
        assertEquals(tester.root, null);
        assertEquals(tester.printTree(), "E, 0, 0, 1024, 1024\n");
        tester.insert(record);
        tester.insert(record2);
        tester.insert(record3);
        String check = tester.printTree();
        String checker = "I, 0, 0, 1024, 1024\n" +
                "  I, 0, 0, 512, 1024\n" + 
                "    I, 0, 0, 512, 512\n" + 
                "      I, 0, 0, 256, 512\n" +
                "        I, 0, 0, 256, 256\n" +
                "          I, 0, 0, 128, 256\n" +
                "            I, 0, 0, 128, 128\n" + 
                "              I, 0, 0, 64, 128\n" +
                "                I, 0, 0, 64, 64\n" +
                "                  DC, 20, 20\n" +
                "                  Richmond, 60, 60\n" + 
                "                  Blacksburg, 45, 30\n";
        assertNotEquals(check, checker);
    }
    
    /**
     * Tests the findInt method.
     */
    @Test
    public void testFindInt() {
        assertEquals(tester.getFindInt(), 0);
        tester.insert(record);
        tester.insert(record2);
        tester.insert(record3);
        tester.find(0, 100, 0, 100);
        assertEquals(tester.getFindInt(), 21);
    }

}
