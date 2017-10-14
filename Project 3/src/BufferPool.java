import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;


/**
 * The implementation of the BufferPool type.
 * @author Andrew Herbert
 * @version Aug 8, 2017
 */
public class BufferPool {

    /**
     * The number of Buffers used in the pool.
     */
    private int numberOfBuffers;
    
    /**
     * The list of Buffers organized by BufferNodes.
     */
    private LinkedList<BufferNode> list;
    
    /**
     * The file to process.
     */
    private RandomAccessFile file;
    
    /**
     * The size of each buffer in bytes.
     */
    public static final int BUFFER_SIZE = 4096;
    
    /**
     * Sets up the buffer pool.
     * @param file The file to process.
     * @param numbOfBuffers The number of Buffers in the pool.
     * @throws FileNotFoundException If the file is non existent.
     */
    public BufferPool(File file, int numbOfBuffers) 
            throws FileNotFoundException {
        this.list = new LinkedList<BufferNode>();
        this.numberOfBuffers = numbOfBuffers;
        this.file = new RandomAccessFile(file, "rw");
    }
    
    /**
     * Gets the handle from the buffer.
     * @param node The BufferNode which contains the handle.
     * @param number The number to get the handle from.
     * @return The short representation of the Handle.
     */
    public short getHandle(BufferNode node, long number) {
        long location = number * 4 - node.getBlockID();
        return node.getBuffer().getShortAtSpot((int) location);
    }
    
    /**
     * Attempts to get the handle at the given location.
     * @param number The location to get the handle from.
     * @return The handle at the given location.
     * @throws IOException If problem with File is encountered. 
     */
    public short getHandleRequest(long number) throws IOException {
        BufferNode node = doesBufferContain(number);
        if (node == null) 
            node = readBuffer(number);
        return getHandle(node, number);
    }
    
    /**
     * Returns the BufferNode which contains the specific number.
     * @param number The number of the set to search for.
     * @return The BufferNode that the number set is in.
     */
    private BufferNode doesBufferContain(long number) {
        for (BufferNode node : list) {
            if (node.getBlockID() <= (number * 4) && 
                    (node.getBlockID() + BUFFER_SIZE) > (number * 4)) {
                StatWriter.cacheHits++;
                return node;
            }
        }
        StatWriter.cacheMisses++;
        return null;
    }
    
    /**
     * Returns a short representation of two bytes.
     * @param first The first byte of the new short.
     * @param second The second byte of the new short.
     * @return The short representation of the supplied bytes.
     */
    public short makeAShortValue(byte first, byte second) {
        ByteBuffer temp = ByteBuffer.allocate(2);
        temp.order(ByteOrder.BIG_ENDIAN);
        temp.put(first);
        temp.put(second);
        short returner = temp.getShort(0);
        return returner;
    }
    
    /**
     * Writes the contents of all of the Buffers in the pool to the file.
     * @throws IOException If problem with File is encountered.
     */
    public void flushThePool() throws IOException {
        for (BufferNode node : list) {
            if (node.isChanged()) {
                writeBufferToFile(node);
                node.setChanged(false);
            }
        }
    }
    
    /**
     * Sets the set of numbers(record) to the supplied bytes array.
     * @param bytes The new data at the location.
     * @param number The location of the numbers to overwrite.
     * @throws IOException If problem with File is encountered.
     */
    public void setTheRecord(byte[] bytes, long number) throws IOException {
        BufferNode node = doesBufferContain(number);
        if (node == null) 
            node = readBuffer(number);
        node.getBuffer().setRecord(bytes, 
                (int) (number * 4 - node.getBlockID()));
        node.setChanged(true);
    }
    
    /**
     * Gets the set of numbers(record) at the number parameter.
     * @param number The location of the (record) to get the numbers from.
     * @return The set of numbers at the given location.
     * @throws IOException If problem with File is encountered.
     */
    public byte[] getTheRecord(long number) throws IOException {
        BufferNode node = doesBufferContain(number);
        if (node == null) 
            node = readBuffer(number);
        else {
            list.remove(node);
            list.addFirst(node);
        }
            
        return node.getBuffer().getRecord(
                (int)(number * 4 - node.getBlockID()));
    }
    
    /**
     * Writes the Buffer in the node to the file.
     * @param node
     * @throws IOException when a problem with the file is encountered.
     */
    private void writeBufferToFile(BufferNode node)
        throws IOException {
        file.seek(node.getBlockID());
        file.write(node.getBuffer().getBytes());
        StatWriter.diskWrites++;
    }
    
    /**
     * Reads a block of bytes into a Buffer in the pool.
     * @param number The location in the file to begin reading from.
     * @return The BufferNode which references the Buffer 
     *  with the data at the given location.
     * @throws IOException if a problem with the File is encountered.
     */
    private BufferNode readBuffer(long number) throws IOException {
        BufferNode current;
        if (list.size() >= numberOfBuffers) {
            current = list.getLast();
            if (current.isChanged())
                writeBufferToFile(current);
            list.removeLast();
        }
        current = new BufferNode();
        long startingLocation = ((number * 4) / 4096);
        startingLocation *= 4096;
        current.setBlockID(startingLocation);
        file.seek(startingLocation);
        file.read(current.getBuffer().getBytes());
        StatWriter.diskReads++;
        list.addFirst(current);
        list.indexOf(current);
        return current;
    }
    
    /**
     * Prints out the first record of each block of BUFFER_SIZE 
     *  to standard output.
     * @return A formated string of the first record in each block.
     * @throws IOException If problem with File is encountered.
     */
    public String printFirstRecordOfEachBlock() throws IOException {
        String returner = "";
        int count = 0;
        for (long i = 0; i * 4 < file.length() - 4095; i += 1024) {
            byte[] bytes = getTheRecord(i);
            count++;
            if (count != 1)
                returner += "    ";
            returner += (makeAShortValue(bytes[0], bytes[1]) + "  " +
                    makeAShortValue(bytes[2], bytes[3]));
            if (count % 8 == 0) 
                returner += "\n";
        }
        return returner;
    }

}
