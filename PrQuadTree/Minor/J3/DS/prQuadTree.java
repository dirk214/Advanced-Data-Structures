import java.util.ArrayList;

public class prQuadTree<T extends Compare2D<? super T>>
{		
    /**
     * The root node of a quadTree.
     */
    prQuadNode root;
    
    /**
     * The bounds of the quadTree.
     */
    final long xMin, xMax, yMin, yMax;
    
    /**
     * A class boolean used for saving a boolean across helper methods and the calling method.
     */
    private boolean found;
    
    /**
     * A class arrayList used for saving a boolean across helper method and the calling method find which returns an arrayList with all the values within a region of the quadTree.
     */
    private ArrayList<T> arrayList;
    
    /**
     * A class integer used to count how many elements are in the quadTree at a given time.
     */
    private int count;
    
    // Initialize quadTree to empty state.
   	public prQuadTree(long xMin, long xMax, long yMin, long yMax){
   		this.root = null;
   		count = 0;
   		this.xMin = xMin; 
   		this.xMax = xMax; 
   		this.yMin = yMin; 
   		this.yMax = yMax;
   	}
    
   	/*
     * A parent class with common fields of both children node types.
     */
    abstract class prQuadNode 
	{
    	final Box bounds;
    	
    	protected prQuadNode(long xMin, long xMax, long yMin, long yMax) {
    		this.bounds = new Box(xMin, xMax, yMin, yMax);
    	}
    	
    	public abstract int children();
    	public abstract prQuadNode child();
	}
    
    /**
     * @author Andrew
     * A child node which only holds a data type.
     */
	class prQuadLeaf extends prQuadNode
	{	
		public ArrayList<T> Elements;
		
		public prQuadLeaf() {
		    super(0,0,0,0);
		    Elements = new ArrayList<T>(1);
		}
		
		public prQuadLeaf(T data) {
		    super(0,0,0,0);
            Elements = new ArrayList<T>(1);
            Elements.add(data);
		}
		
		public prQuadLeaf(T data, long xMin, long xMax, long yMin, long yMax) {
			super(xMin, xMax, yMin, yMax);
			
		    this.Elements = new ArrayList<T>(1);
		    this.Elements.add(data);
		}
		
		@Override
		public int children(){
		    return 0;
		}
		
		@Override
		public prQuadNode child(){
		    return null;
		}
	}
	
	/**
	 * Is a child node which holds no data but 4 child nodes. 
	 * @author Andrew
	 *
	 */
	class prQuadInternal extends prQuadNode
	{
		public prQuadNode NW, NE, SW, SE;
		
		public prQuadInternal() {
		    super(0,0,0,0);
		}
		
		public prQuadInternal(long xMin, long xMax, long yMin, long yMax) {
			super(xMin, xMax, yMin, yMax);			
  	   	}

       	@Override
       	public int children(){
       	    int count = 0;
       	    
       	    if(this.NW != null)
       	        count++;
       	    if(this.NE != null)
       	        count++;
       	    if(this.SW != null)
       	        count++;
       	    if(this.SE != null)
       	        count++;
       	    
       	    return count;
       	}
       	
       	@Override
       	public prQuadNode child(){
       	 if(this.NW != null)
             return this.NW;
       	 else if(this.NE != null)
             return this.NE;
         else if(this.SW != null)
             return this.SW;
         else if(this.SE != null)
             return this.SE;
         else
             return null;
       	}
	}
	
	/**
	 * A class which essentially forms a box with 4 coordinates and makes it easier to reference the sides of any boxes.
	 * @author Andrew
	 *
	 */
	class Box
	{
	    public final long xMin, xMax, yMin, yMax, xCenter, yCenter;

	    public Box(long xMin, long xMax, long yMin, long yMax) {
	        this.xMin = Math.min(xMin, xMax);
	        this.xMax = Math.max(xMin, xMax);
	        this.yMin = Math.min(yMin, yMax);
	        this.yMax = Math.max(yMin, yMax);
	        this.xCenter = (xMin + xMax) / 2;
	        this.yCenter = (yMin + yMax) / 2;
	    }

	    public boolean contains(long x, long y) {
	        return (x > this.xMin && x < this.xMax && 
	        		y > this.yMin && y < this.yMax);
	    }
	}
    
