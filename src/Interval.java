//Benjamin Schroeder

public class Interval 
{
	public int low;
	public int high;
	
	public Interval(int low, int high)
	{
		this.low = low;
		this.high = high;
	}
	
	public int getLow()
	{
		return this.low;
	}
	
	public int getHigh()
	{
		return this.high;
	}

	public boolean overlaps(Interval i) {
		if (i.high < this.low)
			return false;
		if(i.low > this.high)
			return false;
		return true;
	}
	
	public boolean overlapsExatly(Interval i) {
		if (i.low != this.low)
			return false;
		if (i.high != this.high)
			return false;
		return true;
	}
}
