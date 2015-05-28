import java.util.ArrayList;
import java.util.List;

import processing.core.*;

public abstract class AnimatedActor extends Actionable
{
	private int animation_rate;
	
	public AnimatedActor(String name, Point position, List<PImage> imgs, int animation_rate)
	{
		super(name, position, imgs);
		this.animation_rate = animation_rate;
	}
	
	public int getAnimationRate()
	{
		return this.animation_rate;
	}
	
	public void nextImage()
	{
		this.setCurrentImage((this.getCurrentImage() + 1) % this.getImages().size());
	}
	
	public Action createAnimationAction(WorldModel world, int repeat_count)
	{
		Action[] a = { null };
		a[0] = (long current_ticks) ->
		{
			this.removePendingAction(a[0]);
			this.nextImage();

			List<Point> return_pts = new ArrayList<Point>();
			if (repeat_count != 1)
			{
				this.scheduleAction(world, this.createAnimationAction(world, Math.max(repeat_count - 1, 0)),
						 		    current_ticks + this.getAnimationRate());
				return_pts.add(this.getPosition());
			}
			return return_pts;
		};
		return a[0];
	}
	
	public void scheduleAnimation(WorldModel world, int repeat_count)
	{
		this.scheduleAction(world, this.createAnimationAction(world, repeat_count), this.getAnimationRate());
	}
	public void scheduleAnimation(WorldModel world)
	{
		int repeat_count = 0;
		this.scheduleAction(world, this.createAnimationAction(world, repeat_count), this.getAnimationRate());
	}

}