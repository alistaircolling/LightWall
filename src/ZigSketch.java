import java.util.ArrayList;

import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.geom.Vec2D;

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
		zigList = new ZigList(40, 25, pallete, (int) random(2, 6),  (int) random(2, 6), this);

	}

	public void draw() {
		// update once every 20 frames
		if (counter % 5 == 0) {
			if (zigList.allFinished) {
				zigList = null;
				
				background(pallete.getRandom().toARGB());
				createZigList();
			} else {
				zigList.update();
			}
		}
		counter++;

	}

}
