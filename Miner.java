import java.util.List;
import java.util.Map;
import processing.core.*;

public abstract class Miner extends Mover
{
	private int resource_limit;
	private int resource_count;

	public Miner(String name, Point position, List<PImage> imgs, int animation_rate, int rate, int resource_limit, int resource_count) {
		super(name, position, imgs, animation_rate, rate);
		this.resource_limit = resource_limit;
		this.resource_count = resource_count;
	}
	
	public int getResourceCount()
	{
		return this.resource_count;
	}
	
	public void setResourceCount(int n)
	{
		this.resource_count = n;
	}
	
	public int getResourceLimit()
	{
		return this.resource_limit;
	}

	abstract Action createMinerSpecificAction(WorldModel world, Map<String, List<PImage>> i_store);
	
	public Action createMinerAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		return this.createMinerSpecificAction(world, i_store);
	}
	
	public void scheduleMiner(WorldModel world, long ticks, Map<String, List<PImage>> i_store)
	{
		this.scheduleAction(world,  this.createMinerAction(world, i_store), ticks + this.getRate());
		this.scheduleAnimation(world);
	}
}
