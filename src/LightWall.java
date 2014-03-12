import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import processing.core.PApplet;
import toxi.color.TColor;

import com.hookedup.led.LEDMatrix;
import com.hookedup.processing.EQLevels;
import com.hookedup.processing.ProcessingAppLauncherMinim;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class LightWall extends BaseSwingFrameApp {
	
	//Tasks and timers
	LoadFromCanvasTask loadFromCanvasTask = new LoadFromCanvasTask();
	Timer timer;
	
	//Processing window  -what we are drawing on
	// ExtraWindow win;
	DropsWindow win;

	// --- added for matrix
	int MATRIX_COLS = 16;
	int MATRIX_ROWS = 25;

	//Used to send to controller
	LEDMatrix matrix;

	
	private JPanel contentPane;
	private JTextField txtSongName;
	
	private ChatServer chatServer;

	
	private KinectController kinectController;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		/*addWindowListener(new WindowAdapter() {
			//should handle the window closing
			//TODO check controller is getting disconnected1
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Window closing");
				loadFromCanvasTask = null;
				matrix.end();
				timer.cancel();
				timer.purge();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

				System.exit(0);
			}
		});*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnDemo = new JButton("Demo 1");
		btnDemo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runDemo1();
			}
		});
		btnDemo.setBounds(10, 11, 89, 23);
		contentPane.add(btnDemo);

		JButton btnPlayDemoSong = new JButton("Play Demo Song");
		btnPlayDemoSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playDemoSong();
			}
		});
		btnPlayDemoSong.setBounds(133, 11, 195, 23);
		contentPane.add(btnPlayDemoSong);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//button clicked
			}
		});
		btnStop.setBounds(10, 45, 89, 23);
		contentPane.add(btnStop);

		txtSongName = new JTextField();
		txtSongName.setText("/letithappen.mp3");
		txtSongName.setBounds(133, 33, 195, 20);
		contentPane.add(txtSongName);
		txtSongName.setColumns(10);

		setupExtraWindow();
		try {
			setupServer();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// moved -- matrixSetup();

	}

	
	
	void loadDefaultMatrix() {
		System.out.println("load default matrix");
		String tmpResult = matrix
				.loadMatrixFile("/Users/acolling/Desktop/default.xml");

		// .loadMatrixFile("C:/Documents and Settings/acolling.PUBLICISGROUPUK/Desktop/matrix/setup/default.xml");
		if (tmpResult.equals("")) {
			return;
		}
		System.out.println("Loading..");
	}

	void matrixSetup() {
		System.out.println("matrix Setup..");
		matrix = new LEDMatrix(MATRIX_COLS, MATRIX_ROWS, 24, 24, 1);
		loadDefaultMatrix();
/*		try {
			matrix.disconnectFromController();
			System.out.println("disconnected from controller");
		} catch (Error e) {
			System.out
					.println("error trying to disconnect from controller before conencting....");
			System.out.println("maybe it wasnt connected?");
		}*/
		// -- TO CONNECT --->>>
		// matrix.connectToController();

		matrix.refresh();

		matrix.emulatorDelay = 20;
		matrix.ui.setLocation(this.getLocation().x + this.getWidth(),
				this.getLocation().y);

		matrix.ui.setVisible(true);

		setupTimer();
		

	}

	
	//  iterates through the processing window and sets the same colors on the marix
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
	//	win = new MyExtraWindow(proc, "Matrix Setup", 0, 0);
		 win = new DropsWindow(proc, "Matrix Setup", 500, 300);
		// win.setVisible(false);
		matrixSetup();
	}

	

	// --- overrides the set process event to include loading minim from the
	// base
	public void setProc(PApplet theproc) {
		super.setProc(theproc);
	}

	void playDemoSong() {
		System.out.println("play demo song called");
	}

	
	void runDemo1() {
		if (proc == null) {
			System.out.print("NULL");
		} else {
			System.out.print("ACTIVE");
		}
	}

	/**
	 * Create the frame.
	 */
	
//this is used for websocket connections
	private void setupServer() throws InterruptedException, IOException {
	
			chatServer = new ChatServer(8885);
			chatServer.main(null);
	
		
	}

	void setupTimer() {
		timer = new Timer();
		timer.schedule(loadFromCanvasTask, 0, // initial delay
				100);
	}
	
	
	///////CHAT SERVER /////////

	class LoadFromCanvasTask extends TimerTask {
		public void run() {
			while (true) {
				loadFromCanvas();
			}

		}
	}
}