   	// Pre:   elem != null
   	// Post:  If elem lies within the tree's region, and elem is not already 
   	//        present in the tree, elem has been inserted into the tree.
  	// Return true iff elem is inserted into the tree. 
   	public boolean insert(T elem) 
   	{
   		if (elem == null) return false;
   		
   		final long x = elem.getX(), y = elem.getY();
   		
   		// Out-of-Bounds
   		if ( x < this.xMin || x > this.xMax || y < this.yMin || y > this.yMax ) return false;
   		
   		found = false;
   		root = insert(root, elem, new Box(this.xMin, this.xMax, this.yMin, this.yMax));
   		
   		if(found)
   		    count++;
   		
   		return found;
   	}

   	// Pre:  elem != null
   	// Returns reference to an element x within the tree such that 
   	// elem.equals(x)is true, provided such a matching element occurs within
   	// the tree; returns null otherwise.
   	public T find(T Elem) {		
   	    return find(Elem, root);
   	}

   	// Pre:  elem != null
   	// Post: If elem lies in the tree's region, and a matching element occurs
   	//       in the tree, then that element has been removed.
   	// Returns true iff a matching element has been removed from the tree.   
   	public boolean delete(T Elem) {		
   	    if(Elem == null) return false;
   	    
   	    final long x = Elem.getX(), y = Elem.getY();
        if(x < this.xMin || x > this.xMax || y < this.yMin || y > this.yMax) return false;
        
        found = false;
        root = delete(root, Elem);
        
        if(found == true)
            count--;
        
        return found;
   	}

   	// Pre:  xLo < xHi and yLo < yHi
    // Returns a collection of (references to) all elements x such that x is 
    //in the tree and x lies at coordinates within the defined rectangular 
    // region, including the boundary of the region.
    public ArrayList<T> find(long xLo, long xHi, long yLo, long yHi){       
        arrayList = new ArrayList<T>(count);
        find(root, xLo, xHi, yLo, yHi);
        
        return arrayList;
    }    
   	
    @SuppressWarnings({ "unchecked" })
    private T find(T Elem, prQuadNode node){
   	   if(node == null) return null;
   	   
   	   if(node instanceof prQuadTree.prQuadLeaf){
   	       prQuadLeaf leaf = (prQuadLeaf) node;
   	       
   	       if(leaf.Elements.get(0).getX() == Elem.getX() && leaf.Elements.get(0).getY() == Elem.getY())
   	           return leaf.Elements.get(0);
   	       else
   	           return null;
   	   }
   	   
   	   if(node instanceof prQuadTree.prQuadInternal){
   		   prQuadInternal internal = (prQuadInternal) node;
   		   
   	        if(internal.NE != null)
   	            find(Elem, internal.NE);
   	        if(internal.NW != null)
   	            find(Elem, internal.NW);
   	        if(internal.SE != null)
   	            find(Elem, internal.SE);
   	        if(internal.SW != null)
   	            find(Elem, internal.SW);
   	   }
   	   
   	   return null;
   	}
   	
    /**
     * A helper method which inserts a non repeated node into the tree.
     * @param node is the node we are adding to.
     * @param elem is the value we are adding to node.
     * @param bounds is the boundary of the node we are inserting to.
     * @return the node with the additional data value.
     */
    @SuppressWarnings("unchecked")
	private prQuadNode insert(prQuadNode node, T elem, Box bounds) {
        if(node == null) {            
            node = new prQuadLeaf(elem, bounds.xMin, bounds.xMax, bounds.yMin, bounds.yMax);
            found = true;
        }        
        else if(node instanceof prQuadTree.prQuadLeaf) {
            prQuadLeaf leaf = (prQuadLeaf) node;
            
            if(leaf.Elements.get(0) != elem) {
                node = new prQuadInternal(bounds.xMin, bounds.xMax, bounds.yMin, bounds.yMax);
                insert(node, leaf.Elements.get(0), bounds);
                insert(node, elem, bounds);
            }
            else
                return node;
        }
        else if(node instanceof prQuadTree.prQuadInternal) {
        	prQuadInternal internal = (prQuadInternal) node;
        	
            if(elem.getX() > node.bounds.xMin && elem.getX() < node.bounds.xCenter && elem.getY() > node.bounds.yCenter && elem.getY() < node.bounds.yMax)
                internal.NW = insert(internal.NW, elem, new Box(node.bounds.xMin, node.bounds.xCenter, node.bounds.yCenter, node.bounds.yMax));                                                      
            else if(elem.getX() > node.bounds.xCenter && elem.getX() < node.bounds.xMax && elem.getY() > node.bounds.yCenter && elem.getY() < node.bounds.yMax) 
                internal.NE = insert(internal.NE, elem, new Box(node.bounds.xCenter, node.bounds.xMax, node.bounds.yCenter, node.bounds.yMax));
            else if(elem.getX() > node.bounds.xMin && elem.getX() < node.bounds.xCenter && elem.getY() < node.bounds.yCenter && elem.getY() > node.bounds.yMin)
                internal.SW = insert(internal.SW, elem, new Box(node.bounds.xMin, node.bounds.xCenter, node.bounds.yMin, node.bounds.yCenter));
            else if(elem.getX() > node.bounds.xCenter && elem.getX() < node.bounds.xMax && elem.getY() < node.bounds.yCenter && elem.getY() > node.bounds.yMin) 
                internal.SE = insert(internal.SE, elem, new Box(node.bounds.xCenter, node.bounds.xMax, node.bounds.yMin, node.bounds.yCenter));
        } 
        return node;
    }
    
