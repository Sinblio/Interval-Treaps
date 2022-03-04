import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Benjamin Schroeder

public class IntervalTreap 
{
	public Node head;
	public List<Node> list = new ArrayList<Node>();
	public int count = 0;
	
	public IntervalTreap()
	{
		
	}
	
	public Node getRoot()
	{
		return head;
	}
	
	public int getSize()
	{
		return list.size();
	}
	
	public int getHeight()
	{
		if (head == null)
			return 0;
		else
			return head.getHeight() - 1;
	}
	
	public void intervalInsert(Node z)
	{
		Random random = new Random();
		z.priority = random.nextInt(Integer.MAX_VALUE);
		
		list.add(z);
		
		if (head == null) {
			head = z;
			head.iMax = z.interval.high;
		} else {
			Node select = head;
			Boolean inserted = false;
			
			while(!inserted) {
				if (z.key() < select.key()) {
					if (select.left == null) {
						select.left = z;
						z.parent = select;
						inserted = true;
					} else {
					select = select.left;
					}
				} else {
					if (select.right == null) {
						select.right = z;
						z.parent = select;
						inserted = true;
						break;
					} else {
					select = select.right;	
					}
				}
			}
			
			z.iMax = z.interval.getHigh();
			
			while(z.parent != null && z.priority < z.parent.priority) {
				if (z.parent.left != null && z == z.parent.left)
					rotateRight(z);
				else
					rotateLeft(z);
			}
			
			Node c = z.parent;
			while(c != null && c.iMax < z.iMax) {
				c.iMax = z.iMax;
				c = c.parent;
			}
			
		}
	}
	
	public void intervalDelete(Node z)
	{
		count ++;
		if(count % 100 == 0)
			System.out.println(count);
		
		if (head != null && this.getHeight() != 0) {
			Node n = head;
			
			while (n != null && n != z) {
				if (n.key() > z.key())
					n = n.getLeft();
				else
					n = n.getRight();
			}
			
			int leftIMax;
			int rightIMax;
			int newIMax;
			
			if (n != null) {
				if (n.left == null) {
					System.out.println("right");
					if (n.parent != null) {
						if(n.parent.left == n) {
							n.parent.left = n.right;
						} else {
							n.parent.right = n.right;
						}
						if (n.right != null)
							n.right.parent = n.parent;
					} else {
						head = n.right;
					}
					
					list.remove(n);
					
					if (n.right == null) {
						Node c = n;
						boolean b = true;
						while(c != null && b) {
							leftIMax = -1;
							rightIMax = -1;
								
							if(c.left != null) leftIMax = c.left.iMax;
							if(c.right != null) rightIMax = c.right.iMax;
								
							newIMax = Math.max(c.interval.getHigh(), Math.max(leftIMax, rightIMax));
								
							if (newIMax != c.iMax) {
								c.iMax = newIMax;
								c = c.parent;
							} else {
								b = false;
							}
						}
						
					}
					if (n == head) head = n.right;
					n = n.right;
				} else if (n.right == null) {
					System.out.println("left");
					if (n.parent != null) {
						if(n.parent.left == n) {
							n.parent.left = n.left;
						} else {
							n.parent.right = n.left;
						}
						if (n.left != null)
							n.left.parent = n.parent;
					} else {
						head = n.left;
					}
					if(n == head) head = n.left;
					list.remove(n);
					n = n.left;
				} else {
					System.out.println("other");
					Node s = n.right;
					while (s.left != null)
						s = s.left;
					
					Node c = s.parent;
					
					leftIMax = -1;
					rightIMax = -1;
					
					if (c.left != null && c.left == s)
						if(c.right != null) rightIMax = c.right.iMax;
					else
						if(c.left != null) leftIMax = c.left.iMax;
					
					newIMax = Math.max(leftIMax, rightIMax);
					
					if (newIMax < c.iMax) {
						c.iMax = newIMax;
						c = c.parent;
						boolean b = true;
						while(c != null && b) {
							leftIMax = -1;
							rightIMax = -1;
							
							if(c.left != null) leftIMax = c.left.iMax;
							if(c.right != null) rightIMax = c.right.iMax;
							
							newIMax = Math.max(c.interval.getHigh(), Math.max(leftIMax, rightIMax));
							
							if (newIMax != c.iMax) {
								c.iMax = newIMax;
								c = c.parent;
							} else {
								b = false;
							}
						}
					}
					
					if (s.parent.left == s) {
						s.parent.left = s.right;
					} else {
						s.parent.right = s.right;
					}
					
					if (s.right != null) s.right.parent = s.parent;
					
					s.parent = n.parent;
					if (s.parent != null) {
						if (n.parent.left == n) {
							n.parent.left = s;
						} else {
							n.parent.right = s;
						}
					}
					
					s.left = n.left;
					s.left.parent = s;
					
					s.right = n.right;
					if (s.right != null) s.right.parent = s;
					
					if(n == head) head = s;
					list.remove(n);
					n = s;
				}
				
				if(n != null) {
					leftIMax = -1;
					rightIMax = -1;
					
					if(n.left != null) leftIMax = n.left.iMax;
					if(n.right != null) rightIMax = n.right.iMax;
					
					newIMax = Math.max(n.interval.getHigh(), Math.max(leftIMax, rightIMax));
				
					if (newIMax != n.iMax) {
						n.iMax = newIMax;
						Node c = n.parent;
						boolean b = true;
						while(c != null && b) {
							leftIMax = 0;
							rightIMax = 0;
							
							if(c.left != null) leftIMax = c.left.iMax;
							if(c.right != null) rightIMax = c.right.iMax;
							
							newIMax = Math.max(c.interval.getHigh(), Math.max(leftIMax, rightIMax));
							
							if (newIMax != c.iMax) {
								c.iMax = newIMax;
								c = c.parent;
							} else {
								b = false;
							}
						}
					}
				}
				
				while (n != null && ((n.left != null && n.priority > n.left.priority) || (n.right != null && n.priority > n.right.priority))) {
					if (n.left != null && n.priority > n.left.priority && (n.right == null || n.left.priority <= n.right.priority))
						rotateRight(n.left);
					if (n.right != null && n.priority > n.right.priority && (n.left == null || n.right.priority < n.left.priority))
						rotateLeft(n.right);
				}
			}
		} else if (this.getHeight() == 0) {
			head = null;
			list.remove(z);
		}
	}
	
