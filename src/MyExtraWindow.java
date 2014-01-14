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

	public MyExtraWindow(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);
		// initWin();

		// setupVectors();

	}

	private void setupVectors() {

		sex = new Vec2D[61];

		// ==s
		sex[0] = new Vec2D(8, 1);
		sex[1] = new Vec2D(7, 0);
		sex[2] = new Vec2D(6, 0);
		sex[3] = new Vec2D(5, 0);
		sex[4] = new Vec2D(4, 0);
		sex[5] = new Vec2D(4, 1);
		sex[6] = new Vec2D(3, 1);
		sex[7] = new Vec2D(3, 1);
		sex[8] = new Vec2D(3, 2);
		sex[9] = new Vec2D(3, 3);
		sex[10] = new Vec2D(3, 3);
		sex[11] = new Vec2D(3, 4);
		sex[12] = new Vec2D(4, 4);
		sex[13] = new Vec2D(5, 4);
		sex[14] = new Vec2D(6, 4);
		sex[15] = new Vec2D(7, 4);
		sex[16] = new Vec2D(7, 5);
		sex[17] = new Vec2D(7, 6);
		sex[18] = new Vec2D(6, 7);
		sex[19] = new Vec2D(5, 7);
		sex[20] = new Vec2D(4, 7);
		sex[21] = new Vec2D(3, 7);
		sex[22] = new Vec2D(2, 7);
		sex[23] = new Vec2D(2, 6);
		// sex//==e
		sex[24] = new Vec2D(10, 0);
		sex[25] = new Vec2D(10, 1);
		sex[26] = new Vec2D(10, 2);
		sex[27] = new Vec2D(10, 3);
		sex[28] = new Vec2D(10, 4);
		sex[29] = new Vec2D(10, 5);
		sex[30] = new Vec2D(10, 6);
		sex[31] = new Vec2D(10, 7);
		sex[32] = new Vec2D(11, 0);
		sex[33] = new Vec2D(12, 0);
		sex[34] = new Vec2D(13, 0);
		sex[35] = new Vec2D(14, 0);
		sex[36] = new Vec2D(15, 0);
		sex[37] = new Vec2D(11, 3);
		sex[38] = new Vec2D(12, 3);
		sex[39] = new Vec2D(12, 3);
		sex[40] = new Vec2D(13, 3);
		sex[41] = new Vec2D(11, 7);
		sex[42] = new Vec2D(12, 7);
		sex[43] = new Vec2D(13, 7);
		sex[44] = new Vec2D(14, 7);

		// ==x

		sex[45] = new Vec2D(17, 0);
		sex[46] = new Vec2D(18, 1);
		sex[47] = new Vec2D(19, 2);
		sex[48] = new Vec2D(20, 3);
		sex[49] = new Vec2D(21, 4);
		sex[50] = new Vec2D(22, 5);
		sex[51] = new Vec2D(23, 6);
		sex[52] = new Vec2D(24, 7);
		sex[53] = new Vec2D(24, 0);
		sex[54] = new Vec2D(23, 1);
		sex[55] = new Vec2D(22, 2);
		sex[56] = new Vec2D(21, 3);
		sex[57] = new Vec2D(20, 4);
		sex[58] = new Vec2D(19, 5);
		sex[59] = new Vec2D(18, 6);
		sex[60] = new Vec2D(17, 7);

	}

	PImage[] animation;
	private int rgbCount;

	public void setup() {
		size(350, 300);
		frameRate(20);
		// colorMode(HSB, 50, 100, 100);
		// letterCount = 0;
		// loopingGif = new GifAnimation(this, "ghostAnim.gif");
		// loopingGif.loop();
		// animation = Gif.getPImages(this, "ghostAnim.gif");

	}

	public void draw() {

		
		if (randomCol > 500) {
			randomCol = 0;
		} else {
			if (randomCol % 100 == 0) {
				rgbCount = randomCol / 100;
			}
		}
		switch (rgbCount) {
		case 0:
			background(255, 0, 0);
			break;
		case 1:
			background(0, 255, 0);
			break;
		case 2:
			background(0, 0, 255);
			break;
		case 3:
			background(255, 255, 255);
			break;
		case 4:
			background(0, 0, 0);
			break;
		case 5:
			background(255, 0, 0);
			break;
		default:
			break;
		}
		randomCol++;

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
