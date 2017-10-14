/**
 * This is an implementation of a doubly linked list.
 * @author Andrew Herbert, dirk214
 * @version Jul 7, 2017
 * @param <T> is just a generic object.
 */
public class DoublyLinkedList<T> {

    /**
     * This is the reference to the head of the list.
     */
    private Node<T> head;

    /**
     * This is the reference to the last node in the list.
     */
    private Node<T> tail;
    
    /**
     * Keeps track of where we are in the list.
     */
    private Node<T> current;

    /**
     * This is the current amount of nodes in the list.
     */
    private int count;

    /**
     * Sets up an empty DoublyLinkedList.
     */
    public DoublyLinkedList() {
        head = new Node<T>( null );
        tail = new Node<T>( null );
        current = head;
        count = 0;
    }

    /**
     * Inserts the value into the list as a node.
     * @param value The value to insert.
     */
    public void add(T value) {
        Node<T> node = new Node<T>(value);
        if (count == 0) {
            node.setNext(tail);
            node.setPrevious(head);
            head.setNext(node);
            tail.setPrevious(node);
        }
        else if (count > 0 && !(current.equals(head))) {
            node.setNext(current);
            Node<T> currentPrev = null;
            currentPrev = current.getPrevious();
            node.setPrevious(currentPrev);
            current.setPrevious(node);
            node.getPrevious().setNext(node);
        }
        else {
            node.setNext(current.getNext());
            node.setPrevious(current);
            current.getNext().setPrevious(node);
            current.setNext(node);
        }
        current = node;
        count++;
    }

    /**
     * Checks if the list is empty.
     * @return If the list is empty.
     */
    public boolean isEmpty() {
        if (count == 0)
            return true;
        else 
            return false;
    }

    /**
     * Makes the list empty.
     */
    public void makeEmpty() {
        head = new Node<T>(null);
        tail = new Node<T>(null);
        current = head;
        count = 0;
    }

    /**
     * Sets current to its following node.
     */
    public void next() {
        Node<T> next = null;
        next = current.getNext();
        current = next;
    }

    /**
     * Returns the first node of the list.
     * @return The first node of the list.
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Returns the current node in the list.
     * @return The current node in the list.
     */
    public Node<T> getCurrent() {
        return current;
    }

    /**
     * Returns the last node in the list.
     * @return The last node in the list.
     */
    public Node<T> getTail() {
        return tail;
    }

    /**
     * Sets current to the previous node.
     */
    public void previous() {
        if (!(current.equals(head))) {
            Node<T> previous = null;
            previous = current.getPrevious();
            current = previous;
        }
    }

    /**
     * Checks if current is at the beginning of the list.
     * @return If current is at the beginning of the list.
     */
    public boolean isAtStart() {
        return current == head;
    }

    /**
     * Checks if current is at the end of the list.
     * @return If current is at the end of the list.
     */
    public boolean isAtEnd() {
        return current == tail;
    }

    /**
     * Moves current to the beginning of the list.
     */
    public void moveToStart() {
        current = head;
    }

    /**
     * Moves current to the end of the list.
     */
    public void moveToEnd() {
        current = tail;
    }

    /**
     * Gets the count of the list.
     * @return count The count of the list.
     */
    public int count() {
        return count;
    }
    
    /**
     * Prints a string representation of the list.
     * @return A readable string representation of the list.
     */
    public String toString() {
        String string = "[ ";
        Node<T> temp = head;
        while (temp != null) {
            if (temp.getData() != null)
                string +=  Integer.parseInt(temp.getData().toString()) + " ";
            temp = temp.getNext();
        }
        string += "]";
        return string;
    }
    
    /**
     * Sets part of the linked list into a new Node.
     * @param clean The element not full.
     * @param full The full element.
     */
    public void append(T clean, T full) {
        this.getCurrent().setData(full);
        this.add(clean);
        this.next();
    }
    
    /**
     * Sets the count field.
     * @param count What the count field is set to.
     */
    public void setCount(int count) {
        this.count = count;
    }
    
}
