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
	private float strokeW;

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
		System.out.println("startY:" + startY + " counter:" + counter
				+ " zigwidth:" + zigWidth);
		//make sure we reach far side!
		if ((counter * (zHeight)-1) < zigWidth) {
			int upDown; // is going up or down? always 0 or 1
			proc.strokeWeight(strokeW);
			// proc.stroke(0);
			proc.stroke(theCol.toARGB());
			for (int i = startCount; i < counter; i++) {
				upDown = counter % 2;
				int lineXStart = (counter - 1) * zHeight;
				int lineYStart = startY + (upDown * zHeight);
				int lineEndX = (counter) * zHeight;
				int flipVal = Math.abs(upDown - 1);
				int lineEndY = startY + (flipVal * zHeight);
				// proc.line(0, 0, 100, 100);
				proc.line(lineXStart, lineYStart, lineEndX, lineEndY);
			}
			counter++;
		} else {

			finished = true;
		}
	}
}
