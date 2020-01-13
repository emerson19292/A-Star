package aStar;

public class Node {

	int x, y;
	
	int parentX, parentY;
	
	int fCost = 2147483647, gCost = 2147483647, hCost = 0; //gcost = distance from start, hcost = distance for end, fcost = sum
	
	static final int OPEN = 1, CLOSED = 2, WALL = 3, NULL = 4, PATH = 5;
	
	boolean isStart, isEnd;
	
	int state;
	
	public Node(int x, int y) {

		this.x = x;
		this.y = y;
		
		state = NULL;
		
	}
	
	public void explore() {
		
		for (int y = -1; y <= 1; y++) {
			for (int x = -1; x <= 1; x++) {
				
				try {
					if (Main.nodes[this.y + y][this.x + x].isEnd) {
						
						this.showPath();
						
						System.out.println("End found");
											
						Main.nodes[this.y + y][this.x + x].getCosts(this);
						Main.nodes[this.y + y][this.x + x].state = OPEN;
						
						Main.searchInProgress = false;
																
					} else if (Main.nodes[this.y + y][this.x + x].state != WALL && Main.nodes[this.y + y][this.x + x].state != CLOSED) {
						
						Main.nodes[this.y + y][this.x + x].getCosts(this);
						Main.nodes[this.y + y][this.x + x].state = OPEN;
						
					}
				} catch (Exception ex) {
					
					System.out.println("Algorithm attempted check out of bounds. Telling algorithm off...");
					
				}
				
			}
		}
		
		state = CLOSED;
				
	}
	
	public void showPath() {
		
		state = PATH;
				
		if (!Main.nodes[parentY][parentX].isStart) {
			
			Main.nodes[parentY][parentX].showPath();
			
		}
		
	}
	
	public void getCosts(Node parent) {
		
		hCost = getDist(this.x, this.y, Main.end.x, Main.end.y);
		
		int tempGCost;
		
		if (this.x != parent.x && this.y != parent.y) {
			tempGCost = parent.gCost + 14;
		} else {
			tempGCost = parent.gCost + 10;
		}
		
		if (tempGCost <= gCost) {
			gCost = tempGCost;
			setParent(parent);
		}
		
		fCost = hCost + gCost;
		
	}
	
	public void setParent(Node parent) {
		
		parentX = parent.x;
		parentY = parent.y;
		
	}
	
	public int getDist(int x1, int y1, int x2, int y2) {
		
		int dist = 0;
		
		int px = x1, py = y1; //p stands for pointer. They move diagonally till they line up on the x or y with the destination
		
		while (px != x2 && py != y2) {
			
			if (px != x2 && py != y2) {
				
				if (x1 < x2) px++; else px--;
				if (y1 < y2) py++; else py--;
				
				dist += 14;
				
			}
			
		}
		
		dist += Math.abs((x2 - px) * 10);
		dist += Math.abs((y2 - py) * 10);
		
		return dist;
		
	}
	
	public void initializeState() {
		
		if (state == WALL || state == CLOSED) {
			
			fCost = 2147483647;
			hCost = 2147483647;
			
		}
		
	}

}
