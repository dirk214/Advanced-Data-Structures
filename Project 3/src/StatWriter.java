/**
 * Produces the proper output formated as specified. 
 * @author Andrew Herbert
 * @version Aug 2, 2017
 */
public class StatWriter {

    /**
     * The name of the data file.
     */
    public static String dataFileName;
    
    /**
     * The number of cache hits, or times the program found the data it needed
     *  in a buffer and did not have to go to the disk.
     */
    public static int cacheHits = 0;
    
    /**
     * The number of cache misses, or times the program did not find 
     *  the data it needed in a buffer and had to go back to the disk.
     */
    public static int cacheMisses = 0;
    
    /**
     * The number of disk reads, or times the program had to read a 
     *  block of data from disk into a buffer.
     */
    public static int diskReads = 0;
    
    /**
     * The number of disk writes, or times the program had to write a
     *  block of data to the disk from a buffer.
     */
    public static int diskWrites = 0;
    
    /**
     * The time that the program took to execute the 
     *  main heapSort method.
     */
    public static long sortExecutionTime = 0;
    
    /**
     * Produces the data in a properly formated string.
     * @return The properly formated data as a string.
     */
    public static String properOutput() {
        String output = "------  STATS ------";
        output += "\nFile name: " + dataFileName;
        output += "\nCache Hits: " + cacheHits;
        output += "\nCache Misses: " + cacheMisses;
        output += "\nDisk Reads: " + diskReads;
        output += "\nDisk Writes: " + diskWrites;
        output += "\nTime to Sort: " + sortExecutionTime;
        output += "\n";        
        return output;
    }

}