    /**
     * A helper method for the prQuadTree's remove method.
     * @param node node we are checking for to remove from.
     * @param elem element we want to remove.
     * @return node without the element in it.
     */
    @SuppressWarnings("unchecked")
	private prQuadNode delete(prQuadNode node, T elem) {
        if(node == null)
            return null;
        
        else if(node instanceof prQuadTree.prQuadLeaf) {
            prQuadLeaf leaf = (prQuadLeaf) node;
            if(leaf.Elements.get(0).getX() == elem.getX() && leaf.Elements.get(0).getY() == elem.getY()) {
                found = true;
                return null;
            }
            else 
            	return node;
        }
        else if(node instanceof prQuadTree.prQuadInternal) {
        	prQuadInternal internal = (prQuadInternal) node;
        	
            if(internal.NW != null) {
                internal.NW = delete(internal.NW, elem);
                
                if(internal.NW != null && internal.NW.children() == 1)
                    internal.NW = internal.NW.child();
            }
            if(internal.NE != null) {
                internal.NE = delete(internal.NE, elem);
                
                if(internal.NE != null && internal.NE.children() == 1)
                    internal.NE = internal.NE.child();
            }
            if(internal.SW != null) {
                internal.SW = delete(internal.SW, elem);
                
                if(internal.SW != null && internal.SW.children() == 1)
                    internal.SW = internal.SW.child();
            }
            if(internal.SE != null) {
                internal.SE = delete(internal.SE, elem);
                
                if(internal.SE != null && internal.SE.children() == 1)
                    internal.SE = internal.SE.child();
            }
            return node;
        }
        else
            return null;
    }
    
    /*
     * Helper method for the find method.
     */
    @SuppressWarnings("unchecked")
	private void find(prQuadNode node, long xLo, long xHi, long yLo, long yHi){
        if(node instanceof prQuadTree.prQuadInternal) {
        	prQuadInternal internal = (prQuadInternal) node;
        	
            if(internal.NW != null)
                find(internal.NW, xLo, xHi, yLo, yHi);
            if(internal.NE != null)
                find(internal.NE, xLo, xHi, yLo, yHi);
            if(internal.SW != null)
                find(internal.SW, xLo, xHi, yLo, yHi);
            if(internal.SE != null)
                find(internal.SE, xLo, xHi, yLo, yHi);
        }
        else if(node instanceof prQuadTree.prQuadLeaf){
            prQuadLeaf leaf = (prQuadLeaf) node;
            
            if(leaf.Elements.get(0).getX() > xLo && leaf.Elements.get(0).getX() < xHi && leaf.Elements.get(0).getY() > yLo && leaf.Elements.get(0).getY() < yHi)
                arrayList.add(leaf.Elements.get(0));
        }
    }

}
//On my honor:
// 
//- I have not discussed the Java language code in my program with
//anyone other than my instructor or the teaching assistants
//assigned to this course.
//
//- I have not used Java language code obtained from another student,
//or any other unauthorized source, either modified or unmodified.
//
//- If any Java language code or documentation used in my program
//was obtained from another source, such as a text book or course
//notes, that has been clearly noted with a proper citation in
//the comments of my program.
//
//- I have not designed this program in such a way as to defeat or
//interfere with the normal operation of the Curator System.
//
//<Andrew Herbert>