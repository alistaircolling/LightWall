

import processing.core.PVector;
import toxi.color.TColor;


public class Head {
	
	public int id;
	public PVector pos;
	public TColor col;
	
	public Head(int theId) {
		id = theId;
		col  = TColor.newRandom();
		pos = new PVector();
		
	}

}
