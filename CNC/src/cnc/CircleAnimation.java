package cnc;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleAnimation extends Animation{
	
    private static ArrayList<Circle> circles = new ArrayList<>();
    private static Pane cirPane;
    private static double xtemp, ytemp, xtempCenter, ytempCenter;
	
    /**
     * 
     * @param x X-Wert bis zu der Stelle, wo gefräst werden soll.
     * @param y	Y-Wert bis zu der Stelle, wo gefräst werden soll.
     * @param i	X-Wert des Mittelpunktes
     * @param j Y-Wert des Mittelpunktes
     */
	public static void kreis(double x, double y, double i, double j) {
		xtempCenter= GUI.Kopf.getCenterX();
		ytempCenter = GUI.Kopf.getCenterY();
		for(int a = 90 ; a < 270; a++) {
			addCir(a, i,j );
		}
		update();
	}
	
    private static void addCir(int winkel, double i, double j) {
    	  
    	xtemp = (xtempCenter + i + 200*Math.cos(Math.toRadians(winkel)));
		ytemp = (ytempCenter + j + 200*Math.sin(Math.toRadians(winkel)));
    	Circle cir = new Circle(xtemp,ytemp,7.5);     
        cir.setFill(Color.BLACK);
        cir.setVisible(false);
        System.out.println(cir == null);
        
        circles.add(cir);
        GUI.arbeitsF.getChildren().add(cir);
    }
	
	private static int stueck = 0 ;
	private static int intervalle = 180;
	
	private static void updateCirs() {
		
		if((stueck-1)> 0 && (intervalle - stueck)>1) {
			Circle temp2 = circles.get(stueck-1);
			temp2.setFill(Color.BLUE);
			temp2.setStroke(Color.BLUE);
			circles.set(stueck-1, temp2);
			temp2.setVisible(true);
			System.out.println(temp2 + "Blau");
		}
		Circle temp = circles.get(stueck);
		temp.setVisible(true);

		circles.set(stueck, temp);
		System.out.println(temp);
		stueck++;
	}

	private static void update() {

        PauseTransition pause = new PauseTransition(Duration.seconds(0.05));
        pause.setOnFinished(event ->{
        	if(stueck < intervalle) {
        		stueck++;
        		updateCirs();
                pause.play();
        	}  
        });
        pause.play();
    }
}
