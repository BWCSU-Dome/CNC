package cnc;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleAnimation extends Animation{
	
    private static ArrayList<Circle> circles = new ArrayList<>();
    private static Circle tempColor;
    private static double xtemp, ytemp,xEnd, yEnd, xtempCenter, ytempCenter;
	
    /**
     * 
     * @param x X-Wert bis zu der Stelle, wo gefräst werden soll.
     * @param y	Y-Wert bis zu der Stelle, wo gefräst werden soll.
     * @param i	X-Wert des Mittelpunktes
     * @param j Y-Wert des Mittelpunktes
     */
	public static void kreis(double xEnde, double yEnde, double i, double j) {
		yEnde = 1050 - yEnde;
		
		
		double radius = Math.sqrt(i*i+j*j);
		
		xtempCenter = GUI.Kopf.getCenterX()-i;
		ytempCenter = GUI.Kopf.getCenterY()-j;
		System.out.println(ytempCenter);
		
		//Startwinkel ausrechnen mit Vektorformel
		 double startWinkel = ((i)/Math.sqrt(i*i+j*j));
		 startWinkel = Math.toDegrees(Math.acos(startWinkel));
		System.out.println(startWinkel);
		 double vektorEndpunktY = xEnde-xtempCenter;
		 double vektorEndpunktX = yEnde-ytempCenter;
		
		 double endWinkel = (vektorEndpunktX/Math.sqrt(vektorEndpunktX*vektorEndpunktX+vektorEndpunktY+vektorEndpunktY));
		 endWinkel = Math.toDegrees(Math.acos(endWinkel));
		 System.out.println(endWinkel); 
//		if(!(checkKreisBogen(xEnde, yEnde, xtempCenter, ytempCenter,radius))) {
//			//Falscher Kreiseingabe
//			System.out.println("Falsche kreiseingabe Exception");
//			return;
//		}
		
		yEnd = yEnde;
		xEnd = xEnde;
		
		for(double a = startWinkel ; a <= endWinkel; a += 0.5) {
			addCir(a,radius);
			System.out.println(a+ " hinzugefügt");
			if(xtemp == xEnd && ytemp == yEnd) {
				update(endWinkel-startWinkel);
				return;
			}
		}
		update(endWinkel-startWinkel);
		System.out.println("update ausgeführt");
	}
	
    private static void addCir(double winkel,double radius) {
    	  
    	xtemp = (xtempCenter + radius*Math.cos(Math.toRadians(winkel)));
		ytemp = (ytempCenter + radius*Math.sin(Math.toRadians(winkel)));
    	Circle cir = new Circle(xtemp,ytemp,7.5);     
        cir.setFill(Color.RED);
        cir.setVisible(false);

        circles.add(cir);
        GUI.arbeitsF.getChildren().add(cir);
    }
	
	private static int stueck = 0 ;
	private static double intervalle = 360;
	
	private static void updateCirs() {
		
		if(!(tempColor == null)) {
			tempColor.setFill(Color.PINK);
		}

		Circle temp = circles.get(stueck);
		temp.setVisible(true);
		circles.set(stueck, temp);
		tempColor = temp;
		stueck++;
	}
	private static double winkelDiff ; 
	
	/**
	 * 
	 * @param winkelDifferenz
	 */
	private static void update(double winkelDifferenz) {
		intervalle = winkelDifferenz*2; 
        PauseTransition pause = new PauseTransition(Duration.seconds(0.02));
        pause.setOnFinished(event ->{
        	if(stueck < intervalle) {
        		stueck++;
        		winkelDiff--;
        		updateCirs();
                pause.play();
        	}  
        });
        pause.play();
    }
	
	/** Diese Überprüfung ob die Koordinate auf dem Kreis liegt
	 * 
	 * @param x Zielkoordinate der Kreisbewegung
	 * @param y Zielkoordinate der Kreisbewegung
	 * @param CenterX Mittelpunkt des Kreises
	 * @param CenterY Mittelpunkt des Kreises
	 * @return
	 */
	public static boolean checkKreisBogen(double x, double y, double CenterX, double CenterY, double radius) {
		System.out.println(CenterX);
		boolean wert;
		for(double a = 90; a <= 270; a+= 0.5) {
			xtemp = (CenterX + radius*Math.cos(Math.toRadians(a)));
			ytemp = (CenterY + radius*Math.sin(Math.toRadians(a)));
			xtemp = Math.round(xtemp);
			ytemp = Math.round(ytemp);
			
			if(xtemp == x && ytemp == y) {
				
				wert = true;
				return wert;
			}
		}
		return false;
		
		
	}
}
