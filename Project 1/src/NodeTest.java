import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class test all of the methods in the node class.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 */
public class NodeTest {
    
    /**
     * Our testing Node.
     */
    Node<?> tester;
    
    /**
     * A node we will use as the next node reference.
     */
    Node<?> next;
    
    /**
     * A node we will use as the previous node reference.
     */
    Node<?> previous;
    
    /**
     * Sets up the node fields for easier testing.
     * @throws Exception If we find a error.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void setUp() throws Exception {
        next = new Node<Object>(15, null);
        tester = new Node(10, next);
        previous = new Node(5, tester);
    }

    /**
     * Tests the first constructor of node.
     */
    @Test
    public void testNodeNodeOfTNodeOfT() {
        Node<?> tester2 = new Node<Object>(20);
        assertNotNull(tester2);
    }

    /**
     * Tests the second constructor of node.
     */
    @Test
    public void testNodeNodeOfTNodeOfTT() {
        Node<?> tester2 = new Node<Object>(20, null);
        assertNotNull(tester2);
    }

    /**
     * Tests the getNext method of node.
     */
    @Test
    public void testGetNext() {
        assertEquals(next, tester.getNext());
    }

    /**
     * Tests the setNext method of node.
     */
    @Test
    public void testSetNext() {
        tester.setNext(null);
        assertNull(tester.getNext());
    }

    /**
     * Test the getPrevious method of node.
     */
    @Test
    public void testGetPrevious() {
        tester.setPrevious(null);
        assertEquals(tester.getPrevious(), null);
    }

    /**
     * Tests the setPrevious method of node.
     */
    @Test
    public void testSetPrevious() {
        tester.setPrevious(null);
        assertNull(tester.getPrevious());
    }

    /**
     * Tests the getData method of node.
     */
    @Test
    public void testGetData() {
        assertEquals(tester.getData(), 10);
    }

    /**
     * Tests the setData method of Node.
     */
    @Test
    public void testSetData() {
        tester.setData(null);
        assertNull(tester.getData());
    }

}
