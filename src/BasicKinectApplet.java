import java.io.Console;

import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import toxi.color.TColor;

import com.hookedup.processing.ExtraWindow;

public class BasicKinectApplet extends ExtraWindow {

	private Kinect kinect;
	private float[] depthLookUp;
	
	private int threshold = 500;
	public boolean kinectRunning;

	
	public BasicKinectApplet(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);
		// initWin();
	}
	
	public void setup() {
//		size(640, 480, OPENGL);
		size(640, 480);
		frameRate(10);
		println("BasicKinectApplet setting up kinect");
		createLookupTable();
		kinect = new Kinect(this);
		kinect.start();
		kinect.enableRGB(true);
		kinect.enableDepth(true);
		kinect.processDepthImage(true);
		println("==========Kinect set up=============");
		kinectRunning = true;
	}
	
	
	private void createLookupTable() {
		depthLookUp = new float[2048];
		for (int i = 0; i < depthLookUp.length; i++) {
			depthLookUp[i] = rawDepthToMeters(i);
		}

	}

	public float rawDepthToMeters(int depthValue) {
		if (depthValue < 2047f) {
			return (float) (1.0f / ((double) (depthValue) * -0.0030711016f + 3.3309495161f));
		}
		return 0.0f;
	}

	public void draw() {
		background(0);
		PImage img = kinect.getVideoImage();
		// image(img, 0, 0);
		drawPoints();

	}

	private void drawPoints() {
		int skipX = 640/40;
		int skipY = round(480/25);
		int[] depth = kinect.getRawDepth();
		
		//used to calculate av position of object below threshold
		float sumX = 0;
		float sumY = 0;
		float count = 0;
		
		for (int x = 0; x < 640; x += skipX) {
			for (int y = 0; y < 480; y += skipY) {
				int offset = x + y * width;
				// Convert kinect data to world xyz coordinate
				int rawDepth = depth[offset];

				// println(rawDepth);

				if (rawDepth < threshold) {
					sumX += x;
					sumY += y;
					count++;
				}

//				if (count != 0) {
//					float avgX = sumX / count;
//					float avgY = sumY / count;
//					fill(255, 0, 0);
//					ellipse(avgX, avgY, 16, 16);
//				}

				PVector v = depthToWorld(x, y, rawDepth);
				// set color based on z
				float huez = x / 640;
				TColor col = TColor.RED.copy();
				float zDecimal = v.z / 3.3309495161f;
				col.setHSV(zDecimal, 100, 100);
				stroke(round(col.red() * 255), round(col.green() * 255),
						round(col.blue() * 255));
				strokeWeight(1);
				// set offsets half the screen size
				int xOffset = 320;
				int yOffset = 240;
				pushMatrix();
				// Scale up
				float factor = 640;
		// TRYING A NEW TRANSLATE		translate((v.x * factor) + xOffset, yOffset + (v.y * factor),
						//factor - v.z * factor);
				translate(x/skipX, y/skipY);
				// Draw a point
				point(0, 0);
				popMatrix();
			}
		}

	}

	private PVector depthToWorld(int x, int y, int depthValue) {

		final double fx_d = 1.0 / 5.9421434211923247e+02;
		final double fy_d = 1.0 / 5.9104053696870778e+02;
		final double cx_d = 3.3930780975300314e+02;
		final double cy_d = 2.4273913761751615e+02;

		PVector result = new PVector();
		double depth = depthLookUp[depthValue];// rawDepthToMeters(depthValue);
		result.x = (float) ((x - cx_d) * depth * fx_d);
		result.y = (float) ((y - cy_d) * depth * fy_d);
		result.z = (float) (depth);
		return result;
	}

	public void mousePressed() {
		println("mouse pressed");
	}

	public void mouseDragged() {
		println("mouse dragged");
	}

	public void mouseReleased() {
		println("mouse released");
	}

	public void keyPressed() {
		println("key pressed:" + key);
	}

}