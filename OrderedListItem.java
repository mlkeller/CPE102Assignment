
public class OrderedListItem<T>
{
	private T item;
	private long ord;
	
	public OrderedListItem(T item, long ord)
	{
		this.item = item;
		this.ord = ord;
	}
	
	public T getItem()
	{
		return this.item;
	}
	
	public long getTime()
	{
		return this.ord;
	}
}
