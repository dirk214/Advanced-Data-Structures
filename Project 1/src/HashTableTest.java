import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * This tests the HashTable type.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 */
public class HashTableTest {

    /**
     * This is a HashTable instance used for testing.
     */
    HashTable hasher;
    
    /**
     * This is a record instance to aid with testing.
     */
    Record record;
    
    /**
     * This is a handle instance to aid with testing.
     */
    Handle handle;
    
    /**
     * This is a MemoryPool instance to aid with testing.
     */
    MemoryManager memory;
    
    /**
     * Sets up the testing variables.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        memory = new MemoryManager(1000);
        hasher = new HashTable(500, memory);
        record = new Record(1, 5, "DC");
        handle = new Handle(0, record);
    }

    /**
     * Test method for HashTable constructor.
     */
    @Test
    public void testHashTable() {
        assertNotNull(hasher);
    }

    /**
     * Test method for HashTables getSize method.
     */
    @Test
    public void testGetSize() {
        assertEquals(hasher.getSize(), 500);
    }

    /**
     * Test method for HashTables getCSize method.
     */
    @Test
    public void testGetCSize() {
        assertEquals(hasher.getCSize(), 0);
    }

    /**
     * Test method for HashTables getTable method.
     */
    @Test
    public void testGetTable() {
        assertNotNull(hasher.getTable());
    }

    /**
     * Test method for HashTables Insert method.
     */
    @Test
    public void testInsert() {
        assertTrue(hasher.insert(handle, hasher.hash(handle)));
        assertFalse(hasher.insert(null,  10));
        HashTable test = new HashTable(1, null);
        assertTrue(test.insert(handle, 0));
        HashTable test2 = new HashTable(5, null);
        assertTrue(test2.insert(handle, 0));
        assertTrue(test2.insert(handle, 0));
        
    }

    /**
     * Test method for HashTables Hash method.
     */
    @Test
    public void testHash() {
        assertEquals(135, hasher.hash(handle));
        assertEquals(-1, hasher.hash(null));
    }

    /**
     * Test method for HashTables rehash method.
     */
    @Test
    public void testRehash() {
        assertEquals(136, hasher.rehash(hasher.hash(handle)));
        assertEquals(hasher.rehash(500), 0);
        hasher.insert(handle, 0);
        assertEquals(hasher.rehash(500), 1);
    }

    /**
     * Test method for HashTables remove method.
     */
    @Test
    public void testRemove() {
        hasher.insert(handle, hasher.hash(handle));
        assertEquals(handle, hasher.remove(hasher.hash(handle)));
        assertEquals(hasher.remove(-1), null);
        assertEquals(hasher.remove(50), null);
    }

    /**
     * Test method for HashTables printTable method.
     * @throws IOException 
     */
    @Test
    public void testPrintTable() throws IOException {
        HashTable tester = new HashTable(3, memory);
        tester.insert(handle, tester.hash(handle));
        memory.insert(record, MemoryManager.asByte(record));
        assertNotNull(tester.printTable());
    }
    
    /**
     * Tests the printSpecificRecord method.
     */
    @Test
    public void testPrintSpecificRecord() {
        hasher.insert(handle, hasher.hash(handle));
        try {
            memory.insert(record, MemoryManager.asByte(record));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(hasher.printSpecificRecord(handle), 
                "0; 1 5 DC");        
    }
    
    /**
     * Tests the findName method.
     */
    @Test
    public void testFindName() {
        assertEquals(hasher.findName("DC"), -1);
        hasher.insert(handle, 0);
        assertEquals(hasher.findName("DC"), 0);
    }
    
    /**
     * This tests the getInitialSize method.
     */
    @Test
    public void testGetInitialSize() {
        assertEquals(hasher.getInitialSize(), 500);
    }

}
