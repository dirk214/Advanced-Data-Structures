/**
 * A node in the linked list which references a buffer in the buffer pool.
 * @author Andrew Herbert
 * @version Aug 8, 2017
 */
public class BufferNode {

    /**
     * The value stored when the node is empty.
     */
    private static final int EMPTY = -1;
    
    /**
     * The buffer referenced by the node. 
     */
    private Buffer buffer;
    
    /**
     * The location of the buffer in the File.
     */
    private long blockID;
    
    /**
     * A boolean to keep track of whether or not the buffer changes.
     */
    private boolean changed;

    /**
     * Default constructor just sets up an empty node.
     */
    public BufferNode() {
        buffer = new Buffer();
        blockID = EMPTY;
        changed = false;
    }

    /**
     * Getter method for the BlockID.
     * @return The BlockID of the Buffer.
     */
    public long getBlockID() {
        return blockID;
    }
    
    /**
     * Getter method for the changed boolean.
     * @return If the buffer has been changed.
     */
    public boolean isChanged() {
        return changed;
    }
    
    /**
     * Getter method for the Buffer referenced by the node.
     * @return The Buffer referenced by the node.
     */
    public Buffer getBuffer() {
        return buffer;
    }
    
    /**
     * Sets the BlockId field.
     * @param iD What to set the BlockID field to.
     */
    public void setBlockID(long iD) {
        this.blockID = iD;
    }
    
    /**
     * Sets the Buffer referenced by the node.
     * @param buffer The Buffer the node will now reference.
     */
    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }
    
    /**
     * Sets the changed status of the Buffer.
     * @param changed The new status of the Buffer.
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
}
