package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Game extends GameState {

	private static final long serialVersionUID = 1L;
	
	public static Paddle leftPaddle, rightPaddle;
	public static Ball ball;
	
	public Game(char p1, char p2){

		ball = new Ball(width / 2, height / 2);
		
		if(p1 == 'h') leftPaddle = new Human(1);
		else leftPaddle = new Computer(1);
		if(p2 == 'h') rightPaddle = new Human(2);
		else rightPaddle = new Computer(2);
		
	
	}

	@Override
	public void gameUpdate() {
		ball.update();
		leftPaddle.update();
		rightPaddle.update();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(leftPaddle.getScore()), width/4, 40);
		g.drawString(Integer.toString(rightPaddle.getScore()), 3*(width/4), 40);

		g.drawRect(width / 2, 0, 1, height);

		ball.draw(g);
		leftPaddle.draw(g);
		rightPaddle.draw(g);
	}

	@Override
	public void keyPressHandler(KeyEvent e) {
		if (rightPaddle instanceof Human)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				((Human) rightPaddle).keyUp();
				break;
			case KeyEvent.VK_DOWN:
				((Human) rightPaddle).keyDown();
				break;
			}
		if (leftPaddle instanceof Human)
			switch (e.getKeyCode()) {

			case KeyEvent.VK_SHIFT:
				((Human) leftPaddle).keyUp();
				break;
			case KeyEvent.VK_CONTROL:
				((Human) leftPaddle).keyDown();
				break;
			}
		
	}

	@Override
	public void keyReleaseHandler(KeyEvent e) {
		if (rightPaddle instanceof Human)
			if (e.getKeyCode() == KeyEvent.VK_UP
					|| e.getKeyCode() == KeyEvent.VK_DOWN)
				((Human) rightPaddle).keyRelease();
		if (leftPaddle instanceof Human)
			if (e.getKeyCode() == KeyEvent.VK_CONTROL
					|| e.getKeyCode() == KeyEvent.VK_SHIFT)
				((Human) leftPaddle).keyRelease();
	}

}
