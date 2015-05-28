
public class Rectangle 
{
	private int left;
	private int top;
	private int width;
	private int height;
	
	public Rectangle(int left, int top, int width, int height)
	{
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}
	
	public int getLeft()
	{
		return this.left;
	}
	
	public int getTop()
	{
		return this.top;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public boolean pointInRectangle(Point pt)
	{
		return ((pt.getX() >= this.left) && (pt.getX() < (this.left + this.width)) &&
				(pt.getY() >= this.top) && (pt.getY() < (this.top + this.height)));
	}
}
