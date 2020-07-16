package cnc;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.Property;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class LineAnimation extends Animation{
	
	private static boolean timelineIsFinish;
	
	public static void line( double xEnd, double yEnd) {
		double xStart = GUI.getKopfX();
		double yStart = GUI.getKopfY();
		
		
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
		
		Circle cir = new Circle(GUI.getKopfX(), GUI.getKopfY(),GUI.getKopfRadius(),GUI.getKopfFill());
		GUI.KopfsetVisible(false);
		cir.toBack();
		GUI.arbeitsF.getChildren().add(cir);
		temp = cir;
		
		Timeline t = new Timeline();	
		System.out.println(xStart-xEnd + "  "+ (yStart-1050 + yEnd) +" Dauer: " +getDauer(Main.getAktGeschw(),xStart-xEnd,yStart-1050 + yEnd));
		System.out.println("aktuelle Geschwindigkeit der Main " + Main.getAktGeschw());
		
		t.getKeyFrames().add(new KeyFrame(Duration.seconds(getDauer( Main.getAktGeschw() ,xStart-xEnd , yStart-1050 + yEnd)), 
				new KeyValue(line.endXProperty(), xEnd),
				new KeyValue(line.endYProperty(), GUI.getHeight() - yEnd),
				new KeyValue(cir.centerXProperty(), xEnd),
				new KeyValue(cir.centerYProperty(), GUI.getHeight()- yEnd)
				//new KeyValue((Property) Double.parseDouble((GUI.aktuellX.getText())), xEnd)
				));
		
		t.play();
		TranslateTransition trans = new TranslateTransition();
		trans.setNode(GUI.aktuellX);
		
		
		
//		updateXLabel(xStart,xEnd,getDauer( Main.getAktGeschw() ,xStart-xEnd , yStart-1050 + yEnd));
		GUI.setKopfX(xEnd);
		GUI.setKopfY(GUI.getHeight() - yEnd);
		System.out.println(GUI.getHeight() - yEnd);
	}
	public static double a = 0;
	public static double b = 0;
	private static void updateXLabel(double xStart, double xEnd, double time ) {
		b = (xEnd - xStart)/100;
        PauseTransition pause = new PauseTransition(Duration.seconds(time/100));
        pause.setOnFinished(event ->{
        	
        	GUI.setXLabel(GUI.Kopf.getCenterX());
        	a+=b;
            pause.play();
        	 
        });
        pause.play();
    }
}
