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

import processing.core.PApplet;

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
	int MATRIX_COLS = 16;
	int MATRIX_ROWS = 25;

	LEDMatrix matrix;

	private JPanel contentPane;
	private JTextField txtSongName;

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
		/*
		 * CODE TO ADD CLOSE LISTENER addWindowListener(new WindowAdapter() {
		 * 
		 * @Override public void windowClosing(WindowEvent e) {
		 * loadFromCanvasTask = null;
		 * 
		 * matrix.end(); timer.cancel(); timer.purge();
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException ex) { // TODO
		 * Auto-generated catch block ex.printStackTrace(); }
		 * 
		 * System.exit(0); } });
		 */

		setupExtraWindow();
		matrixSetup();
		try {
			setupServer();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// TODO uncomment before running properly!
		// IS THIS CAUSING THE APP TO FAIL TO QUIT WHEN IT IS UNCOMMENTED?
		setupTimer();

	}

	void loadDefaultMatrix() {
		System.out.println("load default matrix");
		String tmpResult = matrix
				.loadMatrixFile("/Users/acolling/Desktop/default.xml");

		// .loadMatrixFile("C:/Documents and Settings/acolling.PUBLICISGROUPUK/Desktop/matrix/setup/default.xml");
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
		// try {
		// matrix.disconnectFromController();
		// System.out.println("disconnected from controller");
		// } catch (Error e) {
		// System.out
		// .println("error trying to disconnect from controller before conencting....");
		// System.out.println("maybe it wasnt connected?");
		// }
		// -- TO CONNECT --->>>
		// matrix.connectToController();

		matrix.refresh();

		matrix.emulatorDelay = 20;
		matrix.ui.setLocation(this.getLocation().x + this.getWidth(),
				this.getLocation().y);

		matrix.ui.setVisible(true);

	}

	void loadFromCanvas() {
		for (int iH = 0; iH < matrix.rows(); iH++) {
			for (int iW = 0; iW < matrix.cols(); iW++) {
				int cp = win.get((iW), (iH));
				int rr = (int) proc.red(cp);
				int red = (int) proc.red(cp);
				int green = (int) proc.green(cp);
				int blue = (int) proc.blue(cp);

				// win.logIt(iW+":"+iH+"red:"+rr+" green:"+green);
				// matrix.setRGB(iW, iH, (int) (proc.red(cp) * 255), (int) (proc
				// .green(cp) * 255), (int) (proc.blue(cp) * 255));
				matrix.setRGB(iW, iH, red, green, blue);
			}
		}
		matrix.refresh();
	}

	void setupExtraWindow() {
		// win = new MyExtraWindow(proc, "Matrix Setup", 0, 0);
		win = new DropsWindow(proc, "Matrix Setup", 500, 300);
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
				100);
	}

	// /////CHAT SERVER /////////

	class LoadFromCanvasTask extends TimerTask {
		public void run() {
			while (true) {
				loadFromCanvas();
			}

		}
	}
}
