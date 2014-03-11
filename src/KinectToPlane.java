import java.util.ArrayList;

import processing.app.tools.MovieMaker;
import processing.core.PApplet;
import toxi.color.TColor;
import toxi.geom.Rect;
import controlP5.ControlP5;

public class KinectToPlane extends PApplet {

	public KinectController kinectController;
	UserInterface ui;
	private ControlP5 ctrl;

	private int bgCol;
	public KinectCanvas kinectCanvas;
	private ArrayList<Rect> rects;
	public boolean showPointCloud = true;
	private float hue = 0;
	public boolean cmdDown;
	public boolean sequentialPoints;
	private int pointCloudVis;
	public boolean drawPointCloud;
	public int zPos;
	private int[] zVals;

	public void setup() {
		size(640, 480, OPENGL);
		colorMode(HSB, 4000, 1000, 100);
		

	//	ui = new UserInterface(this);
		kinectController = new KinectController(this);
		println("kinect controller setup");

		// bgCol = TColor.newRandom().toARGB();
		bgCol = TColor.MAGENTA.toARGB();
		kinectCanvas = new KinectCanvas(this);

	}

	public void draw() {
		
		
		
		//background(60, 0, 100);
		// update the kinect info
		pushMatrix();
		kinectController.draw();
		kinectCanvas.draw();
	
		popMatrix();
		//noLights();
		//ui.draw();

	}

	public void mousePressed() {

	}

	public void mouseDragged() {
		// println("mouse dragged");
	}

	public void mouseReleased() {
		// println("mouse released");
	}

	public void keyReleased() {
		if (keyCode==157){
			cmdDown = false;
		}
	}

	public void keyPressed() {
		println("key pressed:" + keyCode);
		switch (keyCode) {
		//
		case 67:
			ui.toggleShow();
		//	drawPointCloud = !drawPointCloud;
			
			break;
		case 157:
			cmdDown = true;
			break;
		case 38:
			//increase
			zPos += 100;
			break;
		case 40:
			zPos -= 100;
			//decreas
			break;

		default:
			break;
		}

	}

	public void addRect(Rect rect) {

		rects.add(rect);

	}

	public void togglePointCloud(float value) {
		println("toggle:" + value);
		if (value == 0) {
			showPointCloud = false;
		} else {
			showPointCloud = true;
		}

	}

};
