import java.util.List;


public class PointBooleanPair
{
	private List<Point> point;
	private boolean boo;
	
	public PointBooleanPair(List<Point> point, boolean boo)
	{
		this.point = point;
		this.boo = boo;
	}
	
	public List<Point> getPoint()
	{
		return this.point;
	}
	
	public boolean getBoolean()
	{
		return this.boo;
	}

}
