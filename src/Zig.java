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

	public Zig(int starty, int zigHeight, TColor tColor, int startDelay,
			PApplet sketch, int wid) {
		proc = sketch;
		zHeight = zigHeight;
		startY = starty;
		zigWidth = wid;
		theCol = tColor;
		delay = startDelay;
		counter = 0 - startDelay;
	}

	public void update() {
		if (counter < zigWidth) {
			int upDown; // is going up or down? always 0 or 1
			proc.stroke(theCol.toARGB());
			for (int i = 0; i < counter; i++) {
				upDown = counter % 2;
				int lineXStart = counter * zHeight;
				int lineYStart = startY + (upDown * zHeight);
				int lineEndX = (counter + 1) * zHeight;
				int flipVal = Math.abs(upDown - 1);
				int lineEndY = startY + (flipVal * zHeight);
				proc.line(lineXStart, lineYStart, lineEndX, lineEndY);
				counter++;
			}
		}
	}
}
