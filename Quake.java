import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class Quake extends AnimatedActor
{
	static final int QUAKE_STEPS = 10;
	static final int QUAKE_DURATION = 1100;
	static final int QUAKE_ANIMATION_RATE = 100;
	
	public Quake(String name, Point position, List<PImage> imgs, int animation_rate)
	{
		super(name, position, imgs, animation_rate);
	}
	
	public Action createEntityDeathAction(WorldModel world)
	{
		Action[] a = { null };
		a[0] = (long current_ticks) ->
		{
			this.removePendingAction(a[0]);
			this.removeEntity(world);
			Point pt = this.getPosition();
			List<Point> return_pts = new ArrayList<Point>();
			return_pts.add(pt);
			return return_pts;
		};
		return a[0];
	}
	
	public void scheduleQuake(WorldModel world, long ticks)
	{
		this.scheduleAnimation(world, QUAKE_STEPS);
		this.scheduleAction(world, this.createEntityDeathAction(world), ticks + QUAKE_DURATION);
	}
}
