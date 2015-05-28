import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public abstract class WorldObject
{
	private String name;
	private List<PImage> imgs = new ArrayList<PImage>();
	private int current_img = 0;
	
	public WorldObject(String name, List<PImage> imgs)
	{
		this.name = name;
		this.imgs = imgs;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public List<PImage> getImages()
	{
		return this.imgs;
	}
	
	public PImage getImage()
	{
		return this.imgs.get(current_img);
	}
	
	public int getCurrentImage()
	{
		return this.current_img;
	}
	
	public void setCurrentImage(int new_current_img)
	{
		this.current_img = new_current_img;
	}
}
