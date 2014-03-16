import processing.core.PApplet;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
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
	private ColorList list;

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
		System.out.println("======ZIG CREATED====");
		System.out.println("StartY:"+startY);
		System.out.println("delay:"+delay);
		System.out.println("counter:"+counter);

		// make a color list for the zig zag
		ColorGradient grad = new ColorGradient();
		TColor startCol = theCol;
		TColor endCol = startCol.getInverted();
		grad.addColorAt(0, startCol);
		grad.addColorAt(40, endCol);
		list = grad.calcGradient(0, 40);

	}

	public void update() {

		// make sure we reach far side!
		int isGoingDown = 1;
		proc.strokeWeight(strokeW);
		//proc.stroke(theCol.toARGB());
		int yCount = 0;
		if (counter < zigWidth ) {

			int colMin = counter;
			if (colMin<0) colMin = 0;
			// int yPos;
			proc.stroke(list.get(colMin).toARGB());

			float yPos = zHeight
					- Math.abs(proc.frameCount % (2 * zHeight) - zHeight);
			//System.out.println("zig:" + yPos + " xpos:" + xPos + " strokeW:"
				//	+ strokeW);
			// fill(zigZag);
			// noStroke();
			// ellipse( 400 ,150 , zigZag, zigZag);
			// text("triangle wave = " + int(zigZag), 100, 200);
			// stroke(0);
			// strokeWeight(1);
			proc.point(counter, startY + yPos);

			// proc.point(xPos, yPos);
		} else {

			finished = true;
		}
		counter++;
	}
}
