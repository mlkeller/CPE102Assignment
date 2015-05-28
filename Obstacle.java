import java.util.List;
import processing.core.*;

public class Obstacle extends Entity
{
	public Obstacle(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
	}
	
	public String entityString()
	{
		return ("obstacle" + " " +
				this.getName() + " " +
				Integer.toString(this.getPosition().getX()) + " " +
				Integer.toString(this.getPosition().getY()));
	}
}