import java.util.ArrayList;

/**
 * A binTree implementation to store records.
 * @author Andrew Herbert
 * @version Jul 26, 2017
 */
public class BinTree {

    /**
     * The root node of a quadTree.
     */
    BinNode root;

    /**
     * The x boundary of the world.
     */
    private long xMax;

    /**
     * The y boundary of the world.
     */
    private long yMax;

    /**
     * A class boolean used for saving a boolean across 
     *  helper methods and the calling method.
     */
    private boolean found;

    /**
     * A class arrayList usEd for saving a boolean across helper method 
     *  and the calling method find which returns an arrayList with all 
     *  the values within a region of the quadTree.
     */
    private ArrayList<Record> arrayList;

    /**
     * A class integer used to count how many elements 
     *  are in the quadTree at a given time.
     */
    private int count;

    /**
     * UsEd to print the tree.
     */
    private String out = "";

    /**
     * An integer used to count the nodes traversEd in 
     *  the find region sEarch.
     */
    private int findint;

    /**
     * The flyWeight used as a storage saver.
     */
    private BinLeaf fly;

    /**
     * Field for the find method.
     */
    private Record finder;

    /**
     * Helps with the insert method.
     */
    private int inserter;

    /**
     * BinTree constructor.
     */
    public BinTree() {
        this.root = null;
        this.count = 0;
        this.xMax = 1023;
        this.yMax = 1023;
        fly = new BinLeaf();
        inserter = 0;
    }

    /**
     * A parent class with common fields of both children node types.
     * @author Andrew Herbert
     * @version Jul 26, 2017
     */
    abstract class BinNode {

    }

    /**
     * A child node of BinNode which only holds data and has no children.
     * @author Andrew Herbert
     * @version Jul 26, 2017
     */
    class BinLeaf extends BinNode {

        /**
         * A arrayList which holds the leafs data.
         */
        public ArrayList<Record> elements;

        /**
         * Default leaf constructor.
         */
        public BinLeaf() {
            super();
            elements = new ArrayList<Record>(1);
        }

        /**
         * Leaf constructor with a data object to be stored.
         * @param data The data to be stored in the leaf.
         */
        public BinLeaf(Record data) {
            super();
            elements = new ArrayList<Record>(1);
            elements.add(data);
        }


    }

    /**
     * Is a child node which holds no data but 4 child nodes. 
     * @author Andrew Herbert
     * @version 07/26/2017
     */
    class BinInternal extends BinNode {

        /**
         * The left child of the node.
         */
        public BinNode one = fly;

        /**
         * The right child of the node.
         */
        public BinNode two = fly;

        /**
         * The inserter type.
         */
        public int inserter;

        /**
         * Default constructor for the internal node.
         * @param inserter Is the inserter type.
         */
        public BinInternal(int inserter) {
            super();
            this.inserter = inserter;
        }

    }

    /**
     * Attempts to insErt the element into the binTree. 
     * @param elem The element to insErt.
     * @return If the element was insErted.
     */
    public boolean insert(Record elem) {
        if (elem == null)
            return false;
        final long x = elem.x;
        final long y = elem.y;
        if (x > this.xMax || y > this.yMax || x < 0 || y < 0)
            return false;
        found = false;
        root = insert(root, elem, 0, xMax, 0, yMax, 0);
        this.count++;
        return found;
    }

    /**
     * sEarches for an element in the binTree.
     * @param elem The element to sEarch for.
     * @return The reference to the element or null if not found.
     */
    public Record find(Record elem) {
        this.finder = null;
        find(elem, root);
        return finder;
    }

    /**
     * Attempts to delete an element from the binTree.  
     * @param elem The element to delete.
     * @return If the element was deleted.
     */
    public boolean delete(Record elem) {
        if (elem == null)
            return false;
        final long x = elem.x;
        final long y = elem.y;
        if (x > this.xMax || y > this.yMax || x < 0 || y < 0)
            return false;
        found = false;
        root = delete(root, elem);
        if (found == true)
            count--;
        if (count == 0) {
            root = null;
        }
        return found;
    }

