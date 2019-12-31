package aStar;

public class Node {

	int x, y;
	
	int parentX, parentY;
	
	int fCost;
	
	static final int OPEN = 1, CLOSED = 2, WALL = 3, NULL = 4;
	
	public Node() {
		// TODO Auto-generated constructor stub
	}

}
