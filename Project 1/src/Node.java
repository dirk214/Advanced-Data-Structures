/**
 * This is the node implementation used by the DoublyLinkedList.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 * @param <T> is just a generic data instance. 
 */
public class Node<T> {
    
    /**
     * This is the next node references.
     */
    private Node<T> next; 
    
    /**
     * This is the previous node reference.
     */
    private Node<T> previous;
    
    /**
     * This is the data stored in the node.
     */
    private T data;
    
    /**
     * This is a node constructor with a data argument.
     * @param data This is the data in the node.
     */
    public Node(T data) {
        this(data, null);
    }
    
    /**
     * This is a node constructor with a data and node argument.
     * @param data The data in the node.
     * @param next A reference to the node ahead of this one.
     */
    public Node(T data, Node<T> next) {
        this.data = data;
        this.setNext(next);
    }
    
    
    
    /**
     * This gets the next node.
     * @return The next node reference.
     */
    public Node<T> getNext() {
        return this.next;
    }
    
    /**
     * This sets the next node to the parameter.
     * @param next Is the node we set the next reference to.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
    
    /**
     * This gets the previous node.
     * @return The previous node reference.
     */
    public Node<T> getPrevious() {
        return this.previous;
    }
    
    /**
     * This sets the previous node to the parameter node.
     * @param previous The node we set the previous reference to.
     */
    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }
    
    /**
     * This returns our data instance.
     * @return The data stored in the node.
     */
    public T getData() {
        return this.data;
    }
    
    /**
     * This sets the data stored in the node to the given data instance.
     * @param data Is the data instance we will store in the node.
     */
    public void setData(T data) {
        this.data = data;
    }
    
}
