/**
 * This is an implementation of a blocks type.
 * @author Andrew Herbert
 * @version Jul 12, 2017
 */
public class FreeBlocks {

    /**
     * This references the starting part of the block.
     */
    private int front;
    
    /**
     * This references the back part of the block.
     */
    private int back;
    
    /**
     * Tells if the block is occupied or free.
     */
    private boolean occupied;
    
    /**
     * Sets up the block type. 
     * @param front Where the front of the block is.
     * @param back Where the back of the block is.
     */
    public FreeBlocks(int front, int back) {
        this.front = front;
        this.back = back;
        this.occupied = false;
    }
    
    /**
     * Getter method for the front field.
     * @return The front of the block.
     */
    public int getFront() {
        return this.front;
    }
    
    /**
     * Getter method for the back field.
     * @return The back of the block.
     */
    public int getBack() {
        return this.back;
    }
    
    /**
     * Getter method for the occupied field.
     * @return If the block is occupied.
     */
    public boolean getOccupied() {
        return this.occupied;
    }
    
    /**
     * Sets the front field.
     * @param front What the front is set to.
     */
    public void setFront(int front) {
        this.front = front;
    }
    
    /**
     * Sets the back field.
     * @param back What the back is set to.
     */
    public void setBack(int back) {
        this.back = back;
    }
    
    /**
     * Sets the occupied field.
     * @param bool What the occupied field is set to.
     */
    public void setOccupied(boolean bool) {
        this.occupied = bool;
    }

    /**
     * Makes a readable string of the block.
     * @return A readable string of the block.
     */
    public String toString() {
        return "[" + this.front + " , " + this.back + "]";
    }
    
    /**
     * Getter method for the room in the block.
     * @return The room in the block.
     */
    public int getRoom() {
        return this.back - this.front;
    }
    
}
