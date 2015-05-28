import java.util.LinkedList;
import java.util.List;
import processing.core.*;

public abstract class Actionable extends Entity
{
	private List<Action> pending_actions = new LinkedList<Action>();
	
	public Actionable(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
	}
	
	public List<Action> getPendingActions()
	{
		return this.pending_actions;
	}
	
	public void addPendingAction(Action action)
	{
		this.pending_actions.add(action);
	}
	
	public void removePendingAction(Action action)
	{
		this.pending_actions.remove(action);
	}
	
	public void clearPendingActions()
	{
		this.pending_actions.clear();
	}
	
	public void scheduleAction(WorldModel world, Action action, long time)
	{
		this.addPendingAction(action);
		world.scheduleAction(action, time);
	}
	
	public void removeEntity(WorldModel world)
	{
		for (Action a : this.pending_actions)
		{
			world.unscheduleAction(a);
		}
		this.clearPendingActions();
		world.removeEntity(this);
	}
}