//Benjamin Schroeder

public class Node 
{
	public Node parent;
	public Node left;
	public Node right;
	
	public Interval interval;
	public int priority;
	public int iMax;
	
	public Node(Interval i) 
	{
		this.interval = i;
		iMax = interval.getHigh();
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public Node getLeft()
	{
		return left;
	}
	
	public Node getRight()
	{
		return right;
	}
	
	public Interval getInterv()
	{
		return interval;
	}
	
	public int getIMax()
	{
		return iMax;
	}
	
	public int getPriority()
	{
		return priority;
	}

	public int key() 
	{
		return interval.getLow();
	}

	public int getHeight() 
	{
		int leftHeight = -1;
		int rightHeight = -1;
		
		if (this.left != null) leftHeight = this.left.getHeight();
		if (this.right != null) rightHeight = this.right.getHeight();
			
		return 1 + Math.max(leftHeight, rightHeight);
	}
}
