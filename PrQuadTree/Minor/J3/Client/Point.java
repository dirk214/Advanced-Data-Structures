package Minor.J3.Client;

import Minor.J3.DS.Compare2D;
import Minor.J3.DS.Direction;

public class Point implements Compare2D<Point> 
{
	private long xcoord;
	private long ycoord;
	
	public Point() 
	{
		xcoord = 0;
		ycoord = 0;
	}
	public Point(long x, long y) 
	{
		xcoord = x;
		ycoord = y;
	}
	public long getX()
	{
		return xcoord;
	}
	public long getY() 
	{
		return ycoord;
	}
	
	public Direction directionFrom(long X, long Y) 
	{		
		return Direction.NOQUADRANT;
	}
	
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi) 
	{
	    return Direction.NOQUADRANT;
	}
	
	public boolean inBox(double xLo, double xHi, double yLo, double yHi) 
	{		
		final long x = this.getX(), y = this.getY();
		
		if ( x < xLo || x > xHi || y < yLo || y > yHi ) return false;
		return true;
	}
	
	public String toString() 
	{		
		return new String("(" + xcoord + ", " + ycoord + ")");
	}
	
	public boolean equals(Object o) 
	{
		return false;
	}
}
