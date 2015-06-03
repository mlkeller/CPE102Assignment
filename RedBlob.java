import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import processing.core.PImage;


public class RedBlob extends Blob
{
	public RedBlob(String name, Point position, List<PImage> imgs,
				   int animation_rate, int rate)
	{
		super(name, position, imgs, animation_rate, rate);
	}

	public Action createBlobSpecificAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		a[0] = (current_ticks) ->
		{
			this.removePendingAction(a[0]);
			
			Point entity_pt = this.getPosition();
			MinerNotFull miner = (MinerNotFull)world.findNearest(entity_pt, MinerNotFull.class);
			PointBooleanPair tiles_and_found = this.blobToMiner(world, miner);

			long next_time = current_ticks + this.getRate();
			
			if(tiles_and_found.getBoolean())
			{
				MinerTrapped new_miner = world.createMinerTrapped(miner.getName(), tiles_and_found.getPoint().get(0),
																  miner.getAnimationRate(), miner.getRate(),
																  miner.getResourceLimit(), i_store);
				world.addEntity(new_miner);
				this.removeEntity(world);
			}
			
			this.scheduleAction(world, this.createBlobSpecificAction(world, i_store), next_time);
			
			return tiles_and_found.getPoint();
		};
		return a[0];
	}
	
	public PointBooleanPair blobToMiner(WorldModel world, MinerNotFull miner)
	{
		List<Point> return_pts = new ArrayList<Point>();
		Point entity_pt = this.getPosition();
		
		if (miner == null)
		{
			return_pts.add(entity_pt);
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
				List<Point> new_path = this.AStarPath(entity_pt, miner_pt, world);  // used to be Point new_pt = world.nextPosition(entity_pt, ore_pt);
				if (new_path != null) //if pathfinding didn't fail
				{
					Entity old_entity = world.getTileOccupant(new_path.get(0));
					if (Ore.class.isInstance(old_entity))
					{
						((Ore)old_entity).removeEntity(world);
					}
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
