import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import pong.Pong;
import processing.core.PApplet;
import processing.core.PImage;
import toxi.color.TColor;

import com.hookedup.led.LEDMatrix;
import com.hookedup.processing.EQLevels;
import com.hookedup.processing.ExtraWindow;
import com.hookedup.processing.ProcessingAppLauncherMinim;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;

public class LightWall extends BaseSwingFrameApp {

	LoadFromCanvasTask loadFromCanvasTask = new LoadFromCanvasTask();
	Timer timer;

	// ExtraWindow win;
	DropsWindow win;

	// --- added for matrix
	int MATRIX_COLS = 40;
	int MATRIX_ROWS = 25;

	LEDMatrix matrix;

	private JPanel contentPane;
	private JTextField txtSongName;
	private PImage lastScreen;
	private Pong pong;

	// FIXME add chatserver class
	// private ChatServer chatServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// AppSoundDemoSwing frame = new AppSoundDemoSwing();
					// frame.setVisible(true);

					// ProcessingAppLauncher procLaunch = new
					// ProcessingAppLauncher();
					// NOTE: Using Minim version
					ProcessingAppLauncherMinim procLaunch = new ProcessingAppLauncherMinim();
					procLaunch.launch("LightWall");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LightWall() {

		// setup processing applet
		setupExtraWindow();

		//PONG
		setupPong();
		
		// setup the controller to connect to
		matrixSetup();

		// sets colors on matrix from processing applet
		setupTimer();
		
		/*
		 * // setup chat server for API try { setupServer(); } catch
		 * (InterruptedException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } catch (IOException e1) {
		 * e1.printStackTrace(); }
		 */

	}

	private void setupPong() {
		
		pong = new Pong();
		
		
	}

	void loadDefaultMatrix() {
		System.out.println("load default matrix");
		String tmpResult = matrix
		//		.loadMatrixFile("/Users/acolling/Desktop/default.xml");

		 .loadMatrixFile("C:/Documents and Settings/acolling.PUBLICISGROUPUK/Desktop/matrix/setup/default.xml");
		if (tmpResult.equals("")) {
			// System.out.println("File Loaded.");
			return;
		}
		System.out.println("Loading..");
	}

	void matrixSetup() {
		System.out.println("matrix Setup..");
		matrix = new LEDMatrix(MATRIX_COLS, MATRIX_ROWS, 24, 24, 1);
		loadDefaultMatrix();

		// -- TO CONNECT --->>>
		matrix.connectToController();

		this.setLocation(0, 0);
		matrix.refresh();

		matrix.emulatorDelay = 20;
		matrix.ui.setLocation(this.getLocation().x + this.getWidth(),
				this.getLocation().y);

		matrix.ui.setVisible(true);

		int newX = matrix.ui.getLocation().x;
		int newY = matrix.ui.getLocation().x;
		// win.setLocation(newX, newY);
		System.out.println("matrix setup");

	}

	void loadFromCanvas() {

	//if (lastScreen != null) {
			for (int iH = 0; iH < matrix.rows(); iH++) {
				for (int iW = 0; iW < matrix.cols(); iW++) {
			//		win.background(0);
					//doing pong so gonna get PIMg
					PImage pongImg = pong.pongImage;
					
					win.image(pongImg, 0,0,40,25);
		//			int cp = pongImg.get((iW*10), (iH*10));
					
					int cp = win.get((iW), (iH));
					
					int red = (int) proc.red(cp);
					int green = (int) proc.green(cp);
					int blue = (int) proc.blue(cp);
					
				//	if (TColor.BLACK.toARGB() != cp) {
						// matrix.setRGB(iW, iH, 255, 255, 255);
						matrix.setRGB(iW, iH, red, green, blue);
				//	}

				}
			}
//		} else {
//			// store the last PIMG
//			lastScreen = win.get();
//		}
		matrix.refresh();
	}

	void setupExtraWindow() {
		// win = new MyExtraWindow(proc, "Matrix Setup", 0, 0);
		 win = new DropsWindow(proc, "Processing sketch", 500, 300);
		//win = new BasicKinectApplet(proc, "Processing Sketch", 640, 480);

		// win.setVisible(false);

	}

	// --- overrides the set process event to include loading minim from the
	// base
	public void setProc(PApplet theproc) {
		super.setProc(theproc);
	}

	// this is used for websocket connections
	private void setupServer() throws InterruptedException, IOException {
		// FIXME add the Chatserver code
		// chatServer = new ChatServer(8885);
		// chatServer.main(null);

	}

	void setupTimer() {
		timer = new Timer();
		timer.schedule(loadFromCanvasTask, 0, // initial delay
				500);
	}

	// /////CHAT SERVER /////////

	class LoadFromCanvasTask extends TimerTask {
		public void run() {
			while (true) {
				loadFromCanvas();
/*
				if (((BasicKinectApplet) win).kinectRunning) {

					loadFromCanvas();
				} else {
					// System.out.println("not kinect not up");
				}*/

			}

		}
	}
}
