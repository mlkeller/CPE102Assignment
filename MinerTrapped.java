import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import processing.core.PImage;


public class MinerTrapped extends Miner
{
	public MinerTrapped(String name, Point position, List<PImage> imgs,
						int animation_rate, int rate, int resource_limit)
	{
		super(name, position, imgs, animation_rate, rate, resource_limit, 0);
	}
	
	//figure out a way to get rid of
	public Action createMinerSpecificAction (WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		//a[0] = (long current_ticks) ->
		//{
			//this.removePendingAction(a[0]);
			//List<Point> return_pts = new ArrayList<Point>();
			//return_pts.add(this.getPosition());
			//return return_pts;
		//};
		return a[0];
	}

}
