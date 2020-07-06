package cnc;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class LineAnimation extends Animation{
	public static void line( double xEnd, double yEnd) {
		double xStart = GUI.Kopf.getCenterX();
		double yStart = GUI.Kopf.getCenterY();
		
		
		Line line = new Line();
		line.setStartX(xStart);
		line.setStartY(yStart);
		line.setEndX(xStart);
		line.setEndY(yStart);
		line.setStrokeWidth(11);
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
		System.out.println(xStart-xEnd + "  "+ (yStart-1050 + yEnd) +" " +getDauer(Main.getAktGeschw(),xStart-xEnd,yStart-1050 + yEnd));
		System.out.println(Math.sqrt((xStart-xEnd)*(xStart-xEnd)+(yStart-1050 + yEnd)*(yStart-1050 + yEnd)) +"  " +getDauer( Main.getAktGeschw() ,xStart-xEnd,yStart-1050 + yEnd)*Main.getAktGeschw());
		System.out.println(Main.getAktGeschw());
		t.getKeyFrames().add(new KeyFrame(Duration.seconds(getDauer( Main.getAktGeschw() ,xStart-xEnd , yStart-1050 + yEnd)), 
				new KeyValue(line.endXProperty(), xEnd),
				new KeyValue(line.endYProperty(), GUI.getHeight() - yEnd),
				new KeyValue(cir.centerXProperty(), xEnd),
				new KeyValue(cir.centerYProperty(), GUI.getHeight()- yEnd)
				));
		t.play();
	
			GUI.Kopf.setCenterX(xEnd);
			GUI.Kopf.setCenterY(GUI.getHeight() - yEnd);
			System.out.println(GUI.getHeight() - yEnd);
	}
}
