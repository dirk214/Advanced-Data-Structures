import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This tests the doublyLinkedList implementation.
 * @author Andrew Herbert
 * @version Jul 8, 2017
 */
public class DoublyLinkedListTest {

    /**
     * A instance of DoublyLinkedList used for testing.
     */
    DoublyLinkedList<Integer> tester;
    
    /**
     * Sets up the other testing methods.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tester = new DoublyLinkedList<Integer>();
    }

    /**
     * Test method for the constructor.
     */
    @Test
    public void testDoublyLinkedList() {
        assertNotNull(tester);
    }

    /**
     * Test method for the add method.
     */
    @Test
    public void testAdd() {
        tester.add(1);
        assertEquals(tester.count(), 1);
        assertEquals(tester.getCurrent().getData().toString(), "1");
        tester.add(25);
        assertEquals(tester.getCurrent().getData().toString(), "25");
        assertEquals(tester.getCurrent().getNext().getData().toString(), "1");
        tester.moveToStart();
        tester.setCount(-1);
        tester.add(40);
        assertEquals(tester.getCurrent().getData().toString(), "40");
        assertEquals(tester.getCurrent().getPrevious(), tester.getHead());
        tester = new DoublyLinkedList<Integer>();
        tester.add(1);
        tester.add(20);
        assertEquals(tester.getCurrent().getData().toString(), "20");
        assertEquals(tester.count(), 2);
        
    }
    
    /**
     * Test method for the isEmpty method.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(tester.isEmpty());
        tester.add(1);
        assertFalse(tester.isEmpty());
    }

    /**
     * Test method for the makeEmpty method.
     */
    @Test
    public void testMakeEmpty() {
        assertTrue(tester.isEmpty());
        tester.add(1);
        assertFalse(tester.isEmpty());
        tester.makeEmpty();
        assertTrue(tester.isEmpty());
    }

    /**
     * Test method for the next method.
     */
    @Test
    public void testNext() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        assertEquals(tester.getCurrent().getData().toString(), "10");
        tester.next();
        assertEquals(tester.getCurrent().getData().toString(), "5");
    }

    /**
     * Test method for the getHead method.
     */
    @Test
    public void testGetHead() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        assertNotNull(tester.getHead());
    }

    /**
     * Test method for the getCurrent method.
     */
    @Test
    public void testGetCurrent() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        assertEquals(tester.getCurrent().getData().toString(), "10");
    }

    /**
     * Test method for the getTail method.
     */
    @Test
    public void testGetTail() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        assertNotNull(tester.getTail());
    }

    /**
     * Test method for the previous method.
     */
    @Test
    public void testPrevious() {
        tester.moveToStart();
        tester.previous();
        assertEquals(tester.getCurrent(), tester.getHead());
        tester.add(1);
        tester.add(5);
        tester.add(10);
        tester.previous();
        assertSame(tester.getCurrent(), tester.getHead());
    }

    /**
     * Test method for the isAtStart method.
     */
    @Test
    public void testIsAtStart() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        assertFalse(tester.isAtStart());
        tester.moveToStart();
        assertTrue(tester.isAtStart());
    }

    /**
     * Test method for the isAtEnd method.
     */
    @Test
    public void testIsAtEnd() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        tester.moveToEnd();
        assertTrue(tester.isAtEnd());
        tester.previous();
        assertFalse(tester.isAtEnd());
    }

    /**
     * Test method for the moveToStart method.
     */
    @Test
    public void testMoveToStart() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        assertFalse(tester.isAtStart());
        assertEquals(tester.getCurrent().getData().toString(), "10");
        tester.moveToStart();
        assertTrue(tester.isAtStart());
        assertEquals(tester.getCurrent().getData(), null);
    }

    /**
     * Test method for the moveToEnd method.
     */
    @Test
    public void testMoveToEnd() {
        tester.add(1);
        tester.add(5);
        tester.add(10);
        tester.moveToEnd();
        assertTrue(tester.isAtEnd());
        tester.moveToStart();
        assertFalse(tester.isAtEnd());
        tester.moveToEnd();
        assertTrue(tester.isAtEnd());
    }

    /**
     * Test method for the count method.
     */
    @Test
    public void testCount() {
        assertEquals(tester.count(), 0);
        tester.add(1);
        tester.add(5);
        tester.add(10);
        assertEquals(tester.count(), 3);
    }

    /**
     * Test method for the toString method.
     */
    @Test
    public void testToString() {
        tester.add(10);
        tester.add(5);
        tester.add(1);
        assertEquals(tester.toString(), "[ 1 5 10 ]");
    }
    
    /**
     * Tests the append method.
     */
    @Test
    public void testAppend() {
        tester.add(10);
        tester.append(tester.getCurrent().getData(), 5);
        assertEquals(tester.count(), 2);
        assertEquals(tester.getCurrent().getData().toString(), "5");
        tester.append(tester.getCurrent().getData(), 15);
        tester.append(tester.getCurrent().getData(), 25);
    }
    
    /**
     * Tests the setCount Method.
     */
    @Test
    public void testSetCount() {
        assertEquals(tester.count(), 0);
        tester.setCount(10);
        assertEquals(tester.count(), 10);
    }

}
