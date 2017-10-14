import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This type creates an array of bytes in memory and provides the methods
 *  needed to manipulate it.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 */
public class MemoryManager {

    /**
     * The memory we allocated as a array of bytes.
     */
    private byte[] bytes;

    /**
     * The size of the array.(Number of bytes allocated).
     */
    private int size;

    /**
     * The initial size of the array.
     */
    private int initialSize;

    /**
     * The DoublyLinkedList representation of the free blocks in 
     *  the memoryPool.
     */
    private DoublyLinkedList<FreeBlocks> freeSpace;

    /**
     * This is the constructor used to instantiate the MemoryPool.
     * @param size Is the initial size of the memoryPool.
     */
    public MemoryManager(int size) {
        this.size = size;
        this.initialSize = size;
        this.bytes = new byte[this.size];
        this.freeSpace = new DoublyLinkedList<FreeBlocks>();
        this.freeSpace.add(new FreeBlocks(0, this.size));
    }

    /**
     * Getter method for the allocated memory array.
     * @return The allocated memory array.
     */
    public byte[] getMemoryPool() {
        return this.bytes;
    }

    /**
     * Getter method for the size of the array.
     * @return The size of the array.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Getter method for the freeSpace part of the memory that was allocated.
     * @return The representation of the free space in the memory.
     */
    public DoublyLinkedList<FreeBlocks> getFreeSpace() {
        return this.freeSpace;
    }

    /**
     * Attempts to insert the record into the pool.
     * @param record The record we wish to insert.
     * @param byter Is the array of bytes associated with the record.
     * @return The associated handle with the record.
     * @throws IOException 
     */
    public Handle insert(Record record, byte[] byter) throws IOException {
        int spot = findFirstFreeLocationOfSize(byter.length);
        if (spot != -1) {
            this.bytes[spot] = (byte) MemoryManager.sizeof(record);
            for (int i = 0; i < byter.length - 1; i++)
                this.bytes[i + spot] = byter[i];
            return new Handle(spot, record);
        }
        else
            return null;
    }

    /**
     * Attempts to remove the record at the handle.
     * @param handle Is the handle with the record to remove.
     * @return The byte representation of the record removed.
     * @throws IOException 
     */
    public byte[] remove(Handle handle) throws IOException {
        if (handle == null)
            return null;
        int spot = handle.getLocation();
        int tSize = MemoryManager.sizeof(handle.getRecord());
        byte[] tBytes = new byte[tSize];
        for (int i = 0; i < tSize; i++) {
            tBytes[i] = this.bytes[spot + i];
            this.bytes[spot + i] = 0;
        }
        for (int i = 0; i < this.freeSpace.count(); i++) {
            if (this.freeSpace.getCurrent() == this.freeSpace.getTail()) {
                this.freeSpace.moveToStart();
                this.freeSpace.next();
            }
            if (this.freeSpace.getCurrent().getData().getFront() == spot) {
                this.freeSpace.getCurrent().getData().setOccupied(false);
                this.mergeFreeBlocks();
                continue;
            }
            this.freeSpace.next();
        }
        return tBytes;
    }

    /**
     * Finds the index of the first section of bytes in our pool which
     *  can hold the size parameter. 
     * @param bSize The size we need to find.
     * @return The location of the starting spot of that space.
     */
    private int findFirstFreeLocationOfSize(int bSize) {
        int spot = -1;
        if (bSize <= 255) { 
            for (int i = 0; i < this.freeSpace.count(); i++) {
                if (this.freeSpace.getCurrent() == this.freeSpace.getTail()) {
                    this.freeSpace.moveToStart();
                    this.freeSpace.next();
                }
                if (this.freeSpace.getCurrent().getData().
                        getRoom() > bSize) {
                    if (!this.freeSpace.getCurrent().getData()
                            .getOccupied()) {
                        spot = this.freeSpace.getCurrent()
                                .getData().getFront();
                        this.freeSpace.
                        add(new FreeBlocks(spot, spot + bSize + 1));
                        this.freeSpace.getCurrent().getData().
                        setOccupied(true);
                        this.freeSpace.next();
                        this.freeSpace.getCurrent().getData().
                        setFront(spot + bSize + 1);
                        break;
                    }
                }
                this.freeSpace.next();
            }
            if (spot == -1) {
                this.expand();
                return this.findFirstFreeLocationOfSize(bSize);
            }
        }
        return spot;
    }

