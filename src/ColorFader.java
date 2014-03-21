import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PImage;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.math.CosineInterpolation;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;

import com.hookedup.processing.ExtraWindow;

public class ColorFader extends ExtraWindow {

	// the chance of getting loads of bounces
	public static final float CHANCE_OF_LOTS = 100;
	private static final float MAX_MANY_BOUNCES = 100;
	// the gradient should be somewhat wider than the screen
	public static final float GRADIENT_WIDTH = 1000;
	public static final float GRADIENT_HEIGHT = 700;
	private static final int MIN_LINES = 3;
	private int counter = 0;
	private ColorList colorList;
	private double maxSpeed = 1;
	private ColorGradient grad;
	private TColor bgCol;
	private ColorList listToDraw;
	private double currColIndex;
	private ColoredLine newColor;
	private double moveDir = .1f;
	private float chanceOfNewLine = 50;// how often a new line is created
	private ArrayList<ColoredLine> lines;
	private int maxBounces = 5;
	private PImage lastScreen;
	private VerletPhysics2D physics;

	public ColorFader(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);

	}

	public void setup() {

		physics = new VerletPhysics2D();
		physics.setWorldBounds(new Rect(0, 0, 1000, 700));

		smooth();
		lines = new ArrayList<ColoredLine>();
		currColIndex = 0;
		lastScreen = get();
		System.out.println("COLOR FADE LAUNCHED");
		colorList = KNColors.getPallete();
		addNewLine();
	}

	public void draw() {

		
		physics.update();
		
		ArrayList<Integer> addedNums = new ArrayList<Integer>();
		// create the gradiemnt
		grad = new ColorGradient();
		// grad.setInterpolator(new DecimatedInterpolation(50));
		grad.setInterpolator(new CosineInterpolation());
		
		//iterate thru all lines (particles actually)
		
		 // draw all particles
		  for(Iterator i=lines.iterator(); i.hasNext();) {
			  ColoredLine line = (ColoredLine) i.next();
			  
		    // selected particle in cyan, all others in black
			  noStroke();
		    fill(line.color.toARGB());
		    line.particle.update();
		    ellipse(line.particle.x,line.particle.y,5,5);
		  }
		
		/*if (lines.size() > 0) {

			// iterate thru lines and add them to the gradient
			for (int i = 0; i < lines.size(); i++) {
				ColoredLine line = lines.get(i);
				
				 * // code for just drawing lines stroke(line.color.toARGB());
				 * // shift 5 left line(line.currentPos.x - 5, 0,
				 * line.currentPos.x - 5, ColorFader.GRADIENT_HEIGHT);
				 
				// check we havent already added a line here
				if (!addedNums.contains((int) line.currentPos.x)) {
					grad.addColorAt(line.currentPos.x, line.color);
					addedNums.add((int) line.currentPos.x);
				}
			}

			listToDraw = grad.calcGradient(0, 1000);// 5 extra each side
			// target middle of grad
			for (int i = 0; i < 1000; i++) {

				float ratio = 1;

				TColor targetCol = listToDraw.get(i);
				int lastCol = lastScreen.get(i, 0);
				TColor tLastCol = TColor.newARGB(lastCol);

				float lastRed = tLastCol.red();
				float lastGreen = tLastCol.green();
				float lastBlue = tLastCol.blue();

				float newRed = targetCol.red();
				float newGreen = targetCol.green();
				float newBlue = targetCol.blue();

				int red = (int) Math.abs((ratio * newRed)
						+ ((1 - ratio) * lastRed));
				int green = (int) Math.abs((ratio * newGreen)
						+ ((1 - ratio) * lastGreen));
				int blue = (int) Math.abs((ratio * newBlue)
						+ ((1 - ratio) * lastBlue));

				stroke(targetCol.toARGB());
				// stroke(red, green, blue);
				line(i, 0, i, GRADIENT_HEIGHT);
			}

		} else {
			System.out.println(" no lines");
		}
*/
	//	updateLines();
		int random = (int) random(0, chanceOfNewLine);
		if (random == 1) {
			System.out.println("new line!");
			addNewLine();
		}

		lastScreen = get();

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
		if (lines.size() < MIN_LINES) {
			addNewLine();
		}

	}

	private void addNewLine() {

		// add color
		ColoredLine line = new ColoredLine(colorList.getRandom());
		
		
		// add vector that is -1 to +1
		Vec2D ranVect = (Vec2D.randomVector().scale(2)).sub(new Vec2D(1, 1));
		line.vector = Vec2D.randomVector();
		
		
		line.particle = new VerletParticle2D(Vec2D.randomVector().scale(1000));
		line.particle.add(ranVect.scale(100));//particle speed can add var
		physics.addParticle(line.particle);
		
		
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
