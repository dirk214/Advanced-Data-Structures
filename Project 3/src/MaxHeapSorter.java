import java.io.IOException;

/**
 * A data structure for max heap and sorting methods.
 * @author Andrew Herbert
 * @version Aug 8, 2017
 */
public class MaxHeapSorter {

    /**
     * The BufferPool instance which this sorts.
     */
    private BufferPool pool;
    
    /**
     * The maximum size of the heap.
     */
    private long maxSize;
    
    /**
     * The number of nodes in the heap.
     */
    private long number;
    
    /**
     * Creates the maxHeap type, starts up the BufferPool,
     *  and sets the class fields.
     * @param pool The BufferPool this will work with.
     * @param number The number of elements that will be within the heap.
     * @throws IOException If problem with File is encountered.
     */
    public MaxHeapSorter(BufferPool pool, long number) throws IOException {
        this.pool = pool;
        this.number = number;
        this.maxSize = number;
        buildTheHeap();
    }
    
    /**
     * Builds the heap structure.
     * @throws IOException If problem with File is encountered.
     */
    public void buildTheHeap() throws IOException {
        for (long i = number / 2 - 1; i >= 0; i--) 
            shiftDownTheHeap(i);
    }
    
    /**
     * Shifts the heap down at the given position.
     * @param i The position of the element to shift down.
     * @throws IOException If problem with File is encountered.
     */
    private void shiftDownTheHeap(long i) throws IOException {
        while (!isLeaf(i)) {
            long temp = leftChild(i);
            if ((temp < (number - 1)) && (pool.getHandleRequest(temp) 
                    < pool.getHandleRequest(temp + 1)))
                temp++;
            long compare = pool.getHandleRequest(temp);
            if ((compare) <= pool.getHandleRequest(i))
                return;
            swapNumbers(i, temp);
            i = temp;
        }
    }

    /**
     * Swap to elements within the maxHeap structure.
     * @param numOne The location of the first value to swap.
     * @param numTwo The location fo the second value to swap.
     * @throws IOException If a problem with the File is encountered.
     */
    private void swapNumbers(long numOne, long numTwo) throws IOException {
        byte[] tempBytes = pool.getTheRecord(numOne);
        byte[] tempBytes2 = pool.getTheRecord(numTwo);
        pool.setTheRecord(tempBytes, numTwo);
        pool.setTheRecord(tempBytes2, numOne);
    }

    /**
     * Sorts the elements in the heap by removing the max once every element.
     * @throws IOException If a problem with the File is encountered.
     */
    public void sortTheHeap() throws IOException {
        for (int i = 0; i < maxSize; i++)
            this.removeMaxElement();
    }
    
    /**
     * Removes the max element from the heap.
     * @return The max element in the heap.
     * @throws IOException If a problem with the file is encountered.
     */
    public byte[] removeMaxElement() throws IOException {
        swapNumbers(0, --number);
        if (number != 0)
            shiftDownTheHeap(0);
        return pool.getTheRecord(number);
    }
    
    /**
     * Getter method for the number of records in the heap.
     * @return The number of records in the heap.
     */
    public long getSizeOfHeap() {
        return number;
    }
    
    /**
     * Return if the element at the given location is a leaf.
     * @param i The location of the element to check.
     * @return If the element at the location is a leaf.
     */
    public boolean isLeaf(long i) {
        return (i >= (number / 2)) && (i < number);
    }
    
    /**
     * Return the location of the elements left child.
     * @param i The location of the element to check.
     * @return The location of the elements left child.
     */
    public long leftChild(long i) {
        return 2 * i + 1;
    }
    
}
