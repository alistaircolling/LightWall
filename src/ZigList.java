import java.util.ArrayList;

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
	private ArrayList<Zig> list;
	private TColor bgCol;
	public boolean allFinished;
	private float strokeW;

	public ZigList(int animWidth, int animHeight, ColorList pallete, int spacin, int zigHeigh, PApplet proc) {
	
			aWidth = animWidth;
			aHeight = animHeight;
			colorList = pallete;
			spacing  = spacin;
			zigHeight = zigHeigh;
			sketch = proc;
			strokeW = proc.random(1, 1);//TODO make varibla
			
			totZigs = (int) Math.ceil(animHeight/(zigHeight+spacin))+3;
			
			generateZigs();
	}

	private void generateZigs() {
		//one color for all of zigs? - or maybe get another list
		TColor ranCol = colorList.getRandom();
		//generate a list from this 1 col so the zigs are similar
		ColorList similarList = KNColors.getListFromColor(ranCol, totZigs);
		//FIXME make sure that the bgcol isnt the zigzag col
		bgCol = colorList.getRandom();
		//figure out how many will fit in vertically
		//totZigs = (int) (aHeight / (zigHeight+spacing));
		list = new ArrayList<Zig>();
		for (int i = 0; i < totZigs; i++) {
			int startDelay = (int) Math.random()*maxDelay;
			Zig zig = new Zig(i*(zigHeight+spacing), zigHeight, similarList.get(i), startDelay, sketch, aWidth, (int) strokeW); 
			list.add(zig);
		}
		
		
		
	}

	public void update() {
	//	sketch.stroke(0);
	//	sketch.line(0,0,10,10);
		
		for (int i = 0; i < list.size(); i++) {
			Zig zig = list.get(i);
			//check if finsihed then startc again //TODO make better! - check all
			if (zig.finished){
				allFinished = true;
			}else{
				zig.update();
			}
		}
		
	}

}