    /**
     * sEarches for all records in the given area.
     * @param xLo The lower x bound.
     * @param xHi The upper x bound.
     * @param yLo The lower y bound.
     * @param yHi The upper y bound.
     * @return The list of records within the area.
     */
    public ArrayList<Record> find(long xLo, long xHi, long yLo, long yHi) {
        this.findint = 0;
        arrayList = new ArrayList<Record>(count);
        if (xLo < 0 && xHi < 0 && yLo < 0 && yHi < 0)
            return null;
        if (xLo > 1023 && xHi > 1023 && yLo > 1023 && yHi > 1023)
            return null;
        if (xLo < 0) 
            xLo = 0;
        if (xHi > 1023) 
            xHi = 1023;
        if (yLo < 0) 
            yLo = 0;
        if (yHi > 1023) 
            yHi = 1023;
        find(root, xLo, xHi, yLo, yHi);
        return arrayList;
    }    

    /**
     * A recursive helper method for find.
     * @param elem The element to sEarch for.
     * @param node The node currently being checked in the traversal.
     */
    private void find(Record elem, BinNode node) {
        if (node instanceof BinTree.BinLeaf) {
            BinLeaf leaf = (BinLeaf) node;
            if (leaf != fly && leaf.elements.get(0).x == elem.x && 
                    leaf.elements.get(0).y == elem.y) {
                finder = leaf.elements.get(0);
            }
        }
        else if (node instanceof BinTree.BinInternal) {
            BinInternal internal = (BinInternal) node;
            if (internal.one != null) 
                find(elem, internal.one);
            if (internal.two != null)
                find(elem,  internal.two);
        }
    }

    /**
     * A recursive helper method which insErts a non repeated node
     *  into the tree.
     * @param node is the node we are adding to.
     * @param elem is the value we are adding to node.
     * @param xMi Is the lower x bound.
     * @param xMa Is the upper x bound.
     * @param yMi Is the lower y bound.
     * @param yMa Is the upper y bound.
     * @return the node with the additional data value.
     */
    private BinNode insert(BinNode node, Record elem,
            long xMi, long xMa, long yMi, long yMa, int level) {
        if (node == null || node == fly || this.count == 0) {
            node = new BinLeaf(elem);
            found = true;
        }
        else if (node instanceof BinTree.BinLeaf) {
            BinLeaf leaf = (BinLeaf) node;
            if (leaf.elements.get(0) != elem) {
                if (inserter == 0) {
                    node = new BinInternal(0);
                    inserter = 1;
                }
                else {
                    node = new BinInternal(1);
                    inserter = 0;
                }
                level += 1;
                insert(node, leaf.elements.get(0), xMi, xMa, yMi, yMa, level);
                insert(node, elem, xMi, xMa, yMi, yMa, level);
            }
            else
                return node;
        }
        else if (node instanceof BinTree.BinInternal) {
            BinInternal internal = (BinInternal) node;
            if (internal.inserter == 1) {
                int yMid = (int) ((yMi + yMa) / 2);
                if (elem.y <= yMid) {
                    internal.one = insert(internal.one,
                            elem, xMi, xMa, yMi, yMid, level); 
                }
                else {
                    internal.two = insert(internal.two, 
                            elem, xMi, xMa, yMid, yMa, level); 
                }
            }
            else {
                int xMid = (int) ((xMi + xMa) / 2);
                if (elem.x <= xMid) {
                    internal.one = insert(internal.one,
                            elem, xMi, xMid, yMi, yMa, level);
                }
                else {
                    internal.two = insert(internal.two, 
                            elem, xMid, xMa, yMi, yMa, level);
                }
            }
        }
        return node;
    }

    /**
     * A recursive helper method for the prQuadTree's remove method.
     * @param node node we are checking for to remove from.
     * @param elem element we want to remove.
     * @return node without the element in it.
     */
    private BinNode delete(BinNode node, Record elem) {
        if (node == null)
            return null;
        else if (node instanceof BinTree.BinLeaf) {
            BinLeaf leaf = (BinLeaf) node;
            if (leaf != fly && leaf.elements.get(0).x == elem.x && 
                    leaf.elements.get(0).y == elem.y) {
                found = true;
                return fly;
            }
            else 
                return node;
        }
        else if (node instanceof BinTree.BinInternal) {
            BinInternal internal = (BinInternal) node;
            if (internal.one != this.fly) {
                internal.one = delete(internal.one, elem);
            }
            if (internal.two != this.fly) {
                internal.two = delete(internal.two, elem);
            }
            if (internal.one == fly) {
                if (internal.two instanceof BinTree.BinLeaf) {
                    node = internal.two;
                }
            }
            if (internal.two == fly) {
                if (internal.one instanceof BinTree.BinLeaf) {
                    node = internal.one;
                }
            }
            return node;
        }
        else
            return null;
    }