    /**
     * Merges all of the free blocks in the list.
     */
    public void mergeFreeBlocks() {
        boolean bool = false;
        if (this.freeSpace.getCurrent().getPrevious() != 
                this.freeSpace.getHead() && !this.freeSpace.getCurrent()
                .getPrevious().getData().getOccupied()) {
            this.freeSpace.getCurrent().setData(new FreeBlocks(this.freeSpace.
                    getCurrent().getPrevious().getData().getFront(), 
                    this.freeSpace.getCurrent().getData().getBack()));
            Node<FreeBlocks> temp = this.freeSpace.getCurrent().getPrevious();
            this.freeSpace.getCurrent().getPrevious().getPrevious().
            setNext(this.freeSpace.getCurrent());
            this.freeSpace.getCurrent().setPrevious(temp.getPrevious());
            bool = true;
            this.freeSpace.setCount(this.freeSpace.count() - 1);
        }
        if (this.freeSpace.getCurrent().getNext() != this.freeSpace.getTail()
                && !this.freeSpace.getCurrent().getNext().getData()
                .getOccupied()) {
            this.freeSpace.getCurrent().setData(new FreeBlocks(this
                    .freeSpace.getCurrent().getData().
                    getFront(), this.freeSpace.getCurrent().
                    getNext().getData().getBack()));
            Node<FreeBlocks> temp = this.freeSpace.getCurrent().getNext();
            this.freeSpace.getCurrent().getNext().getNext().
            setPrevious(this.freeSpace.getCurrent());
            this.freeSpace.getCurrent().setNext(temp.getNext());
            bool = true;
            this.freeSpace.setCount(this.freeSpace.count() - 1);
        }
        if (bool) {
            while (this.freeSpace.getCurrent().getData().getOccupied()) 
                this.freeSpace.next();
        }
    }

    /**
     * Forms a readable output of the freeBlocks.
     * @return A string representation of the freeBlocks.
     */
    public String printFreeBlocks() {
        String string = "{";
        this.freeSpace.moveToStart();
        this.freeSpace.next();
        while (!this.freeSpace.isAtEnd()) {
            if (this.freeSpace.getCurrent().getData().getFront() == 
                    this.freeSpace.getCurrent().getData().getBack() && 
                    this.freeSpace.getCurrent().getNext() == 
                    this.freeSpace.getTail())
                break;
            if (!this.freeSpace.getCurrent().getData().getOccupied())
                string += (this.freeSpace.getCurrent().getData().toString());
            this.freeSpace.next();
        }
        string += "}";
        return string;
    }

    /**
     * Expands the array of bytes if more space is needed.
     */
    private void expand() {
        byte[] temp = new byte[this.size + this.initialSize];
        for (int i = 0; i < this.size; i++) {
            temp[i] = this.bytes[i];
        }
        this.bytes = temp;
        this.freeSpace.moveToEnd();
        this.freeSpace.add(new FreeBlocks(this.size, this.initialSize +
                this.size));
        this.freeSpace.getCurrent().getData().setOccupied(false);
        this.size += this.initialSize;
        this.mergeFreeBlocks();
    }

    /**
     * Getter method for the initialSize.
     * @return The initalSize of the bytes array.
     */
    public int getInitialSize() {
        return this.initialSize;
    }

    /**
     * Gets the serialization number for the object.
     * @param obj The object to be serialized.
     * @return The serialization number of the object.
     * @throws IOException
     */
    public static int sizeof(Object obj) throws IOException {
        byte[] temp;
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = 
                new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        temp = new byte[byteOutputStream.toByteArray().length - 6];
        return temp.length;
    }

    /**
     * Gets the byte array representation of the object.
     * @param obj The object to be converted.
     * @return The byte array of the object.
     * @throws IOException
     */
    public static byte[] asByte(Object obj) throws IOException {
        byte[] temp;
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = 
                new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        temp = new byte[byteOutputStream.toByteArray().length - 6];
        for (int i = 6; i < byteOutputStream.toByteArray().length; i++) {
            temp[i - 6] = byteOutputStream.toByteArray()[i];
        }
        return temp;
    }

}
