import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import controlP5.Println;

import peasy.PeasyCam;
import processing.core.PVector;
import toxi.color.TColor;
import toxi.geom.AABB;
import toxi.geom.Rect;
import toxi.geom.Vec3D;
import toxi.geom.mesh.LaplacianSmooth;
import toxi.geom.mesh.Vertex;
import toxi.geom.mesh.WETriangleMesh;
import toxi.math.waves.SineWave;
import toxi.processing.ToxiclibsSupport;

public class KinectCanvas {

	private KinectToPlane sketch;
	private KinectController controller;
	public Rect rect;
	// private UserInterface ui;
	private SineWave wave;
	private SineWave wave2;
	private float rotation = 0;
	private PeasyCam cam;
	private float s;
	private PVector[] depthPoints;
	private float[] zVals;// used to store zvals for easing
	private float mouseRotation;
	private float mouseRotationX;
	private int pointsInBox = 0;
	private int topCount;
	private ToxiclibsSupport gfx;
	float camX, camY, camZ;
	private float colorCount;
	private PVector lastValidVect = new PVector(3000, 3000, 3000);
	public AABB hitArea;
	private int counter = 0;

	public KinectCanvas(KinectToPlane app) {

		sketch = app;
		controller = sketch.kinectController;
		// ui = sketch.ui;
		app.smooth();
		init();
	}

	private void init() {

		topCount = 0;
		wave = new SineWave(0, .005f, 1, 0);
		wave2 = new SineWave(.7f, .005f, 1, 0);
		gfx = new ToxiclibsSupport(sketch);
		// cam = new PeasyCam((PApplet)sketch, 0, 0, 0, 1000);
		// cam = new PeasyCam(sketch, 0,0,0,1000);

	}

