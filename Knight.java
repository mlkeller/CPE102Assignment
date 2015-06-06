import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import processing.core.PImage;

public class Knight extends Mover
{

	public Knight(String name, Point position, List<PImage> imgs,
				  int animation_rate, int rate)
	{
		super(name, position, imgs, animation_rate, rate);
	}
	
	public Action createKnightAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		a[0] = (long current_ticks) ->
		{
			this.removePendingAction(a[0]);

			Point entity_pt = this.getPosition();
			MinerTrapped miner = (MinerTrapped)world.findNearest(entity_pt, MinerTrapped.class);
			PointBooleanPair tiles_and_found = this.knightToMinerTrapped(world, miner);

			long next_time = current_ticks + this.getRate();
			
			if (tiles_and_found.getBoolean())
			{
				MinerNotFull new_miner = world.createMinerNotFull(miner.getName(), tiles_and_found.getPoint().get(0),
																  miner.getAnimationRate(), miner.getRate(),
																  miner.getResourceLimit(), i_store);
				world.addEntity(new_miner);
				new_miner.scheduleMiner(world, current_ticks, i_store);

				
			}
			
			if(world.findNearest(entity_pt, RedBlob.class) == null &&
				   world.findNearest(entity_pt, MinerTrapped.class) == null)
			{				
				Quake quake = world.createQuake(this.getPosition(), current_ticks, i_store);
				//Vein vein = world.createVein("vein", this.getPosition(), current_ticks + Quake.QUAKE_DURATION*2, i_store);
				world.clearPendingActions(this);
				this.removeEntity(world);
				//world.addEntity(vein);
				world.addEntity(quake);
			}
			

			if(!(world.findNearest(entity_pt, RedBlob.class) == null &&
				   world.findNearest(entity_pt, MinerTrapped.class) == null))
			{
				this.scheduleAction(world, this.createKnightAction(world, i_store), next_time);
			}
			
			return tiles_and_found.getPoint();
		};
		return a[0];
	}
	
	public PointBooleanPair knightToMinerTrapped(WorldModel world, MinerTrapped miner)
	{
		List<Point> return_pts = new ArrayList<Point>();
		Point entity_pt = this.getPosition();
		
		if (miner == null)
		{
			RedBlob blob = (RedBlob)world.findNearest(this.getPosition(), RedBlob.class);
			if(blob != null)
			{
				return_pts = world.moveEntity(this, world.nextPosition(this.getPosition(), blob.getPosition()));
			}
			else
			{
				return_pts.add(entity_pt);
			}
			return new PointBooleanPair(return_pts, false);
		}
		else
		{
			Point miner_pt = miner.getPosition();
			if (MathOperations.adjacent(entity_pt, miner_pt))
			{
				miner.removeEntity(world);
				return_pts.add(miner_pt);
				return new PointBooleanPair(return_pts, true);
			}
			
			else
			{
				List<Point> new_path = this.AStarPath(entity_pt, miner_pt, world);
				if (new_path != null)
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
	
	public void scheduleKnight(WorldModel world, long ticks, Map<String, List<PImage>> i_store)
	{
		this.scheduleAction(world, this.createKnightAction(world, i_store), ticks + this.getRate()*4);
		this.scheduleAnimation(world);
	}
}
