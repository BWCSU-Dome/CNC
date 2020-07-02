package cnc;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Animation {
	
	private static Circle temp;
	
	public static void kreis(double x, double y, double i, double j) {
		
	}
	
	public static void line( double xEnd, double yEnd) {
		double xStart = GUI.Kopf.getCenterX();
		double yStart = GUI.Kopf.getCenterY();
		
		
		Line line = new Line();
		line.setStartX(xStart);
		line.setStartY(yStart);
		line.setEndX(xStart);
		line.setEndY(yStart);
		line.setStrokeWidth(10);
		line.setStroke(Color.PINK);

		GUI.arbeitsF.getChildren().add(line);
		line.toBack();
		if(!(temp == null)) {
			temp.setVisible(false);
		}
		
		Circle cir = new Circle(GUI.Kopf.getCenterX(), GUI.Kopf.getCenterY(),GUI.Kopf.getRadius(),GUI.Kopf.getFill());
		GUI.Kopf.setVisible(false);
		cir.toBack();
		GUI.arbeitsF.getChildren().add(cir);
		temp = cir;
		
		Timeline t = new Timeline();	
		System.out.println(xStart-xEnd + "  "+ (yStart-yEnd) +" " +getDauer( 70      ,xStart-xEnd,yStart-yEnd));
		System.out.println(Math.sqrt((xStart-xEnd)*(xStart-xEnd)+(yStart-yEnd)*(yStart-yEnd)) +"  " +getDauer( 700      ,xStart-xEnd,yStart-yEnd)*700);
		t.getKeyFrames().add(new KeyFrame(Duration.seconds(getDauer( 70      ,xStart-xEnd,yStart-yEnd)), 
				new KeyValue(line.endXProperty(), xEnd),
				new KeyValue(line.endYProperty(), GUI.getHight() - yEnd),
				new KeyValue(cir.centerXProperty(), xEnd),
				new KeyValue(cir.centerYProperty(), GUI.getHight()- yEnd)
				));
		t.play();
	
			GUI.Kopf.setCenterX(xEnd);
			GUI.Kopf.setCenterY(GUI.getHight() - yEnd);
	}
	
	private static double getDauer(double geschwinCMproSek, double a, double b) {
		double c_quad = a*a + b*b;
		double c = Math.sqrt(c_quad);
		
		return c/geschwinCMproSek;
	}
}
