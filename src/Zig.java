import java.awt.Stroke;

import processing.core.PApplet;
import toxi.color.TColor;

public class Zig {
	private int zHeight;
	private TColor theCol;
	private int delay;
	private int counter;
	private PApplet proc;
	private int zigWidth;
	private int startY;
	private int startCount;
	public boolean finished;
	private int strokeW;

	public Zig(int starty, int zigHeight, TColor tColor, int startDelay,
			PApplet sketch, int wid, int strok) {
		proc = sketch;
		zHeight = zigHeight;
		startY = starty;
		zigWidth = wid;
		theCol = tColor;
		delay = startDelay;
		counter = 0 - startDelay;
		startCount = counter;
		strokeW = strok;
	}

	public void update() {
	
		// make sure we reach far side!
		int isGoingDown = 1;
		proc.strokeWeight(strokeW);
		proc.stroke(theCol.toARGB());
		int yCount = 0;
		int xPos = 0;
		if (counter < zigWidth) {

		//	int yPos;
			
			
			  float yPos = zHeight - Math.abs(proc.frameCount % (2*zHeight) - zHeight);
			  System.out.println("zig:"+yPos+" xpos:"+xPos+" strokeW:"+strokeW);
			  //fill(zigZag);
			  //noStroke();
			  //ellipse( 400 ,150 , zigZag, zigZag);
			  //text("triangle wave = " + int(zigZag), 100, 200);
			 // stroke(0);
			  //strokeWeight(1); 
			  proc.point(counter, startY + yPos);
			  xPos++;//=strokeW;//strokewidth is the speed;
			  
//			proc.point(xPos, yPos);
		} else {

			finished = true;
		}
		counter++;
	}
}
