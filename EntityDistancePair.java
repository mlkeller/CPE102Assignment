
public class EntityDistancePair
{
	private Entity entity;
	private double distance;
	
	public EntityDistancePair(Entity entity, double distance)
	{
		this.entity = entity;
		this.distance = distance;
	}
	
	public Entity getEntity()
	{
		return this.entity;
	}
	
	public double getDistance()
	{
		return this.distance;
	}

}
