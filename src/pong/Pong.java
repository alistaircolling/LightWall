package pong;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import processing.core.PImage;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;

public class Pong extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	GameState state;

	private Image dbImage;
	private Graphics dbg;
	private Thread game;
	protected boolean running = false;

	public PImage pongImage;

	private ArrayList<TColor> ballColors;
	private ArrayList<TColor> bgColors;

	private ColorGradient bgGrad;
	private ColorGradient ballGrad;

	private double ballX;

	public static int fps = 0;
	// public static int fWidth = 600, fHeight = 400;
	public static int fWidth = 400, fHeight = 250;
	public static int width = fWidth - 0, height = fHeight - 0;

	// public static int width = fWidth - 16, height = fHeight - 38;

	public Pong() {

		setSize(fWidth, fHeight);
		createColors();
		setTitle("Pong");
		FlowLayout layout = new FlowLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null); // Center in screen

		state = new Menu(this);

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				state.keyPressHandler(e);
			}

			public void keyReleased(KeyEvent e) {
				state.keyReleaseHandler(e);
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		add(state);
		state.setLocation(50, 50);
		setVisible(true);
	}

	private void createColors() {

		ballColors = new ArrayList<TColor>();
		bgColors = new ArrayList<TColor>();
		ballGrad = new ColorGradient();
		bgGrad = new ColorGradient();
		TColor newRan;
		TColor opp;
		for (int i = 0; i < 3; i++) {
			newRan = TColor.newRandom();
			opp = newRan.getInverted();
			ballColors.add(newRan);
			bgColors.add(opp);
			ballGrad.addColorAt(i * 400, newRan);
			bgGrad.addColorAt(i * 400, opp);
		}

	}

	public void startGame(char p1, char p2) {
		state = new Game(p1, p2);
		
	}

	public static void main(String[] args) {
		new Pong();
	}

	@Override
	@SuppressWarnings("static-access")
	public void run() {
		while (running) {
			long beforeTime = System.nanoTime();
			
			
		
			gameUpdate();
			gameRender();
			paintScreen();

			double millis = System.nanoTime() - beforeTime;
			millis /= 1000000;

			try {
				if ((int) (16.66666 - millis) > 0)
					game.sleep((int) (16.66666 - millis));
			} catch (InterruptedException e) {
			}

			millis = System.nanoTime() - beforeTime;
			millis /= 1000000;

			fps = (int) (1000 / millis);
		}
	}

	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();

			if (dbImage != null && g != null) {
				g.drawImage(dbImage, 8, 30, null);
			}

			Toolkit.getDefaultToolkit().sync(); // For some operating systems
			g.dispose();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private void gameRender() {
		
		

		if (dbImage == null) { // Create the buffer
			dbImage = createImage(width, height);
			if (dbImage == null) {
				System.err.println("dbImage is still null!");
				return;
			} else {
				dbg = dbImage.getGraphics();
			}
		}

		pongImage = new PImage(dbImage);
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, width, height);

		state.draw(dbg);

		// dbg.drawString(Integer.toString((int) fps), 20, 20);
	}

	private void gameUpdate() {
		state.gameUpdate();
	}

	public void addNotify() {
		super.addNotify();
		startGame();
	}

	private void startGame() {
		if (game == null || !running) {
			game = new Thread(this);
			game.start();
			
			running = true;
		}
	}

}
