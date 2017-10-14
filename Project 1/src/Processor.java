import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * This is the main organizer of commands and determines what 
 *  to do with the commands.
 * @author Andrew Herbert
 * @version Jul 8, 2017
 */
public class Processor {
    
    /**
     * This is the commands file.
     */
    File commands;
    
    /**
     * This is the scanner used to parse the commands file.
     */
    private Scanner scanner;
    
    /**
     * This is the hashTable used to store handles.
     */
    private HashTable hasher;
    
    /**
     * A string representation of the currentLine of the scanner.
     */
    private String currentLine;
    
    /**
     * This is the constructor which sets up the class fields.
     * @param commands Is the file we will parse.
     * @param hasher Is the hashTable that will Store the handles to records.
     */
    public Processor(File commands, HashTable hasher) {
        this.commands = commands;
        this.hasher = hasher;
        try {
            this.scanner = new Scanner(this.commands);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This is the main method that will parse the commands file and 
     *  decide what to do with the commands within it.
     */
    public void process() {
        try {
            while (scanner.hasNextLine()) {
                this.currentLine = scanner.nextLine();
                String[] command = this.currentLine.split("\\s+");
                if (currentLine.contains("insert")) {
                    if (command[1] == null || command[2] == null || 
                            command[3] == null)
                        continue;
                    Record record = new Record(Integer.parseInt(command[1]),
                            Integer.parseInt(command[2]), command[3]);
                    Handle handle = hasher.memory.insert(record, 
                            MemoryManager.asByte(record));
                    if (hasher.insert(handle, hasher.hash(handle)))
                    System.out.println("Inserted Record of size "
                            + MemoryManager.sizeof(record) + " "
                                    + "in memory pool location "
                            + handle.getLocation()
                            + " and hashtable location " 
                            + handle.getIndex());
                    else
                        System.out.println("No record inserted."
                                + " Record size is too large.");
                }                
                else if (currentLine.contains("remove")) {
                    String rName = command[1];
                    int spot = this.hasher.findName(rName);
                    Handle temp = this.hasher.remove(spot);
                    byte[] temporary = this.hasher.memory.remove(temp);
                    if (temporary != null)
                        System.out.println("Removed " + rName 
                                + " at memory position "
                                + temp.getLocation() + ".");
                    else
                        System.out.println("Record not removed."
                                + "  No record found.");
                }
                else if (currentLine.contains("print")) {
                    if (command.length > 1) {
                        String pName = command[1];
                        String temp = "Record could not be printed."
                                + "  It does not exist.";
                        if (hasher.findName(pName) != -1)
                            temp = hasher.printSpecificRecord(hasher.
                                    getTable()[hasher.findName(pName)]);
                        System.out.println(temp);
                    }
                    else {
                        System.out.println(hasher.printTable());
                    }
                }
                else if (this.currentLine.isEmpty())
                    continue;                
                else
                    System.out.println("ERROR! Unrecognized command."
                            + "  Check input..." + this.currentLine);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
