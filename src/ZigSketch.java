import java.util.Iterator;

import processing.core.PApplet;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;

import com.hookedup.processing.ExtraWindow;

public class ZigSketch extends ExtraWindow {

	private ZigList zigList;
	private int animWidth = 40;
	private int animHeight = 25;
	// all of the theme colors
	private ColorList pallete;
	private KNColors knCols;
	private int counter;

	public ZigSketch(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);

	}

	public void setup() {
		size(350, 300);
		knCols = new KNColors();
		frameRate(25);
		createPallette();
		createZigList();
		counter = 0;

	}

	private void createPallette() {
		pallete = KNColors.getPallete();
		// TODO Auto-generated method stub

	}

	private void createZigList() {
		//
		zigList = new ZigList(40, 25, pallete, 3,
				3, this);
/*		zigList = new ZigList(40, 25, pallete, (int) random(1, 4),
				(int) random(2, 6), this);
*/
	}

	public void draw() {
		// update once every 20 frames
		if (counter % 5 == 0) {
			if (zigList.allFinished) {
				zigList = null;

				// background(pallete.getRandom().toARGB());
				drawThBG();
				createZigList();
			} else {
				zigList.update();
			}
		}
		counter++;

	}

	private void drawThBG() {
		TColor startCol = pallete.getRandom();
		TColor endCol = pallete.getRandom();
		ColorGradient grad = new ColorGradient();
		grad.addColorAt(0, startCol);
		grad.addColorAt(40, endCol);
		ColorList list = grad.calcGradient(0, 40);
		int x = 0;
		for (Iterator i = list.iterator(); i.hasNext();) {
			TColor c = (TColor) i.next();
			stroke(c.toARGB());
			line(x, 0, x, 25);
			x++;
		}

	}

}
