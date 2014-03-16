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
	//all of the theme colors
	private ColorList pallete;
	private KNColors knCols;

	public ZigSketch(PApplet theApplet, String theName, int theWidth,
			int theHeight) {
		super(theApplet, theName, theWidth, theHeight);
		
	}

	public void setup() {
		size(350, 300);
		knCols = new KNColors();
		createPallette();
		createZigList();

	} 

	

	private void createPallette() {
		pallete = KNColors.getPallete(); 
		// TODO Auto-generated method stub
		
	}

	private void createZigList() {
		// 
		zigList = new ZigList(animWidth, animHeight, pallete, 3, 3, this);
		
		
		
	}

	public void draw() {
		zigList.update();
		
	}

	
}

