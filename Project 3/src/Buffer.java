import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * The implementation of the Buffer type.
 * @author Andrew Herbert
 * @version Aug 8, 2017
 */
public class Buffer {

    /**
     * The data in the file this buffer can use.
     */
    private byte[] bytes;
    
    /**
     * Default constructor just sets up the Buffers byte array.
     */
    public Buffer() {
        this.bytes = new byte[BufferPool.BUFFER_SIZE];
    }
    
    /**
     * Getter method for the bytes array in the Buffer.
     * @return The bytes array used in the Buffer.
     */
    public byte[] getBytes() {
        return this.bytes;
    }
    
    /**
     * Sets the bytes at the location to the bytes in the supplied
     *  array of bytes.
     * @param newBytes The new data to put at the location.
     * @param location The location to put the new data.
     */
    public void setRecord(byte[] newBytes, int location) {
        for (int i = 0; i < newBytes.length; i++) 
            this.bytes[i + location] = newBytes[i];
    }
    
    /**
     * Getter method for the bytes in the Buffer at the given location.
     * @param location The location to get the bytes from.
     * @return The bytes at the location.
     */
    public byte[] getRecord(int location) {
        byte[] tempBytes = new byte[4];
        System.arraycopy(bytes, location, tempBytes, 0, 4);
        return tempBytes;
    }
    
    /**
     * Returns the short value from the bytes at location and location + 1.
     * @param location The location to get the first byte of the short value.
     * @return The short value found at location and location + 1.
     */
    public short getShortAtSpot(int location) {
        ByteBuffer temp = ByteBuffer.allocate(2);
        temp.order(ByteOrder.BIG_ENDIAN);
        temp.put(bytes[location]);
        temp.put(bytes[location + 1]);
        short returner = temp.getShort(0);
        return returner;
    }

}
