/**
 * This is a implementation of a Hash Table to stores Handle objects.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 */
public class HashTable {

    /**
     * An array of handles which we use as our hash table data storage.
     */
    private Handle[] handles;
    
    /**
     * This is a reference to how big the array is instantiated.
     */
    private int size;
    
    /**
     * This is the current size of the array. 
     */
    private int cSize;
    
    /**
     * This is the initial size of the hashTable.
     */
    private final int initialSize;
    
    /**
     * This is the associated memoryPool with the hashTable.
     */
    MemoryManager memory;
    
    /**
     * This is the constructor which instantiates the array of handles.
     * @param size How big the array will be.
     * @param memory Is the memoryPool we will use.
     */
    public HashTable(int size, MemoryManager memory) {
        this.size = size;
        this.handles = new Handle[this.size];
        this.cSize = 0;
        this.memory = memory;
        this.initialSize = size;
    }
    
    /**
     * This returns the max size of the array.
     * @return The max size of the array.
     */
    public int getSize() {
        return this.size;
    }
    
    /**
     * This returns the current Size of the array.
     * @return The current size of the array.
     */
    public int getCSize() {
        return this.cSize;
    }
    
    /**
     * This returns the array of Handles
     * @return The array of Handles.
     */
    public Handle[] getTable() {
        return this.handles;
    }
    
    /**
     * This returns the associated memoryPool.
     * @return The associated memoryPool.
     */
    public MemoryManager getMemoryPool() {
        return this.memory;
    }
    
    /**
     * Inserts the handle into the array.
     * @param handle The handle we wish to insert.
     * @param index The location we wish to insert the handle to.
     * @return If we could insert it or not.
     */
    public boolean insert(Handle handle, int index) {
        if (handle == null)
            return false;
        if (this.handles[index] == null) {
            this.handles[index] = handle;
            this.cSize++;
            handle.setIndex(index);
            return true;
        }
        else {
            return insert(handle, rehash(index));
        }
    }
    
    /**
     * This is the hashing function to get the location to insert it.
     * @param handle The handle we wish to get the hash value from.
     * @return The hash integer for the Handle.
     */
    public int hash(Handle handle) {
        if (handle == null)
            return -1;
        String x = handle.getRecord().cityname;
        char[] ch;
        ch = x.toCharArray();
        int xlength = x.length();
        int i;
        int sum;
        for (sum = 0, i = 0; i < xlength; i++)
            sum += ch[i];
        return sum % this.size;
    }
    
    /**
     * This rehashes the old integer if the location is occupied.
     * @param oldHash The integer that must be rehashed.
     * @return The newly rehashed integer.
     */
    public int rehash(int oldHash) {
        oldHash++;
        if (oldHash >= this.size) {
            oldHash = 0;
            for (int i = 0; i < this.size; i++) {
                if (this.handles[i] == null) {
                    return oldHash;
                }
                oldHash++;
            }
        }
        return oldHash;
    }
    
    /**
     * This attempting to remove the handle at a given spot in the array.
     * @param rSpot Where to remove in the array.
     * @return The handle we removed or null if no handle is there.
     */
    public Handle remove(int rSpot) {
        if (rSpot != -1 && this.handles[rSpot] != null) {
            Handle handle = this.handles[rSpot];
            this.handles[rSpot] = null;
            this.cSize--;
            return handle;
        }
        return null;
    }
    
    /**
     * This print out the entire to a string.
     * @return A string representation of the table.
     */
    public String printTable() {
        String string = "";
        for (int i = 0; i < this.size; i++) {
            if (this.handles[i] == null)
                string += "[EMPTY]\n";
            else {
                string += printSpecificRecord(handles[i]) + "\n";
            }
        }
        string += "\n";
        return string;
    }
    
    /**
     * Prints a specific record from the hashTable and its corresponding 
     *  record in memPool.
     * @param handle The handle we wish to print.
     * @return A readable string form of the handle and its corresponding
     *  record.
     */
    public String printSpecificRecord(Handle handle) {
        return handle.getLocation() + "; " + handle.getRecord().x 
                + " " + handle.getRecord().y + " " + 
                handle.getRecord().cityname;
    }
    
    /**
     * Searches for the integer location with its name equal to 
     *  the name parameter.
     * @param name The name we are searching for.
     * @return The handle with the given name or null if not there.
     */
    public int findName(String name) {
        for (int i = 0; i < this.size; i++) {
            if (this.handles[i] != null) {
                String temp = this.handles[i].getRecord().cityname.trim();
                name.trim();
                if (temp.equals(name))
                    return i;
            }
        }
        return -1;
    }
    
    /**
     * Getter method for the initialSize of the HashTable.
     * @return The initialSize of the HashTable.
     */
    public int getInitialSize() {
        return this.initialSize;
    }
    
    /**
     * Prints a specific record for the find command.
     * @param handle What we use to find the record. 
     * @return A string version of the record.
     */
    public String printSpecificRecordForFind(Handle handle) {
        return handle.getRecord().cityname + " " 
                + handle.getRecord().x + " " 
                + handle.getRecord().y;
    }
    
}