import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import processing.core.*;

public class MinerNotFull extends Miner
{
	public MinerNotFull(String name, Point position, List<PImage> imgs, int animation_rate, int rate,
					    int resource_limit)
	{
		super(name, position, imgs, animation_rate, rate, resource_limit, 0);
	}
	
	public String entityString()
	{
		return ("miner" + " " + 
			    this.getName() + " " +
			    this.getPosition().getX() + " " + 
			    this.getPosition().getY() + " " +
			    this.getResourceLimit() + " " +
			    this.getRate() + " " +
			    this.getAnimationRate());
	}
	
	public Miner tryTransformMinerNotFull()
	{
		if (this.getResourceCount() < this.getResourceLimit())
		{
			return this;
		}
		else
		{
			MinerFull new_entity = new MinerFull(this.getName(), this.getPosition(), this.getImages(),
												 this.getAnimationRate(), this.getRate(), this.getResourceLimit());
			return new_entity;
		}
	}
	
	public Action createMinerSpecificAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		a[0] = (long current_ticks) ->
		{
			this.removePendingAction(a[0]);
			
			Point entity_pt = this.getPosition();
			Ore ore = (Ore)world.findNearest(entity_pt, Ore.class);
			PointBooleanPair tiles_and_found = this.minerToOre(world, ore);
			
			Miner new_entity = this;
			if (tiles_and_found.getBoolean())
			{
				new_entity = this.tryTransformMinerNotFull();
				if (new_entity != this)
				{
					world.clearPendingActions(this);
					world.removeEntityAt(this.getPosition());
					world.addEntity(new_entity);
					new_entity.scheduleAnimation(world);
				}
			}
			new_entity.scheduleAction(world, new_entity.createMinerAction(world, i_store), current_ticks + new_entity.getRate());
			
			return tiles_and_found.getPoint();
		};
		return a[0];
	}
	
	public PointBooleanPair minerToOre(WorldModel world, Actionable ore)
	{
		List<Point> return_pts = new ArrayList<Point>();
		Point entity_pt = this.getPosition();
		
		if (ore == null)
		{
			return_pts.add(entity_pt);
			return new PointBooleanPair(return_pts, false);
		}
		else
		{
			Point ore_pt = ore.getPosition();
			if (MathOperations.adjacent(entity_pt, ore_pt))
			{
				this.setResourceCount(this.getResourceCount() + 1);
				ore.removeEntity(world);
				return_pts.add(ore_pt);
				return new PointBooleanPair(return_pts, true);
			}
	
			else
			{
				List<Point> new_path = this.AStarPath(entity_pt, ore_pt, world);  // used to be Point new_pt = world.nextPosition(entity_pt, ore_pt);
				if (new_path != null) //if pathfinding didn't fail
				{
					return new PointBooleanPair(world.moveEntity(this, new_path.get(0)), false);
				}
				else
				{
					return_pts.add(entity_pt);
					return new PointBooleanPair(return_pts, false);
				}
			}
		}
	}
	
}
