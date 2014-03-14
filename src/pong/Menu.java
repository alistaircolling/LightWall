package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Menu extends GameState {

	private static final long serialVersionUID = 1L;
	
	public MenuOption[] options;
	public int choice = 0;
	private Pong game;
	
	public class MenuOption{
		public String name;
		public char p1, p2;
		
		public MenuOption(String name, char p1, char p2){
			this.name = name;
			this.p1 = p1;
			this.p2 = p2;
		}
	}
	
	public Menu(final Pong game){
		super();
		this.game = game;
		
		options = new MenuOption[4];
		options[0] = new MenuOption("Human 		- 	Human", 'h', 'h');
		options[1] = new MenuOption("Human 	  	- 	Computer", 'h', 'c');
		options[2] = new MenuOption("Computer	- 	Human", 'c', 'h');
		options[3] = new MenuOption("Computer 	- 	Computer", 'c', 'c');
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		for(int i = 0; i < 4; i++){
			g.drawString(options[i].name, 20, 120 + (20*i));
		}
		g.drawOval(12, 113 + (20 * choice), 4, 4);
	}
	
	@Override
	public void keyPressHandler(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			game.startGame(options[choice].p1, options[choice].p2);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			if(choice > 0)
				choice--;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if(choice < 3)
				choice++;
		}
	}

	@Override
	public void keyReleaseHandler(KeyEvent e) {
	}

	@Override
	public void gameUpdate() {
	}

}