	public void saveImage() {

		String str = "";

		try {
			Date date = new Date();
			String ss = date.toString();
			FileWriter outFile = new FileWriter("pointcloud_" + date.toString());

			PrintWriter out = new PrintWriter(outFile);

			// Also could be written as follows on one line //
			// PrintWriter out = new PrintWriter(new FileWriter(args[0]));

			for (int i = 0; i < depthPoints.length; i++) {
				PVector vect = depthPoints[i];
				out.println(vect.x + " " + vect.y + " " + vect.z + " ");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addRect() {
		Rect rect = new Rect();
		rect.width = 500;
		rect.height = 500;
		sketch.addRect(rect);

		// ui.addRect(rect);

	}

	public void draw() {

		if (controller.kinectConnected)
			// sketch.image(sketch.kinectController.kinect.depthImage(), 0, 0);
			if (sketch.showPointCloud) {
				getDepthPoints();
				// drawPointCloud();
				// drawMesh();
				drawSimple();
				/*
				 * sketch.fill(counter,1000,100); if (counter>16000){ counter =
				 * 0; } counter+=100; sketch.noStroke(); sketch.rect(0, 0, 100,
				 * 100);
				 */

			} else {
				if (!controller.kinectConnected) {

				}
			}

		// //draw a circle inside the rect
		// sketch.fill(TColor.RED.toARGB());
		// sketch.noStroke();
		// float wavVal = wave.update();
		// float wavVal2 = wave2.update();
		// sketch.ellipse(ui.rectWidth.getValue()*.5f,
		// ui.rectHeight.getValue()*.5f, 100*wavVal, 100*wavVal);
		// sketch.fill(TColor.CYAN.toARGB());
		// sketch.ellipse(ui.rectWidth.getValue()*.7f,
		// ui.rectHeight.getValue()*.7f, 200*wavVal2, 200*wavVal2);
		// sketch.fill(TColor.GREEN.toARGB());
		// sketch.ellipse(ui.rectWidth.getValue()*.2f,
		// ui.rectHeight.getValue()*.2f, 80*(1-wavVal2), 80*(1-wavVal2));
		//

		// sketch.rect(0,0,ui.rectSize, ui.rectSize);//

	}

	private void drawSimple() {
		int skip = (int) Math.floor(640 / 40);
		float newZ;
		float lastZ;
		float setZ = 0;
		float difference;
		for (int x = 0; x < 640 - skip; x += skip) {
			for (int y = 1; y < 480 - skip; y += skip) {
				PVector vect1 = depthPoints[x + (y * 640)];

				if (zVals == null) {
					zVals = new float[640 * 480];
				} else {
					newZ = vect1.z;// get val from camera
					lastZ = zVals[x + (y * 640)]; // get previous val from array
					difference = newZ - lastZ; //find difference
					difference *= 0.1f;
					setZ = lastZ + difference; //new value to set is prev value plus half the diff
					zVals[x + (y * 640)] = setZ; //store the new value

				}

				sketch.println("vect1:" + vect1.toString());
				int xPos = x;
				int yPos = y;
				sketch.noStroke();
				sketch.fill(setZ, 1000, 100);
				sketch.rect(xPos, yPos, skip, skip);

			}
		}

	}

	public void drawMesh() {

		sketch.background(0);
		sketch.lights();
		sketch.directionalLight(0, 0, 1000, 0, 10000, 500);
		sketch.specular(0, 0, 100);
		sketch.shininess(16);

		sketch.camera(camX, camY, camZ, 0, 0, 0, 0, 1, 0);
		// sketch.println("x:"+camX+" Y:"+camY+"z:"+camZ);

		hitArea.rotateY(sketch.radians(rotation));
		sketch.strokeWeight(2);
		sketch.stroke(3000, 1000, 100);
		sketch.noFill();
		gfx.box(hitArea);
		// mesh to go behind objects

		WETriangleMesh triMesh = null;
		WETriangleMesh triMesh2 = null;

		int pointsInBox = 0;
		float maxX = 0;
		float maxY = 0;

		int minZ = 800;
		float lastZ = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("POINT COUNT: ");
		sb.append(pointsInBox);
		String strI = sb.toString();

		// ui.pointCount.setText(strI);
		sketch.println("points:" + pointsInBox);

	}

	private boolean checkWithinBounds(PVector vect) {

		return false;

	}

	private boolean isAHole(PVector[] vects) {

		for (int i = 0; i < vects.length; i++) {
			PVector vect = vects[i];
			if (vect.x == 0 && vect.y == 0 && vect.z == 0) {
				return true;
			}
		}

		return false;
	}

	private void drawPointCloud() {

		// sketch.rotateX(sketch.radians(180));
		// sketch.rotateX(sketch.radians(90));
		// sketch.rotateY(sketch.radians(rotation));
		// sketch.rotateX(sketch.radians(180));
		// sketch.rotateZ(sketch.radians(rotation));
		rotation += 2;

		float theX;
		float theY;
		float theZ;

		int skip = 10;
		sketch.background(0);
		sketch.pushMatrix();

		// allow control via mouse
		if (sketch.cmdDown) {
			mouseRotation = sketch.map(sketch.mouseX, 0, sketch.width, -360,
					360);
			mouseRotationX = sketch.map(sketch.mouseY, 0, sketch.height, -180,
					180);
			s = sketch.map(sketch.mouseY, 0, sketch.height, -10, 10);
		}
		sketch.translate(sketch.width * .5f, sketch.height * .5f, -1000);
		sketch.rotateX(sketch.radians(180));
		sketch.translate(0, 0, 1000);

		sketch.rotateY(sketch.radians(mouseRotation));

		// rotateZ(radians(mouseRotation));
		sketch.translate(-300, -500, s * -1000);
		sketch.scale(s);

		int currPointsInBox = 0;

		if (!sketch.sequentialPoints) {
			topCount = depthPoints.length;
			sketch.strokeWeight(5);
			skip = 5;
		} else {
			sketch.strokeWeight(3);
			skip = 2;
		}

		if (currPointsInBox != pointsInBox) {
			pointsInBox = currPointsInBox;
			sketch.println("POINTS IN BOX:" + pointsInBox);
		}
		// TODO DRAW BOX HERE

	}

	private void getDepthPoints() {

		if (controller.kinectConnected) {

//			depthPoints = sketch.kinectController.kinect.depthMapRealWorld();
			depthPoints = sketch.kinectController.kinect.depthMapRealWorld();
		} else {
			try {
				// only load if null
				if (depthPoints == null) {
					depthPoints = parseTextFileToDepth();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private PVector[] parseTextFileToDepth() throws FileNotFoundException {
		PVector[] depthPoints = new PVector[307200];

		Scanner scan = new Scanner(new File("myNumbers.txt"));

		// Read each row.
		for (int i = 0; i < depthPoints.length; i++) {

			// For each number in the column, read a number
			// and put it in the array
			PVector vect = new PVector();
			// String obj = scan.next();
			vect.x = Float.parseFloat(scan.next());
			vect.y = Float.parseFloat(scan.next());
			vect.z = Float.parseFloat(scan.next());
			depthPoints[i] = vect;
			// advance the scanner to the next line (row).
			scan.nextLine();
		}
		return depthPoints;
	}

	private void drawHead(Head head) {

	}

}
