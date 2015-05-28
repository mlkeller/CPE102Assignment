
public class Point {

	private int x;
	private int y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object that)
	{
		if (!(that instanceof Point))
		{
			return false;
		}
		
		if (that == this)
		{
			return true;
		}
		
		Point that_pt = (Point)that;
		return ((this.x == that_pt.getX()) && (this.y == that_pt.getY()));
	}
	
	public int hashCode()
	{
		return x + y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
}
