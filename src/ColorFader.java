import java.util.ArrayList;
import java.util.Vector;

import processing.core.PApplet;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.ReadonlyTColor;
import toxi.color.TColor;
import toxi.geom.Vec2D;

import com.hookedup.processing.ExtraWindow;

public class ColorFader extends ExtraWindow {

	// the chance of getting loads of bounces
	public static final float CHANCE_OF_LOTS = 100;
	private static final float MAX_MANY_BOUNCES = 100;
	// the gradient should be somewhat wider than the screen
	public static final float GRADIENT_WIDTH = 50;
	public static final float GRADIENT_HEIGHT = 25;
	private int counter = 0;
	private ColorList colorList;
	private double maxSpeed = 1;
	private ColorGradient grad;
	private TColor bgCol;
	private ColorList l;
	private double currColIndex;
	private ColoredLine newColor;
	private double moveDir = .1f;
	private float chanceOfNewLine = 100;// how often a new line is created
	private ArrayList<ColoredLine> lines;
	private int maxBounces = 5;

	public ColorFader(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);

	}

	public void setup() {
		lines = new ArrayList<ColoredLine>();
		currColIndex = 0;
		System.out.println("COLOR FADE LAUNCHED");
		colorList = KNColors.getPallete();
		// createGradient();
		addRandomColor();

	}

	private void addRandomColor() {
		grad = new ColorGradient();
		// TODO add new
		bgCol = colorList.getRandom();
		TColor lineCol = colorList.getRandom();
		// choose another one if the same!
		while (lineCol == bgCol) {
			lineCol = colorList.getRandom();
		}
		newColor = new ColoredLine(lineCol);
		double rand = Math.random();
		double xVect = (rand * .1f) - .05f;
		System.out.println(rand + " XVECT:" + xVect);
		// newColor.xVect = xVect;
		// if (xVect < 0) {
		grad.addColorAt(0, newColor.color);
		grad.addColorAt(5, bgCol);
		// } else {
		// grad.addColorAt(35, bgCol);
		// grad.addColorAt(40, newColor.color);
		// }

	}

	private void createGradient() {
		grad = new ColorGradient();
		// make a background color
		TColor bgCol = TColor.GREEN.copy();
		grad.addColorAt(0, bgCol);
		grad.addColorAt(40, bgCol);

	}

	public void draw() {

		int random = (int) random(0, chanceOfNewLine);
		if (random == 1) {
			System.out.println("new line!");
			addNewLine();
		}

		updateLines();
		// create the gradiemnt
		grad = new ColorGradient();
		
		// iterate thru lines and add them to the gradient
		for (int i = 0; i < lines.size(); i++) {
			ColoredLine line = lines.get(i);
			//grad.addColorAt(line.currentPos, line.color);
			stroke(line.color.toARGB());
			//shift 5 left 
			line(line.currentPos.x-5, 0, line.currentPos.x-5, ColorFader.GRADIENT_HEIGHT);
		}

/*		grad.addColorAt((float) currColIndex, newColor.color);
		grad.addColorAt((float) (currColIndex + 4), bgCol);

		grad.addColorAt((float) (currColIndex - 4), bgCol);

		l = grad.calcGradient(0, 40);
		// updateGrads();

		for (int i = 0; i < 40; i++) {
			TColor lineCol = l.get(i);
			stroke(lineCol.toARGB());
			line(i, 0, i, 25);
		}
		if (currColIndex < 40) {
			currColIndex += moveDir;
		} else {
			moveDir = 0 - .1f;
			currColIndex += moveDir;
			// currColIndex = 0;
			// addRandomColor();
		}
*/
	}

	private void updateLines() {
		for (int i = 0; i < lines.size(); i++) {
			ColoredLine line = lines.get(i);
			line.update();
		}

	}

	private void addNewLine() {

		// add color
		ColoredLine line = new ColoredLine(colorList.getRandom());
		// add vector that is -1 to +1
		Vec2D ranVect = (Vec2D.randomVector().scale(2)).sub(new Vec2D(1, 1));
		line.vector = Vec2D.randomVector();
		System.out.println("VECTOR:" + line.vector.toString());
		// add start position - opposite to vector
		// if moving right to left put at the right
		if (line.vector.x < 0) {
			// put at the
			line.currentPos = new Vec2D(GRADIENT_WIDTH, 0);// nb y val
															// currentlky not
															// used
		} else {
			line.currentPos = new Vec2D(0, 0);
		}

		// NB ---------- WE ARE ONLY GOING TO USE X VECTOR ----------
		// add bounces
		line.bounces = (int) random(maxBounces);
		// add lots of bounces occassionally
		int lotsChance = (int) random(CHANCE_OF_LOTS);
		if (lotsChance == CHANCE_OF_LOTS) {
			// create a line with lots of bounces
			int manyBounces = (int) random(MAX_MANY_BOUNCES);
			line.bounces = manyBounces;
		}
		lines.add(line);

	}

	private void updateGrads() {

		for (int i = 0; i < l.size(); i++) {
			// get each color in the list

		}
		// TODO Auto-generated method stub

	}

}
