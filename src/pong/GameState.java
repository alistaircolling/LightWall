package pong;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public abstract class GameState extends JPanel { //implements Runnable{

	private static final long serialVersionUID = 1L;
	
	protected boolean running = false;
	
	public static int fps = 0;
	public static int width, height;
	
	public GameState(){
		
		width = Pong.width;
		height = Pong.height;
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(false);
		this.setVisible(true);
	}
	
	public abstract void gameUpdate();
	public abstract void draw(Graphics g);
	public abstract void keyPressHandler(KeyEvent e);
	public abstract void keyReleaseHandler(KeyEvent e);
	
}
