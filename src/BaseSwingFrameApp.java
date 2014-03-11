
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import processing.core.PApplet;

import com.hookedup.processing.IProcessingApp;
import com.hookedup.processing.ProcessingAppLauncher;


public class BaseSwingFrameApp extends JFrame implements IProcessingApp {

	private JPanel contentPane;
	PApplet proc;
	private JList list;
	private DefaultListModel newModel;
	
	//--- This function is called by the launcher when it opens the window
	//  this provides a handle to the processing applet, needed for most libraries
	public void setProc(PApplet theproc){
		proc = theproc;
	}
	
	public void showApp(){
		this.setVisible(true);
	}

	void runDemo1(){
		if(proc==null){
			System.out.print("NULL");
		} else {
			System.out.print("ACTIVE");
		}
	
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcessingAppLauncher  procLaunch =  new ProcessingAppLauncher();
					procLaunch.launch("BaseSwingFrameApp");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void systemExit(){
		System.exit(0);
	}
	
	/**
	 * Create the frame.
	 */
	public BaseSwingFrameApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDemo = new JButton("Clear List");
		btnDemo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearList();
			}
		});
		btnDemo.setBounds(10, 11, 89, 23);
		contentPane.add(btnDemo);
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startUp();
			}
		});
		
		startButton.setBounds(155, 230, 89, 23);
		contentPane.add(startButton);
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopIt();
			}
		});
		
		stopButton.setBounds(155, 250, 89, 23);
		contentPane.add(stopButton);
		createList();
		createDropDown();
		
	}

	protected void stopIt() {
		System.out.println("Stop!");
		
	}

	protected void startUp() {
		System.out.println("Start!");
		// TODO Auto-generated method stub
		
	}

	private void createList() {
		list = new JList(new DefaultListModel() {

		      String[] companies = {"item1", "item2", "item3"};
		      @Override
		      public int getSize() {
		      return companies.length;
		      }

		      @Override
		      public Object getElementAt(int index) {
		      return companies[index];
		      }
		      });
		      contentPane.add(list);
		      list.setBounds(160, 10, 250, 200);
		      clearList();


		
	}
	
	private void clearList() {
			  newModel = new DefaultListModel();
			  list.setModel(newModel);
	        
	}
	

	private void createDropDown() {
		
		String[] petStrings = { "shadows", "drawing", "tubes", "pong", "giftime" };

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		final JComboBox petList = new JComboBox(petStrings);
		petList.setSelectedIndex(4);
		petList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object item = petList.getSelectedItem();
				String itemName = item.toString();
				addItemToList(itemName);
			}
		});
		petList.setBounds(10, 50, 89, 23);
		contentPane.add(petList);

		
	}

	protected void addItemToList(String itemName) {
		System.out.println("add "+itemName);
		newModel.addElement(itemName);
		
	}

}
