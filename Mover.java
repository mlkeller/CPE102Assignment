import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.*;


public abstract class Mover extends AnimatedActor
{
	private int rate;
	
	public Mover(String name, Point position, List<PImage> imgs, int animation_rate, int rate) {
		super(name, position, imgs, animation_rate);
		this.rate = rate;
	}
	
	public int getRate()
	{
		return this.rate;
	}
	
	public List<Point> AStarPath(Point start, Point goal, WorldModel world)
	{
		OrderedList<Point> open_set = new OrderedList<Point>();
		List<Point> been_in_open_set = new ArrayList<Point>(); //for drawing
		List<Point> closed_set = new ArrayList<Point>();
		Map<Point, PathNode> pathnode_map = new HashMap<Point, PathNode>();
		
		int f_start = this.heuristicCostEstimate(start, goal);
		pathnode_map.put(start, new PathNode(null, 0, f_start));
		open_set.insert(start, f_start);
		been_in_open_set.add(start);
		
		while (open_set.getSize() > 0 && open_set.getSize() <= 1200)
		{
			Point current = open_set.pop();
			if (current.equals(goal))
			{
				List<Point> path = this.makePath(pathnode_map, goal);
				path.remove(0);
				this.setPath(path);
				this.setSearched(been_in_open_set);
				return path;
			}
			
			closed_set.add(current);
			
			List<Point> neighbor_points = validNeighbors(current, closed_set, world);

			for (Point neighbor : neighbor_points)
			{
				if ((world.isOccupied(neighbor)) && !(neighbor.equals(goal)))
				{
					if (OreBlob.class.isInstance(world.getTileOccupant(start)) && !(Ore.class.isInstance(world.getTileOccupant(neighbor))))
					{
						continue;
					}
					else if (!(OreBlob.class.isInstance(world.getTileOccupant(start))))
					{
						continue;
					}
				}
				
				int tentative_g_score = pathnode_map.get(current).getGScore() + gAdditional(world, neighbor);
				
				if (!(open_set.contains(neighbor)) || (tentative_g_score < pathnode_map.get(neighbor).getGScore()))
				{
					int temp_f_score = tentative_g_score + this.heuristicCostEstimate(neighbor, goal);
					pathnode_map.put(neighbor, new PathNode(current, tentative_g_score, temp_f_score));
					
					if (!(open_set.contains(neighbor)))
					{
						open_set.insert(neighbor, temp_f_score);
						been_in_open_set.add(neighbor);
					}
				}
			}
		}
		return null;
	}
	
	private int heuristicCostEstimate(Point node_location, Point goal)
	{
		return (Math.abs(node_location.getX() - goal.getX()) +
				Math.abs(node_location.getY() - goal.getY()));
	}
	
	private int gAdditional(WorldModel world, Point next_step)
	{
		//we had the thing checking to see if it was water and having a cost of 4
		//but it was overwriting the water, so we removed it
		//consider adding back in list
		return 1;
	}
	
	private List<Point> validNeighbors(Point current, List<Point> closed_set, WorldModel world)
	{
		List<Point> neighbor_points = new ArrayList<Point>();
		
		Point right = new Point(current.getX() + 1, current.getY());
		if (world.withinBounds(right) && !(closed_set.contains(right)))
		{
			neighbor_points.add(right);
		}
		Point up = new Point(current.getX(), current.getY() + 1);
		if (world.withinBounds(up) && !(closed_set.contains(up)))
		{
			neighbor_points.add(up);
		}
		Point left = new Point(current.getX() - 1, current.getY());
		if (world.withinBounds(left) && !(closed_set.contains(left)))
		{
			neighbor_points.add(left);
		}
		Point down = new Point(current.getX(), current.getY() - 1);
		if (world.withinBounds(down) && !(closed_set.contains(down)))
		{
			neighbor_points.add(down);
		}
		return neighbor_points;
	}
	
	private List<Point> makePath(Map<Point, PathNode> path_map, Point current)
	{
		List<Point> total_path = new ArrayList<Point>();
		total_path.add(current);
		
		while (this.pointInMap(current, path_map))
		{
			current = this.getFromMap(current, path_map);
			total_path.add(0, current);
		}
		total_path.remove(0);
		return total_path;
	}
	
	private boolean pointInMap(Point pt, Map<Point, PathNode> path_map)
	{
		for (Point key : path_map.keySet())
		{
			if (key != null && pt != null && pt.equals(key))
			{
				return true;
			}
		}
		return false;
	}
	
	private Point getFromMap(Point key_pt, Map<Point, PathNode> path_map)
	{
		for (Point key : path_map.keySet())
		{
			if (key.equals(key_pt))
			{
				return path_map.get(key).getCameFrom();
			}
		}
		return null;
	}
}