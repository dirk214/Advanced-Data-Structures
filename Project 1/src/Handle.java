/**
 * This object is what our HashTable class will store.
 * @author Andrew Herbert
 * @version 07.07.2017
 */
public class Handle {

    /**
     * The location of the record in the memory Pool.
     */
    private final int location;
    
    /**
     * This is the record associated with the handle.
     */
    private Record record;
    
    /**
     * The location of the handle in the hashTable.
     */
    private int index;
    
    /**
     * Instantiates the handle and sets the location field to the parameter.
     * @param location What we set the field to. 
     * @param record Is the record associated with the handle.
     */
    public Handle(int location, Record record) {
        this.location = location;
        this.record = record;
    }
    
    /**
     * A getter method for the location field.
     * @return The location.
     */
    public int getLocation() {
        return this.location;
    }
    
    /**
     * This returns the record associated with the handle.
     * @return The handle associated with the handle.
     */
    public Record getRecord() {
        return this.record;
    }
    
    /**
     * Returns the location of the handle in the hashTable.
     * @return The location of the handle in the hashTable.
     */
    public int getIndex() {
        return this.index;
    }
    
    /**
     * Sets the location of the handle in the hashTable.
     * @param index Where we set the index to be. 
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
}
