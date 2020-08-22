package cnc;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class LineAnimation extends Animation{

	/** Die line Methode erzeugt eine Animierte Line, die bei der aktuellen Position des Kopfes startet und bis zu den 
	 * übergebenen Koordinaten fährt. 
	 *  
	 * @param xEnd X Endposition nach der Animation
	 * @param yEnd Y Endposition nach der Animation
	 */
	
	public static void line( double xEnd, double yEnd) {
		Platform.runLater(()->{
		double xStart = GUI.getKopfX();
		double yStart = GUI.getKopfY();
		
		Line line = new Line();
		line.setStartX(xStart);
		line.setStartY(yStart);
		line.setEndX(xStart);
		line.setEndY(yStart);
		line.setStrokeWidth(11);
		line.setStroke(Color.BLACK);
	
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
		
		System.out.println(xStart-xEnd + "  "+ (yStart-1050 + yEnd) +" Dauer: " +getDauer(Main.getAktGeschw(),xStart-xEnd,yStart-1050 + yEnd));
		System.out.println("aktuelle Geschwindigkeit der Main " + Main.getAktGeschw());
		
		// Diese Timeline sorgt dafür, dass die Gerade und der Kopf animiert wird.
		Timeline t = new Timeline();
		t.getKeyFrames().add(new KeyFrame(Duration.seconds(getDauer( Main.getAktGeschw() ,xStart-xEnd , yStart-1050 + yEnd)), 
				new KeyValue(line.endXProperty(), xEnd),
				new KeyValue(line.endYProperty(), GUI.getHeight() - yEnd),
				new KeyValue(cir.centerXProperty(), xEnd),
				new KeyValue(cir.centerYProperty(), GUI.getHeight()- yEnd)
				
				));
		
		// Diese Timeline sorgt dafür, dass die X und Y Koordinaten in der GUI aktualisiert werden.
		Timeline t2 = new Timeline();
		int intervalle = 200;
		for(int a = 1; a <= intervalle ; a++) {
		t2.getKeyFrames().add(new KeyFrame(Duration.seconds(((getDauer( Main.getAktGeschw() ,xStart-xEnd , yStart-1050 + yEnd))/intervalle)*a),
				new KeyValue(GUI.aktuellX.textProperty(), String.valueOf(Math.round(xStart+((xEnd-xStart)/intervalle)*a))),
				new KeyValue(GUI.aktuellY.textProperty(), String.valueOf(Math.round(1050-yStart+((-1050+yEnd+yStart)/intervalle)*a)))
				));
		}
		TranslateTransition trans = new TranslateTransition();
		trans.setNode(GUI.aktuellX);
		GUI.setAndPlayTimeline(t, t2);
		t.setOnFinished(ActionEvent ->{
			CodeVerarbeitung.setBoolWeiter(true);
		});
		
		GUI.setKopfX(xEnd);
		GUI.setKopfY(GUI.getHeight() - yEnd);
		System.out.println(GUI.getHeight() - yEnd);
	
		});
	}
}