    /**
     * A recursive helper method for the find in region method.
     * @param node Is the node currently being traversEd.
     * @param xLo Is the lower x bound.
     * @param xHi Is the upper x bound.
     * @param yLo is the lower y bound.
     * @param yHi is the upper y bound.
     */
    private void find(BinNode node, long xLo, long xHi, 
            long yLo, long yHi) {
        if (node instanceof BinTree.BinInternal) {
            this.findint++;
            BinInternal internal = (BinInternal) node;
            if (internal.one != null) {
                find(internal.one, xLo, xHi, yLo, yHi); 
            }
            if (internal.two != null) {
                find(internal.two, xLo, xHi, yLo, yHi); 
            }
        }
        else if (node instanceof BinTree.BinLeaf) {
            this.findint++;
            BinLeaf leaf = (BinLeaf) node;
            if (leaf != fly && leaf.elements.get(0).x >= xLo 
                    && leaf.elements.get(0).x <= xHi 
                    && leaf.elements.get(0).y >= yLo 
                    && leaf.elements.get(0).y <= yHi)
                arrayList.add(leaf.elements.get(0));
        }
    }

    /**
     * Prints the Tree in String form.
     * @return The String of the printed Tree.
     */
    public String printTree() {
        this.out = "";
        if (root == null)
            return "E, 0, 0, 1024, 1024\n";
        else
            printTreeHelper(root, 0, 1024, 0, 1024, 0);
        return out;
    }

    /**
     * A helper method for the print function.
     * @param sRoot The node we start printing from.
     * @param xMi Is the lower x bound.
     * @param xMa Is the upper x bound. 
     * @param yMi Is the lower y bound.
     * @param yMa Is the upper y bound.
     * @param level Is the level in the tree the traversal is on.
     * @return The Full string.
     */
    private String printTreeHelper(BinNode sRoot, int xMi, int xMa,
            int yMi, int yMa, int level) {
        for (int i = 0; i < level; i++) {
            out += "  ";
        }
        int xS = (int) ((xMi + xMa) / 2);
        int yS = (int) ((yMi + yMa) / 2);
        if (sRoot instanceof BinTree.BinInternal) {
            BinInternal internal = (BinInternal) sRoot;
            this.out += "I, " + xMi + ", " + yMi + ", " +
                    (xMa - xMi) + ", " + (yMa - yMi) + "\n";
            if (level % 2 == 0) {
                level += 1;
                printTreeHelper(internal.one, xMi, xMi + xS, yMi,
                        yMa, level);
                printTreeHelper(internal.two, xMi + xS, xMa, yMi,
                        yMa, level);
            }
            else {
                level += 1;
                printTreeHelper(internal.one, xMi, xMa, yMi,
                        yMi + yS, level);
                printTreeHelper(internal.two, xMi, xMa, yMi + yS,
                        yMa, level);
            }
        }
        if (sRoot instanceof BinTree.BinLeaf) {
            BinLeaf leaf = (BinLeaf) sRoot;
            if (level % 2 == 0) {
                if (leaf == fly) {
                    out += "E, " + xMi + ", " + yMi + ", " + (xMa - xMi) + ", "
                            + (yMa - yMi) + "\n";
                }
                else {
                    Record temp = leaf.elements.get(0);
                    out +=  temp.cityname + ", " + temp.x + ", " +
                            temp.y + "\n";
                }
            }
            else {
                if (leaf == fly) {
                    out += "E, " + (xMi) + ", " + yMi + ", "
                            + (xMa - xMi) + ", " + (yMa - yMi) + "\n";
                }
                else {
                    Record temp = leaf.elements.get(0);
                    out += temp.cityname + ", " + temp.x + ", " +
                            temp.y + "\n";
                }
            }
        }

        return this.out;
    }

    /**
     * Gets the findInt field.
     * @return The findInt field.
     */
    public int getFindInt() {
        return this.findint;
    }

}