import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import processing.core.*;

public class MinerFull extends Miner
{

	public MinerFull(String name, Point position, List<PImage> imgs, int animation_rate, int rate,
					 int resource_limit)
	{
		super(name, position, imgs, animation_rate, rate, resource_limit, resource_limit);
	}
	
	public MinerNotFull tryTransformMinerFull(WorldModel world)
	{
		MinerNotFull new_entity = new MinerNotFull(this.getName(), this.getPosition(), this.getImages(),
												   this.getAnimationRate(), this.getRate(), this.getResourceLimit());
		return new_entity;
	}
	
	public Action createMinerSpecificAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		a[0] = (long current_ticks) ->
		{
			this.removePendingAction(a[0]);
			
			Point entity_pt = this.getPosition();
			Entity smith = world.findNearest(entity_pt, Blacksmith.class);
			PointBooleanPair tiles_and_found = this.minerToSmith(world, smith);
			
			Miner new_entity = this;
			if (tiles_and_found.getBoolean())
			{
				new_entity = this.tryTransformMinerFull(world);
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
	
	public PointBooleanPair minerToSmith(WorldModel world, Entity smith)
	{
		List<Point> return_pts = new ArrayList<Point>();
		Point entity_pt = this.getPosition();
		
		if (smith == null)
		{
			return_pts.add(entity_pt);
			return new PointBooleanPair(return_pts, false);
		}
		else
		{
			Point smith_pt = smith.getPosition();
			if (MathOperations.adjacent(entity_pt, smith_pt))
			{
				this.setResourceCount(0);
				return new PointBooleanPair(return_pts, true);
			}
			else
			{
				List<Point> new_path = this.AStarPath(entity_pt, smith_pt, world);
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