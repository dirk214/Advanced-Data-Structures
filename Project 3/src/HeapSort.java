import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
/**
 * Main method for the program which sorts a file of binary data using a
 *  external heapSort sorting algorithm.
 * @author Andrew Herbert
 * @version Aug 2, 2017
 */
public class HeapSort {

    /**
     * The main method, begins the program.
     * @param args The arguments needed for the program to work.
     * @throws FileNotFoundException when the file isn't found.
     * @throws IOException when we have a different error other than the
     *  FileNotFoundException.
     */
    public static void main(String[] args) throws FileNotFoundException,
        IOException {
        if (args.length != 3) {
            System.out.println("Improper Input, terminating program");
        }
        else {
            resetStatWriter();
            String data = args[0];
            String stats = args[2];
            int numberOfBuffers = Integer.parseInt(args[1]);        
            StatWriter.dataFileName = data;
            File heapFile = new File(data);
            BufferPool pool = new BufferPool(heapFile, numberOfBuffers);
            MaxHeapSorter heapSorter = new MaxHeapSorter(pool, 
                    heapFile.length() / 4);
            long startTime = System.currentTimeMillis();
            heapSorter.sortTheHeap();
            pool.flushThePool();
            StatWriter.sortExecutionTime = 
                    System.currentTimeMillis() - startTime;
            File statsFile = new File(stats);
            statsFile.createNewFile();
            FileWriter fileWriter = new FileWriter(statsFile, true);
            BufferedWriter bufferedWriter = 
                    new BufferedWriter(fileWriter);
            bufferedWriter.write(StatWriter.properOutput());
            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println(pool.printFirstRecordOfEachBlock());
            System.out.println(StatWriter.properOutput());
            System.out.println("\n");
            resetStatWriter();
        }
    }

    /**
     * Resets the StatWriter fields after each run.
     */
    public static void resetStatWriter() {
        StatWriter.dataFileName = "";
        StatWriter.cacheHits = 0;
        StatWriter.cacheMisses = 0;
        StatWriter.diskReads = 0;
        StatWriter.diskWrites = 0;
        StatWriter.sortExecutionTime = 0;
    }
    
}

//On my honor:
//
//I have not used source code obtained from another student,
//or any other unauthorized source, either modified or unmodified.
//
//All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.