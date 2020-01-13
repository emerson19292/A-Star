package aStar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	public static int boardHeight = 40;
	public static int boardWidth = 40;
	public static int nodeSize = 16;
	
	public static Point start = new Point(0,0);
	public static Point end = new Point(0,1);
	
	public static boolean searchInProgress = false;
	
	boolean startSet = false, endSet = false;
	
	static Node[][] nodes = new Node[boardHeight][boardWidth];
	
	public static void main(String[] args) {
		
		for (int row = 0; row < boardHeight; row++) {
			
			for (int column = 0; column < boardWidth; column++) {
				
				nodes[row][column] = new Node(column, row);
				
			}
			
		}
		
		new Main();

	}
	
	public Main() {
		
		this.setSize(boardWidth * nodeSize + 30, boardHeight * nodeSize + 30);
		this.setTitle("A*");
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		GraphicsPanel game = new GraphicsPanel();
		
		game.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				
				if (!startSet) {
					
					start = new Point(Math.round(e.getX() / nodeSize), Math.round(e.getY() / nodeSize));
					
					nodes[start.y][start.x].isStart = true;
					
					System.out.println("Start set");
					
					startSet = true;
										
				} else if (!endSet) {
					
					end = new Point(Math.round(e.getX() / nodeSize), Math.round(e.getY() / nodeSize));
					
					nodes[end.y][end.x].isEnd = true;
					
					System.out.println("End set");
					
					endSet = true;
										
				}
				
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
			
		});
		
		game.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				
				nodes[Math.round(e.getY() / nodeSize)][Math.round(e.getX() / nodeSize)].state = Node.WALL;
				
			}

			public void mouseMoved(MouseEvent e) {	}
			
		});
		
		
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0, 8, TimeUnit.MILLISECONDS);
		
		InputMap im = game.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "start");
		
		ActionMap am = game.getActionMap();
		am.put("start", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				if (startSet && endSet) {
					
					System.out.println("Starting algorithm");
					
					searchInProgress = true;
					
					nodes[start.y][start.x].explore();
					
				}
				
			}
		});
		
		this.add(game);
		this.setVisible(true);
		
	}

}

class RepaintTheBoard implements Runnable {

	Main theBoard;
	
	public void run() {
		
		if (Main.searchInProgress) {
			
			int tx = 0; //target x and target y. the node with the lowest fcost.
			int ty = 0;
			
			for (int row = 0; row < Main.boardHeight; row++) {
				
				for (int column = 0; column < Main.boardWidth; column++) { //if fcost is lower
					
					Main.nodes[row][column].initializeState();
					
					if (Main.nodes[row][column].fCost < Main.nodes[ty][tx].fCost) {
						
						ty = row;
						tx = column;
						
					} else if (Main.nodes[row][column].fCost == Main.nodes[ty][tx].fCost && Main.nodes[row][column].hCost < Main.nodes[ty][tx].hCost && Main.nodes[ty][tx].state == Node.OPEN) { //if fcost is same and hcost is lower
						
						ty = row;
						tx = column;
						
					}
					
				}
				
			}
			
			Main.nodes[ty][tx].explore();
			
		}
		
		theBoard.repaint();
		
	}
	
	public RepaintTheBoard(Main theBoard) {
		
		this.theBoard = theBoard;
		
	}

}

@SuppressWarnings("serial")
class GraphicsPanel extends JComponent {
	
	public void paint(Graphics g) {
		
		Graphics2D gs = (Graphics2D)g;
		
		int cellGap = 1; //this is cropped not added
		
		gs.setColor(Color.DARK_GRAY);
		gs.fillRect(0, 0, Main.boardWidth * Main.nodeSize + cellGap, Main.boardHeight * Main.nodeSize + cellGap);
		
		for (int row = 0; row < Main.boardHeight; row++) {
			
			for (int column = 0; column < Main.boardWidth; column++) {
				
				switch (Main.nodes[row][column].state) {
				
				case Node.NULL:
					gs.setColor(new Color(220, 220, 220));
					break;
				
				case Node.OPEN:
					gs.setColor(new Color(93, 230, 93));
					break;
					
				case Node.CLOSED:
					gs.setColor(new Color(230, 93, 93));
					break;
					
				case Node.WALL:
					gs.setColor(new Color(138, 138, 138));
					break;
					
				case Node.PATH:
					gs.setColor(new Color(93, 93, 230));
					break;
				
				}
				
				if (Main.nodes[row][column].isStart || Main.nodes[row][column].isEnd) {
					
					gs.setColor(new Color(93, 93, 230));
					
				}
				
				gs.fillRect(Main.nodes[row][column].x * Main.nodeSize + cellGap, Main.nodes[row][column].y * Main.nodeSize + cellGap, Main.nodeSize - cellGap, Main.nodeSize - cellGap);
				
			}
			
		}
	     	     
	}
	
}