import java.io.File;
import java.util.ArrayList;
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
     * BinTree dataType to store records and retrieve them faster.
     */
    private BinTree tree;

    /**
     * This is the constructor which sets up the class fields.
     * @param commands Is the file we will parse.
     * @param hasher Is the hashTable that will Store the handles to records.
     */
    public Processor(File commands, HashTable hasher) {
        this.commands = commands;
        this.hasher = hasher;
        this.tree = new BinTree();
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
                    System.out.println(">" + currentLine);
                    if (currentLine.substring(7).isEmpty()) {
                        continue;
                    }
                    Record record = new Record(Integer.parseInt(command[1]),
                            Integer.parseInt(command[2]), command[3]);
                    Handle handle = hasher.memory.insert(record, 
                            MemoryManager.asByte(record));
                    if (hasher.insert(handle, hasher.hash(handle))) {
                        System.out.println("Inserted Record of size "
                                + MemoryManager.sizeof(record) + " "
                                + "in memory pool location "
                                + handle.getLocation()
                                + " and hashtable location " 
                                + handle.getIndex() + "." + "\n");
                        tree.insert(record);
                    }
                    else
                        System.out.println("No record inserted."
                                + " Record size is too large." + "\n");
                }                
                else if (currentLine.contains("remove")) {
                    System.out.println(">" + currentLine);
                    if (command.length == 2) {
                        String rName = command[1];
                        int spot = this.hasher.findName(rName);
                        Handle temp = this.hasher.remove(spot);
                        byte[] temporary = this.hasher.memory.remove(temp);
                        if (temporary != null) {
                            System.out.println("Removed " + rName 
                                    + " at memory position "
                                    + temp.getLocation() + "." + "\n");
                            tree.delete(temp.getRecord());
                        }
                        else
                            System.out.println("Record not removed."
                                    + "  No record found." + "\n");
                    }
                    else {
                        int x = Integer.parseInt(command[1]);
                        int y = Integer.parseInt(command[2]);
                        Record temps = tree.find(new Record(x, y, ""));
                        if (tree.delete(temps)) {
                            String rName = temps.cityname;
                            int spot = this.hasher.findName(rName);
                            Handle temp = this.hasher.remove(spot);
                            byte[] temporary = this.hasher.memory.remove(temp);
                            if (temporary != null)
                                System.out.println("Removed " + rName 
                                        + " at memory position "
                                        + temp.getLocation() + "." + "\n");
                            else
                                System.out.println("Record not removed."
                                        + "  No record found." + "\n");
                        }
                        else 
                            System.out.println("Record not removed."
                                    + "  No record found." + "\n");
                    }
                }
                else if (currentLine.contains("find")) {
                    System.out.println(">" + currentLine);
                    String temp = "Record could not be printed."
                            + "  It does not exist." + "\n";
                    if (command.length == 2) {
                        String pName = command[1];
                        if (hasher.findName(pName) != -1)
                            temp = hasher.printSpecificRecordForFind(hasher.
                                    getTable()[hasher.findName(pName)]);
                        System.out.println(temp + "\n");
                    }
                    else if (command.length == 3) {
                        int x = Integer.parseInt(command[1]);
                        int y = Integer.parseInt(command[2]);
                        Record temps = tree.find(new Record(x, y, ""));
                        if (temps == null) {
                            System.out.println("Record could not be printed."
                                    + "  It does not exist.\n");
                        }
                        else 
                            System.out.println(temps.cityname + " "
                                    + temps.x + " " + temps.y + "\n");
                    }
                    else
                        System.out.println(temp);
                }
                else if (currentLine.contains("regionsearch")) {
                    System.out.println(">" + currentLine);
                    if (command.length == 5) {
                        int xLo = Integer.parseInt(command[1]);
                        int w = Integer.parseInt(command[3]) - 1;
                        int yLo = Integer.parseInt(command[2]);
                        int h = Integer.parseInt(command[4]) - 1;
                        ArrayList<Record> lister = 
                                tree.find(xLo , xLo + w, yLo, yLo + h);
                        if (lister == null) {
                            System.out.println("This area is outside of"
                                    + " the known world.\n");
                        }
                        else {
                            for (int i = 0; i < lister.size(); i++) {
                                System.out.println(lister.get(i).cityname + 
                                        " " + lister.get(i).x + " " 
                                        + lister.get(i).y);
                            }
                            System.out.println(tree.getFindInt() + 
                                    " Nodes visited.\n");
                        }
                    }
                    else 
                        System.out.println("Improper amount "
                                + "of input integers.\n");
                }
                else if (currentLine.contains("hashtable")) {
                    System.out.println(">" + currentLine);
                    System.out.println(this.hasher.printTable());
                }
                else if (currentLine.contains("freelist")) {
                    System.out.println(">" + currentLine);
                    System.out.println(this.hasher.
                            getMemoryPool().printFreeBlocks());
                }
                else if (currentLine.contains("print")) {
                    System.out.println(">" + currentLine);
                    //System.out.println("here");
                    System.out.println(tree.printTree());
                }
                else if (this.currentLine.isEmpty())
                    continue;                
                else {
                    System.out.println(">" + currentLine);
                    System.out.println("ERROR! Unrecognized command."
                            + "  Check input..." + this.currentLine
                            + "." + "\n");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
