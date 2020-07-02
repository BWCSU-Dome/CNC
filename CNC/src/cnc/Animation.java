package cnc;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Animation {
	
	protected static Circle temp;

	protected static double getDauer(double geschwinCMproSek, double a, double b) {
		double c_quad = a*a + b*b;
		double c = Math.sqrt(c_quad);
		
		return c/geschwinCMproSek;
	}
}
