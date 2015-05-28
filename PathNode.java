
public class PathNode
{
	private Point came_from;
	private int g_score;
	private int f_score;
	
	public PathNode(Point came_from, int g_score, int f_score)
	{
		this.came_from = came_from;
		this.g_score = g_score;
		this.f_score = f_score;
	}
	
	public Point getCameFrom()
	{
		return this.came_from;
	}
	
	public int getGScore()
	{
		return this.g_score;
	}
	
	public int getFScore()
	{
		return this.f_score;
	}
}