	public Node intervalSearch(Interval i)
	{
		return intervalSearchRec(i, head);
	}
	
	public Node intervalSearchRec(Interval i, Node r)
	{
		if (r == null)
			return null;
		
		if (i.overlaps(r.getInterv()))
			return r;
		
		if (r.left != null && i.getLow() < r.left.getIMax())
			return intervalSearchRec(i, r.left);
			
		return intervalSearchRec(i, r.right);
	}
	
	public Node intervalSearchExactly(Interval i)
	{
		return intervalSearchExactRec(i, head);
	}
	
	public Node intervalSearchExactRec(Interval i, Node r)
	{
		if (r == null)
			return null;
		
		if (i.overlapsExatly(r.getInterv()))
			return r;
		
		if (r.left != null && i.getLow() < r.key())
			return intervalSearchExactRec(i, r.left);
			
		return intervalSearchExactRec(i, r.right);
	}
	
	public List<Interval> overlappingIntervals(Interval i)
	{
		return overlappingIntervalsRec(i, head);
	}
	
	public List<Interval> overlappingIntervalsRec(Interval i, Node r)
	{
		List<Interval> overlaps = new ArrayList<Interval>();
		
		if (i.overlaps(r.getInterv()))
			overlaps.add(r.interval);
		
		if (r.left != null && r.left.getIMax() >= i.getLow()) {
			List<Interval> left = overlappingIntervalsRec(i, r.left);
			if (left != null)
				overlaps.addAll(left);
		}
		
		if (r.right != null && r.key() <= i.getHigh()) {
			List<Interval> right = overlappingIntervalsRec(i, r.right);
			if (right != null)
				overlaps.addAll(right);
		}
		
		if (overlaps.size() == 0)
			return null;
		else
			return overlaps;
	}
	
	public void rotateLeft(Node pivot) {
		Node root = pivot.parent;
		
		if(root != head) {
			if(root.parent.key() > root.key())
				root.parent.left = pivot;
			else
				root.parent.right = pivot;
		} else {
			head = pivot;
		}
		pivot.parent = root.parent;
		root.parent = pivot;
		
		if (root.key() != pivot.key()) {
			root.right = pivot.left;
			if (pivot.left != null)
				pivot.left.parent = root;
			pivot.left = root;
		}else {
			Node temp = root.left;
			root.left = pivot.left;
			if (root.left != null)
				root.left.parent = root;
			root.right = pivot.right;
			if (root.right != null)
				root.right.parent = root;
			pivot.left = temp;
			if (pivot.left != null)
				pivot.left.parent = pivot;
			pivot.right = root;
		}
		
		int rootLeft = 0;
		int rootRight = 0;
		int pivotRight = 0;
		int pivotLeft = 0;
		
		if (root.left != null) rootLeft = root.left.getIMax();
		if (root.right != null) rootRight = root.right.getIMax();
		if (pivot.right != null) pivotRight = pivot.right.getIMax();
		if (pivot.left != null) pivotLeft = pivot.left.getIMax();
		
		root.iMax = Math.max(rootLeft, Math.max(rootRight, root.interval.high));
		pivot.iMax = Math.max(pivotLeft, Math.max(pivotRight, pivot.interval.high));
	}
	
	public void rotateRight(Node pivot) {
		Node root = pivot.parent;
		
		if(root != head) {
			if(root.parent.key() > root.key())
				root.parent.left = pivot;
			else
				root.parent.right = pivot;
		} else {
			head = pivot;
		}
		pivot.parent = root.parent;
		
		root.parent = pivot;
		root.left = pivot.right;
		if (pivot.right != null)
			pivot.right.parent = root;
		pivot.right = root;
		
		int rootLeft = 0;
		int rootRight = 0;
		int pivotLeft = 0;
		int pivotRight = 0;
		
		if (root.left != null) rootLeft = root.left.getIMax();
		if (root.right != null) rootRight = root.right.getIMax();
		if (pivot.left != null) pivotLeft = pivot.left.getIMax();
		if (pivot.right != null) pivotRight = pivot.right.getIMax();
		
		root.iMax = Math.max(rootLeft, Math.max(rootRight, root.interval.high));
		pivot.iMax = Math.max(pivotRight, Math.max(pivotLeft, pivot.interval.high));
	}
}
