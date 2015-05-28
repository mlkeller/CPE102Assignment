import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import processing.core.*;

public class Ore extends Actionable
{
	static final int BLOB_RATE_SCALE = 4;
	static final int BLOB_ANIMATION_RATE_SCALE = 50;
	static final int BLOB_ANIMATION_MIN = 1;
	static final int BLOB_ANIMATION_MAX = 3;
	
	int rate = 5000;
	
	public Ore(String name, Point position, List<PImage> imgs, int rate)
	{
		super(name, position, imgs);
		this.rate = rate;
	}
	
	public Ore(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
	}
	
	public String entityString()
	{
		return ("miner" + " " + 
			    this.getName() + " " +
			    this.getPosition().getX() + " " + 
			    this.getPosition().getY() + " " +
			    this.getRate());
	}
	
	public int getRate()
	{
		return this.rate;
	}
	
	public Action createOreTransformAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		a[0] = (long current_ticks) ->
		{
			List<Point> tiles = new ArrayList<Point>();
			
			this.removePendingAction(a[0]);
			OreBlob blob = world.createBlob(this.getName() + " -- blob", this.getPosition(),
											this.getRate() / BLOB_RATE_SCALE, current_ticks, i_store);
			this.removeEntity(world);
			world.addEntity(blob);
			
			tiles.add(blob.getPosition());
			return tiles;
		};
		return a[0];
	}
	
	public void scheduleOre(WorldModel world, long ticks, Map<String, List<PImage>> i_store)
	{
		this.scheduleAction(world, this.createOreTransformAction(world, i_store), ticks + this.getRate());
	}
}