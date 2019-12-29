package aStar;

import java.awt.Graphics;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;

;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	public static void main(String[] args) {
		
		new Main();

	}
	
	public Main() {
		
		this.setSize(500, 500);
		this.setTitle("Main bike game");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		GraphicsPanel game = new GraphicsPanel();
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0, 20, TimeUnit.MILLISECONDS);
		
		this.add(game);
		this.setVisible(true);
		
	}

}

class RepaintTheBoard implements Runnable {

	Main theBoard;
	
	public void run() {
		
		
		
	}
	
	public RepaintTheBoard(Main theBoard) {
		
		this.theBoard = theBoard;
		
	}

}

@SuppressWarnings("serial")
class GraphicsPanel extends JComponent {
	
	public void paint(Graphics g) {
		
		
	     	     
	}
	
}