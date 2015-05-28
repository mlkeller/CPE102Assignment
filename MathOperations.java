
public class MathOperations
{
	public static int sign(int x)
	{
		if (x < 0)
		{
			return -1;
		}
		else if (x > 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public static boolean adjacent(Point pt1, Point pt2)
	{
		return ((pt1.getX() == pt2.getX()) && (Math.abs(pt1.getY() - pt2.getY()) == 1)) ||
			   ((pt1.getY() == pt2.getY()) && (Math.abs(pt1.getX() - pt2.getX()) == 1));
	}
	
	public static double distanceSquared(Point pt1, Point pt2)
	{
		return Math.pow(pt1.getX() - pt2.getX(), 2) + Math.pow(pt1.getY() - pt2.getY(), 2);
	}
}
