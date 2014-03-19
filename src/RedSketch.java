import java.util.Iterator;

import processing.core.PApplet;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;

import com.hookedup.processing.ExtraWindow;

public class RedSketch extends ExtraWindow {

	

	public RedSketch(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);

	}

	public void setup() {
	
		System.out.println("RED SKETCH LAUNCHED");
		

	}

	
	public void draw() {
		
		background(255, 0, 0);
	}

	

}
