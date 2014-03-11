import gifAnimation.GifAnimation;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import toxi.color.ColorGradient;
import toxi.color.ReadonlyTColor;
import toxi.color.TColor;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;
import toxi.physics2d.behaviors.AttractionBehavior;
import toxi.physics2d.behaviors.GravityBehavior;

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
	private float letterCount;
	private TColor opposite;
	private GifAnimation loopingGif;
	private Vec2D[] rVec;
	private Vec2D[] yVec;
	private Vec2D[] aVec;
	private Vec2D[] nVec;
	private Vec2D[][] ryanVec;

	PImage[] animation;
	private int rgbCount;
	private PImage a;
	private int heartSize;
	private int maxHearts;
	private int theScale = 3;
	TColor currCol;
	private boolean ryanSequence = false;
	// physics
	int NUM_PARTICLES = 10;

	VerletPhysics2D physics;
	private boolean heartsDrop = false;
	private boolean linesSequence = false;
	private boolean animLines = true;

	public MyExtraWindow(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);
		

		setupVectors();

	}

	private ArrayList<Vec2D[]> letterList;
	private int lineCount;
	private int maxLines = 30;
	private ReadonlyTColor bgCol;
	private ReadonlyTColor lineCol;
	private int theWidth = 32;
	private int theHeight = 25;
	private Vec2D movementVect;
	private Vec2D currentPos;
	private float maxVel = 2;
	
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
		// a
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
		nVec = new Vec2D[9];
		nVec[0] = new Vec2D(0, 3);
		nVec[1] = new Vec2D(0, 2);
		nVec[2] = new Vec2D(0, 1);
		nVec[3] = new Vec2D(0, 0);
		nVec[4] = new Vec2D(1, 0);
		nVec[5] = new Vec2D(2, 0);
		nVec[6] = new Vec2D(2, 1);
		nVec[7] = new Vec2D(2, 2);
		nVec[8] = new Vec2D(2, 3);
		
		letterList = new ArrayList<Vec2D[]>();
		letterList.add(rVec);
		letterList.add(yVec);
		letterList.add(aVec);
		letterList.add(nVec);
		letterList.add(nVec);

	}

	public void setup() {
		size(350, 300);
		letterCount = 0;

		frameRate(30);
		noStroke();
		setupPhysics();
		setupLines();
		
		// a = loadImage("heart.gif");
		// heartSize = 3;
		// maxHearts = 30;
		// //noLoop();
		// colorMode(HSB, 50, 100, 100);
		// letterCount = 0;
		// loopingGif = new GifAnimation(this, "ryann.gif");
		// loopingGif.loop();
		// animation = Gif.getPImages(this, "ghostAnim.gif");

	}

	private void setupLines() {
		bgCol = TColor.BLACK;
		lineCol = TColor.MAGENTA;
		lineCount = 0;
		movementVect = new Vec2D();
		currentPos = new Vec2D();
		newAnimLine();
		
	}

	private void setupPhysics() {
		physics = new VerletPhysics2D();
		physics.setDrag(0.05f);
		physics.setWorldBounds(new Rect(0, 0, 16, 25));
		// the NEW way to add gravity to the simulation, using behaviors
		physics.addBehavior(new GravityBehavior(new Vec2D(0, 0.015f)));
		

	}

	void addParticle() {
		VerletParticle2D p = new VerletParticle2D(Vec2D.randomVector().scale(1)
				.addSelf(random(0,16), 0));
		physics.addParticle(p);
		// add a negative attraction force field around the new particle
	//	physics.addBehavior(new AttractionBehavior(p, 20, -1.2f, 0.01f));
	}

	public void draw() {
	//	background(0);
		if (ryanSequence) {
			doRyanSequence();
		}
		if (heartsDrop) {

			drawPhysics();
		}
		if (linesSequence){
			drawLines();
		}
		if (animLines){
			plotLines();
		}
		// image(loopingGif, 0, 0);//,;// height / 2 - loopingGif.height / 2);

		// Vec2D thePoint;
		//
		// thePoint = sex[letterCount];
		// point(thePoint.x, thePoint.y);setu
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

	private void plotLines() {
		//check which direction we are going in and it we have reached the sides
		//moving right
		if (movementVect.x > 0 && currentPos.x >theWidth){
			println("moving off right");
			newAnimLine();
		}
		//moving left
		if (movementVect.x < 0 && currentPos.x <0){
			println("moving off left");
			newAnimLine();
		}
		//moving down
		if (movementVect.y > 0 && currentPos.y > theHeight){
			println("moving off bottom");
			newAnimLine();
		}
		//moving up
		if (movementVect.y < 0 && currentPos.y < 0){
			println("moving off top");
			newAnimLine();
		}
		
		//increment
		Vec2D dotToDraw = new Vec2D();
		dotToDraw = currentPos.add(movementVect);
		currentPos = dotToDraw;
		stroke(lineCol.toARGB());
		point(currentPos.x, currentPos.y);
		
	}

	private void newAnimLine() {
		println("new anim line");
		lineCol = TColor.newRandom();
		int startSide = (int) random(4);
		println(startSide);
		maxVel = .2f;
		theHeight = 25;
		theWidth = 32;
		switch (startSide) {
		//left side
		case 0:
			currentPos = new Vec2D(0, random(25));
			movementVect = new Vec2D(random(.5f, 2), random(0, (maxVel*2))-maxVel);
			break;
			//right side
		case 1:
			currentPos = new Vec2D(theWidth, round(random(0, theHeight)));
			movementVect = new Vec2D(0-random(.5f, maxVel), random(0, (maxVel*2))-maxVel);
			
			break;
			//top
		case 2:
			currentPos = new Vec2D(round(random(0, theWidth)), 0);
			movementVect = new Vec2D(random(0, (maxVel*2)-maxVel), random(0, maxVel));
			break;
			//bottom
		case 3:
			currentPos = new Vec2D(round(random(0, theWidth)), theHeight);
			movementVect = new Vec2D(random(0, (maxVel*2)-maxVel), 0-random(0, maxVel));
			
			break;

		default:
			break;
		}
		
		println("curr pos:"+currentPos.toString());
		println("movement:"+movementVect.toString());
		
	}

	private void drawLines() {
		if (lineCount<maxLines){
			drawLine();
			
		}else{
			clearLines();
			lineCount = 0;
		}
		
	}

	private void drawLine() {
		int fromX = 0;
		int toX = theWidth;
		int fromY = 0;
		int toY = theHeight;
		TColor oppCol;
		oppCol = lineCol.getComplement();
		oppCol = TColor.newRandom();
		stroke(oppCol.toARGB());
		strokeWeight(random(10));
		//decide if the line is going to be horizontal or vertical
		if (lineCount%2==0){
			//horizontal line
			fromY = (int) random(theHeight);
			toY = (int) random(theHeight);
			lineCol = oppCol.getComplement();
		}else{
			//vertical line
			fromX = (int) random(theWidth);
			toX = (int) random(theWidth);
			lineCol = oppCol.getLightened(random(1));
		}
		
		
		line(fromX, fromY, toX, toY);
		lineCount ++;
	}

	private void clearLines() {
		
		background(bgCol.toARGB());
		
		
	}

	private void drawPhysics() {
		if (physics.particles.size() < NUM_PARTICLES) {
			addParticle();
		}
		physics.update();
		for (VerletParticle2D p : physics.particles) {
			TColor ranCol = TColor.newRandom();
			fill(255);
			rect(p.x, p.y, 3, 4);
		}

	}

	private void doRyanSequence() {

		int floorer = floor(letterCount);

		switch (floorer) {
		case 0:

			currCol = TColor.newHSV(0, 40, 100);
			drawletter(rVec, theScale, currCol);
			
			break;
		case 1:
			currCol = TColor.newHSV(.1f, 40, 100);
			drawletter(yVec, theScale, currCol);
			break;
		case 2:
			currCol = TColor.newHSV(.2f, 40, 100);
			drawletter(aVec, theScale, currCol);
			break;
		case 3:
			currCol = TColor.newHSV(.3f, 40, 100);
			drawletter(nVec, theScale, currCol);
			break;
		case 4:
			currCol = TColor.newHSV(.15f, 40, 100);
			drawletter(nVec, theScale, currCol);
			break;

		default:
			break;
		}

		letterCount += 0.2f;
		if (letterCount >= 5) {
			letterCount = 0;
			theScale += 1;
			if (theScale > 6) {
				theScale = 1;
			}
		}

	}

	private void drawletter(Vec2D[] rVec2, int theScale, TColor currCol2) {
		TColor bg = currCol2.getComplement();
		background(bg.toARGB());
		int tWid = 3;
		int tHi = 4;
		int xPos;
		int yPos;
		int boxSize = theScale;
		for (int i = 0; i < rVec2.length; i++) {
			Vec2D thisVec = rVec2[i];
			// stroke(255);
			fill(currCol2.toARGB());
			xPos = (int) (thisVec.x * theScale);
			yPos = (int) (thisVec.y * theScale);

			rect(xPos, yPos, boxSize, boxSize);
			// point(thisVec.x, thisVec.y);
		}
		// TODO Auto-generated method stub
	}

	private void drawHeart(int i) {

		// heartsize id the ints
		// get the xpos
		int xPos = (16 - i) / 2;
		int yPos = (24 - i) / 2;

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
