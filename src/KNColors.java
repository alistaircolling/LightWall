import java.util.ArrayList;
import java.util.Iterator;

import toxi.color.ColorList;
import toxi.color.ColorRange;
import toxi.color.TColor;
import toxi.color.theory.ColorTheoryRegistry;

public class KNColors {

	public static TColor PINK = TColor.newHex("ff00ee");
	public static TColor YELLOW = TColor.newHex("fbff1c");
	public static TColor ORANGE = TColor.newHex("FF9D00");
	public static TColor PURPLE = TColor.newHex("BF00FF");
	public static TColor BLUE = TColor.newHex("00AAFF");

	// private ColorRange range;
	// private ColorList sorted;

	public KNColors() {

	}

	// could add option to pass kind of pallete
	public static ColorList getListFromColor(TColor col, int tot ) {

/*//		ColorRange range = new ColorRange(
				ColorTheoryRegistry.SPLIT_COMPLEMENTARY
						.createListFromColor(col));*/
/*//		ColorRange range = new ColorRange(
				ColorTheoryRegistry.TETRAD
				.createListFromColor(col));*/
		ColorRange range = new ColorRange(
				ColorTheoryRegistry.ANALOGOUS
				.createListFromColor(col));
		ColorList sorted = range.getColors(tot).sortByDistance(false);
		return sorted;
	}

	// returns a list of themed colors
	public static ColorList getPallete() {
		// TODO Auto-generated method stub
		ColorList list = new ColorList();
		list.add(PINK);
		list.add(YELLOW);
		list.add(ORANGE);
		list.add(PURPLE);
		list.add(BLUE);
		
		return list; 
	}
}
