import java.util.ArrayList;

import SimpleOpenNI.SimpleOpenNI;

public class KinectController {

	private KinectToPlane mainSketch;
	public SimpleOpenNI kinect;
	public ArrayList<Head> heads;
	public boolean kinectConnected = true;

	public KinectController(KinectToPlane sketch) {

		mainSketch = sketch;

		init();
	}

	private void init() {

		if (kinectConnected) {
			kinect = new SimpleOpenNI(mainSketch);
			kinect.enableDepth();
			kinect.enableRGB();
			kinect.enableUser(SimpleOpenNI.NODE_NONE); //FIXME was skel_profile_none -= deprecated???
		}
		mainSketch.println("KINECT INITIALISED");

	}

	public void draw() {

		if (kinectConnected) kinect.update();

		// check if we are using the user list or the point cloud

		

	}

	
//
//	void onEndCalibration(int userID, boolean successful) {
//		if (successful) {
//			mainSketch.println("user calibrated!");
//			kinect.startTrackingSkeleton(userID);
//		} else {
//			mainSketch.println("user calibration failed");
//		}
//	}
//
//	void onNewUser(int userId) {
//
//		mainSketch.println("new user:" + userId);
//	}

}
