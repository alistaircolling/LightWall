import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;


public class ZigList {

	private int aWidth;
	private int aHeight;
	private ColorList colorList;
	private int spacing;
	private int zigHeight;
	private int totZigs;
	private int maxDelay = 15;
	private PApplet sketch;

	public ZigList(int animWidth, int animHeight, ColorList pallete, int spacin, int zigHeigh, PApplet proc) {
	
			aWidth = animWidth;
			aHeight = animHeight;
			colorList = pallete;
			spacing  = spacin;
			zigHeight = zigHeigh;
			sketch = proc;
			
			
			
			generateZigs();
	}

	private void generateZigs() {
		//one color for all of zigs? - or maybe get another list
		TColor ranCol = colorList.getRandom();
		//generate a list from this 1 col so the zigs are similar
		ColorList similarList = KNColors.getListFromColor(ranCol, totZigs);
		//figure out how many will fit in vertically
		totZigs = (int) (aHeight / (zigHeight+spacing));
		for (int i = 0; i < totZigs; i++) {
			int startDelay = (int) Math.random()*maxDelay;
			Zig zig = new Zig(i*(zigHeight+spacing), zigHeight, similarList.get(i), startDelay, sketch, aWidth); 
		}
		
		
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

}
