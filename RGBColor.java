
public class RGBColor
{
	private int red;
	private int green;
	private int blue;
	private int alpha = 0;
	
	public RGBColor(int red, int green, int blue, int alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	public RGBColor(int red, int green, int blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	
	public int getRed()
	{
		return this.red;
	}
	
	public int getGreen()
	{
		return this.green;
	}
	
	public int getBlue()
	{
		return this.blue;
	}
	
	public int getAlpha()
	{
		return this.alpha;
	}
	
}
