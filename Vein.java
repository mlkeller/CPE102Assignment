import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import processing.core.*;

public class Vein extends Actionable
{
	private int rate;
	private int resource_distance = 1;
	
	public Vein(String name, Point position, List<PImage> imgs, int rate)
	{
		super(name, position, imgs);
		this.rate = rate;
	}
	public Vein(String name, Point position, List<PImage> imgs, int rate, int resource_distance)
	{
		super(name, position, imgs);
		this.rate = rate;
		this.resource_distance = resource_distance;
	}
	
	public int getRate()
	{
		return this.rate;
	}

	public int getResourceDistance()
	{
		return this.resource_distance;
	}
	
	public Action createVeinAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		a[0] = (long current_ticks) ->
		{
			List<Point> tiles = new ArrayList<Point>();
			
			this.removePendingAction(a[0]);
			
			Point open_pt = world.findOpenAround(this.getPosition(), this.getResourceDistance());
			if (open_pt != null)
			{
				Ore ore = world.createOre("ore - " + this.getName() + " - " + Long.toString(current_ticks),
										   open_pt, current_ticks, i_store);
				world.addEntity(ore);
				tiles.add(open_pt);
			}
			
			this.scheduleAction(world, this.createVeinAction(world, i_store), current_ticks + this.getRate());
			return tiles;
		};
		
		return a[0];
	}
	
	public void scheduleVein(WorldModel world, long ticks, Map<String, List<PImage>> i_store)
	{
		this.scheduleAction(world, this.createVeinAction(world, i_store), ticks + this.getRate());
	}
}
