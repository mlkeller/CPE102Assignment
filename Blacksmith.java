import java.util.List;
import processing.core.*;

public class Blacksmith extends Entity
{
	public Blacksmith(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
	}

	public String entityString()
	{
		return ("blacksmith" + " " +
				this.getName() + " " +
				Integer.toString(this.getPosition().getX()) + " " +
				Integer.toString(this.getPosition().getY()));
	}
}