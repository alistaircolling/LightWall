import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.geom.Vec2D;
import toxi.math.CosineInterpolation;
import toxi.math.DecimatedInterpolation;

import com.hookedup.processing.ExtraWindow;

public class ColorFader extends ExtraWindow {

	// the chance of getting loads of bounces
	public static final float CHANCE_OF_LOTS = 10000;
	private static final float MAX_MANY_BOUNCES = 100;
	// the gradient should be somewhat wider than the screen
	public static final float GRADIENT_WIDTH = 1000;
	public static final float GRADIENT_HEIGHT = 700;
	private static final int MIN_LINES = 2;
	private static final int MAX_LINES = 20;
	private int counter = 0;
	private ColorList colorList;
	private double maxSpeed = 1;
	private ColorGradient grad;
	private TColor bgCol;
	private ColorList listToDraw;
	private double currColIndex;
	private ColoredLine newColor;
	private double moveDir = .1f;
	private float chanceOfNewLine = 500;// how often a new line is created
	private ArrayList<ColoredLine> lines;
	private int maxBounces = 5;
	private int rotCounter;

	public ColorFader(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);

	}

	public void setup() {

		lines = new ArrayList<ColoredLine>();
		currColIndex = 0;
		System.out.println("COLOR FADE LAUNCHED");
		colorList = KNColors.getPallete();
		addNewLine();
		smooth();

	}

	public void draw() {

		ArrayList<Integer> addedNums = new ArrayList<Integer>();
		// create the gradiemnt
		grad = new ColorGradient();
		// grad.setInterpolator(new DecimatedInterpolation(50));
		grad.setInterpolator(new CosineInterpolation());
		if (lines.size() > 0) {

			// iterate thru lines and add them to the gradient
			for (int i = 0; i < lines.size(); i++) {
				ColoredLine line = lines.get(i);
				/*
				 * // code for just drawing lines stroke(line.color.toARGB());
				 * // shift 5 left line(line.currentPos.x - 5, 0,
				 * line.currentPos.x - 5, ColorFader.GRADIENT_HEIGHT);
				 */
				// check we havent already added a line here
				if (!addedNums.contains((int) line.currentPos.x)) {
					grad.addColorAt(line.currentPos.x, line.color);
					addedNums.add((int) line.currentPos.x);
				}
			}
			pushMatrix();
		//	rotate(radians(90));
			
			// translate(100, 0);
			listToDraw = grad.calcGradient(0, 1000);// 5 extra each side
			// target middle of grad
			for (int i = 475; i < 525; i++) {
				TColor col = listToDraw.get(i);
				stroke(col.toARGB());
				line(i - 475, 0, i - 475, GRADIENT_HEIGHT );
			}
			popMatrix();

		} else {
			System.out.println(" no lines");
		}

		updateLines();
		int random = (int) random(0, chanceOfNewLine);
		if ((lines.size() < (MIN_LINES * lines.size())) && random == 1) {
			System.out.println("new line!");
			addNewLine();
		}

	}

	private void updateLines() {

		for (Iterator i = lines.iterator(); i.hasNext();) {
			ColoredLine line = (ColoredLine) i.next();
			line.update();
			if (line.removeMe) {
				i.remove();
				System.out.println("removed line total lines:" + lines.size());
			}
		}
		if (lines.size() > MAX_LINES) {
			lines.remove(0);
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

}
