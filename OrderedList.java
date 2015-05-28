import java.util.LinkedList;
import java.util.List;


public class OrderedList<T>
{
	private List<OrderedListItem<T>> list = new LinkedList<OrderedListItem<T>>();
	
	public int getSize()
	{
		return list.size();
	}
	
	public void insert(T item, long ord)
	{
		int size = this.list.size();
		int idx = 0;
		while(idx < size && this.list.get(idx).getTime() < ord)
		{
			idx = idx + 1;
		}
		
		this.list.add(idx, new OrderedListItem<T>(item, ord));
	}
	
	public void remove(T item) //change action to Action later
	{
		int size = this.list.size();
		int idx = 0;
		while(idx < size && this.list.get(idx).getItem() != item)
		{
			idx = idx + 1;
		}
		
		if (idx < size)
		{
			this.list.remove(idx);
		}
	}
	
	public OrderedListItem<T> head()
	{
		if (!(this.list.isEmpty()))
		{
			return this.list.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public T pop()
	{
		if(!(this.list.isEmpty()))
		{
			T popped = this.list.get(0).getItem();
			this.list.remove(0);
			return popped;
		}
		else
		{
			return null;
		}
	}
	
	public boolean contains(T item)
	{
		return list.contains(item);
	}
}

/// at some point go and put in the checks to deal with the other lists are empty