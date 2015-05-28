import java.util.ArrayList;
import java.util.List;

import processing.core.*;


public abstract class Entity extends WorldObject
{
	private Point position;

	private List<Point> path = new ArrayList<Point>();
	private List<Point> searched = new ArrayList<Point>();
	
	public Entity (String name, Point position, List<PImage> imgs)
	{
		super(name, imgs);
		this.position = position;
	}
	
	public Point getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Point new_position)
	{
		this.position = new_position;
	}
	
	public List<Point> getPath()
	{
		return this.path;
	}
	
	public void setPath(List<Point> new_path)
	{
		this.path = new_path;
	}
	
	public List<Point> getSearched()
	{
		return this.searched;
	}
	
	public void setSearched(List<Point> new_searched)
	{
		this.searched = new_searched;
	}
}