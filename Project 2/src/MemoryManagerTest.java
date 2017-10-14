import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the memoryPool type.
 * @author Andrew Herbert
 * @version Jul 12, 2017
 */
public class MemoryManagerTest {

    /**
     * Testing instance of MemoryPool.
     */
    MemoryManager tester;
    
    /**
     * Sets up testing variables.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tester = new MemoryManager(100);
    }

    /**
     * Test method for constructor.
     */
    @Test
    public void testMemoryPool() {
        assertNotNull(tester);
    }

    /**
     * Test method for getMemoryPool.
     */
    @Test
    public void testGetMemoryPool() {
        assertNotNull(tester.getMemoryPool());
    }

    /**
     * Test method for getSize.
     */
    @Test
    public void testGetSize() {
        assertEquals(tester.getSize(), 100);
    }

    /**
     * Test method for getFreeSpace.
     */
    @Test
    public void testGetFreeSpace() {
        assertNotNull(tester.getFreeSpace());
    }

    /**
     * Test method for insert.
     * @throws IOException 
     */
    @Test
    public void testInsert() throws IOException {
        assertEquals(tester.getFreeSpace().count(), 1);
        Record record = new Record(10, 20, "DC");
        tester.getFreeSpace().moveToEnd();
        tester.insert(record, MemoryManager.asByte(record));
        assertEquals(tester.getFreeSpace().count(), 2);
        MemoryManager mem = new MemoryManager(5);
        assertEquals(mem.getSize(), 5);
        mem.insert(record, MemoryManager.asByte(record));
        assertEquals(mem.getFreeSpace().count(), 2);
        assertNotEquals(mem.getInitialSize(), mem.getSize());
    }

    /**
     * Test method remove.
     * @throws IOException 
     */
    @Test
    public void testRemove() throws IOException {
        assertEquals(tester.remove(null), null);
        assertEquals(tester.getFreeSpace().count(), 1);
        Record record = new Record(10, 20, "DC");
        Handle handle = tester.insert(record, MemoryManager.asByte(record));
        assertEquals(tester.getFreeSpace().count(), 2);
        tester.getFreeSpace().moveToEnd();
        byte[] bytes = tester.remove(handle);
        assertEquals(bytes.length, MemoryManager.sizeof(record));
    }

    /**
     * Test method for mergeFreeBlocks.
     * @throws IOException 
     */
    @Test
    public void testMergeFreeBlocks() throws IOException {
        assertEquals(tester.getFreeSpace().count(), 1);
        Record record = new Record(10, 20, "DC");
        tester.insert(record, MemoryManager.asByte(record));
        assertEquals(tester.getFreeSpace().count(), 2);
        Record record2 = new Record(1, 4, "LES");
        Record record3 = new Record(2, 83, "Hello");
        tester.insert(record2, MemoryManager.asByte(record2));
        Handle handle = tester.insert(record3, MemoryManager.asByte(record3));
        assertEquals(tester.getFreeSpace().count(), 4);
        tester.printFreeBlocks();
        tester.remove(handle);
        tester.printFreeBlocks();
        assertEquals(tester.getFreeSpace().count(), 3);
    }

    /**
     * Test method for printFreeBlocks.
     * @throws IOException 
     */
    @Test
    public void testPrintFreeBlocks() throws IOException {
        assertEquals(tester.getFreeSpace().count(), 1);
        Record record = new Record(10, 20, "DC");
        tester.insert(record, MemoryManager.asByte(record));
        tester.printFreeBlocks();
        assertEquals(tester.printFreeBlocks(), "{[75 , 100]}");
        assertEquals(tester.getFreeSpace().count(), 2);
    }
    
    /**
     * Tests the getInitialSize method.
     */
    @Test
    public void testGetInitialSize() {
        assertEquals(tester.getInitialSize(), 100);
    }

}
