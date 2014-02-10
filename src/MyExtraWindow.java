import gifAnimation.Gif;
import gifAnimation.GifAnimation;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import toxi.color.ColorGradient;
import toxi.color.TColor;
import toxi.geom.Vec2D;

import com.hookedup.processing.ExtraWindow;

public class MyExtraWindow extends ExtraWindow {

	float counter = 0f;
	int randomCol = 0;
	int rectPos = 0;
	int slowAmt = 5;

	int displayWidth = 50;
	int currPos = 0;
	float speed = 1;
	TColor[] cols;
	ColorGradient grad;
	int trailLength = 20;
	ArrayList<Vec2D> trail;
	int currSketch = 0;
	int lastSketch = -1;
	int newSketch = -1;
	Vec2D[] sex;
	private int letterCount;
	private TColor opposite;
	private GifAnimation loopingGif;
	private Vec2D[] rVec;
	private Vec2D[] yVec;
	private Vec2D[] aVec;
	private Vec2D[] nVec;

	public MyExtraWindow(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);
		// initWin();

		setupVectors();

	}

	private void setupVectors() {

		// r
		rVec = new Vec2D[6];
		rVec[0] = new Vec2D(0, 3);
		rVec[1] = new Vec2D(0, 2);
		rVec[2] = new Vec2D(0, 1);
		rVec[3] = new Vec2D(0, 0);
		rVec[4] = new Vec2D(1, 0);
		rVec[5] = new Vec2D(2, 0);
		// y
		yVec = new Vec2D[9];
		yVec[0] = new Vec2D(0, 0);
		yVec[1] = new Vec2D(0, 1);
		yVec[2] = new Vec2D(1, 1);
		yVec[3] = new Vec2D(2, 0);
		yVec[4] = new Vec2D(2, 1);
		yVec[5] = new Vec2D(2, 2);
		yVec[6] = new Vec2D(2, 3);
		yVec[7] = new Vec2D(1, 3);
		yVec[8] = new Vec2D(0, 3);
		// r
		aVec = new Vec2D[10];
		aVec[0] = new Vec2D(0, 3);
		aVec[1] = new Vec2D(0, 2);
		aVec[2] = new Vec2D(0, 1);
		aVec[3] = new Vec2D(0, 0);
		aVec[4] = new Vec2D(1, 0);
		aVec[5] = new Vec2D(2, 0);
		aVec[6] = new Vec2D(2, 1);
		aVec[7] = new Vec2D(2, 2);
		aVec[8] = new Vec2D(2, 3);
		aVec[9] = new Vec2D(1, 2);
		
		// n
		nVec = new Vec2D[6];
		nVec[0] = new Vec2D(0, 3);
		nVec[1] = new Vec2D(0, 2);
		nVec[2] = new Vec2D(0, 1);
		nVec[3] = new Vec2D(0, 0);
		nVec[4] = new Vec2D(1, 0);
		nVec[5] = new Vec2D(2, 0);
		nVec[6] = new Vec2D(2, 1);
		nVec[7] = new Vec2D(2, 2);
		nVec[8] = new Vec2D(2, 3);
		

	}

	PImage[] animation;
	private int rgbCount;
	private PImage a;
	private int heartSize;
	private int maxHearts;
	

	public void setup() {
		size(350, 300);

		frameRate(2);
//		a = loadImage("heart.gif");
//		heartSize = 3;
//		maxHearts = 30;
//		//noLoop();
		// colorMode(HSB, 50, 100, 100);
		// letterCount = 0;
		// loopingGif = new GifAnimation(this, "ryann.gif");
		// loopingGif.loop();
		// animation = Gif.getPImages(this, "ghostAnim.gif");

	}

	public void draw() {
		if (heartSize>maxHearts) heartSize = 3;
		for(int i=0; i<heartSize; i++){
			
			drawHeart(i);
			
		}
		heartSize+=2;
		
		

		// image(loopingGif, 0, 0);//,;// height / 2 - loopingGif.height / 2);

		// Vec2D thePoint;
		//
		// thePoint = sex[letterCount];
		// point(thePoint.x, thePoint.y);
		// // rect(0, 0, 100, 100);
		// if (letterCount < 60) {
		// letterCount++;
		// } else {
		//
		// background(TColor.newRandom().toARGB());
		// stroke(TColor.newRandom().toARGB());
		// letterCount = 0;
		//
		// }

	}

	private void drawHeart(int i) {
		
		// heartsize id the ints
		//get the xpos
		int xPos = (16-i)/2;
		int yPos = (24-i)/2;
		
		background(200);
		image(a, xPos, yPos, a.width, a.width);
		
			
		
		
	}

	private void drawTrail() {
		Vec2D mouse0 = trail.get(0);
		int oppCol = getOppCol(mouse0);
		stroke(oppCol);
		ellipse(trail.get(0).x, trail.get(0).y, 3, 3);
		for (int i = 1; i < trail.size(); i++) {
			Vec2D vec = trail.get(i);
			oppCol = getOppCol(vec);
			stroke(oppCol);
			point(vec.x, vec.y);
		}

	}

	// returns opposite color for a given pixel pos
	private int getOppCol(Vec2D mouse0) {
		int currCol = get((int) mouse0.x, (int) mouse0.y);
		TColor oppCol = TColor.newARGB(currCol);
		oppCol = oppCol.getInverted();
		int retCol = oppCol.toARGB();
		return retCol;
	}

	public void addNewCol() {
		cols[0] = cols[1];
		cols[1] = cols[2];
		cols[2] = getRanCol();
		grad = new ColorGradient();
		grad.addColorAt(0, cols[0]);
		grad.addColorAt(displayWidth, cols[1]);
		grad.addColorAt(displayWidth * 2, cols[2]);
	}

	public void initCols() {
		cols = new TColor[3];

		cols[0] = getRanCol();
		cols[1] = getRanCol();
		cols[2] = getRanCol();

		grad = new ColorGradient();
		grad.addColorAt(0, cols[0]);
		grad.addColorAt(displayWidth, cols[1]);
		grad.addColorAt(displayWidth * 2, cols[2]);

	}

	TColor getRanCol() {
		TColor col = TColor.newRandom();

		return col;
	}
}
