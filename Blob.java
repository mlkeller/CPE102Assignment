import java.util.List;
import java.util.Map;

import processing.core.PImage;


public abstract class Blob extends Mover
{
	public Blob(String name, Point position, List<PImage> imgs,
			    int animation_rate, int rate)
	{
		super(name, position, imgs, animation_rate, rate);
	}

	abstract Action createBlobSpecificAction(WorldModel world,  Map<String, List<PImage>> i_store);
	
	public Action createBlobAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		return this.createBlobSpecificAction(world, i_store);
	}
	
	public void scheduleBlob(WorldModel world, long ticks, Map<String, List<PImage>> i_store)
	{
		this.scheduleAction(world, this.createBlobAction(world, i_store), ticks + this.getRate());
		this.scheduleAnimation(world);
	}
	
}
