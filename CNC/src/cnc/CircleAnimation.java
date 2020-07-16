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
    private static double xtemp, ytemp, xtempCenter, ytempCenter;
	
    /**
     * 
     * @param x X-Wert bis zu der Stelle, wo gefräst werden soll.
     * @param y	Y-Wert bis zu der Stelle, wo gefräst werden soll.
     * @param i	X-Wert bis zum Mittelpunkt
     * @param j Y-Wert bis zum Mittelpunkt
     */
	public static void kreis(double xEnde, double yEnde, double i, double j) {
		yEnde = 1050 - yEnde;
		
		
		double radius = Math.sqrt(i*i+j*j);
		
		xtempCenter = GUI.getKopfX()-i;
		ytempCenter = GUI.getKopfY()-j;
		
		System.out.println("XtempCenter: " + xtempCenter);
		System.out.println("YtempCenter: " + ytempCenter);
		System.out.println("1050 - Ytemp: " + (1050-ytempCenter));
		
		Circle test = new Circle();
		test.setCenterX(xtempCenter);
		test.setCenterY(ytempCenter);
		test.setRadius(10);
		GUI.arbeitsF.getChildren().add(test);

		
		//Startwinkel ausrechnen mit Vektorformel
		double startWinkel = ((i)/Math.sqrt(i*i+j*j));
		startWinkel = Math.toDegrees(Math.acos(startWinkel));
		System.out.println("Startwinkel: " + startWinkel);
		
		
		 double vektorEndpunktY = yEnde-(1050+ytempCenter);
		 double vektorEndpunktX = xEnde-xtempCenter;
		 
		 System.out.println("Vektor Ende X: " + vektorEndpunktX);
		 System.out.println("Vektor Ende Y: " + vektorEndpunktY);
		
		 double endWinkel = (vektorEndpunktX/(Math.sqrt(vektorEndpunktX*vektorEndpunktX)+Math.sqrt(vektorEndpunktY*vektorEndpunktY)));
		 endWinkel = Math.toDegrees(Math.acos(endWinkel))+startWinkel;
		 System.out.println("Endwinkel: "+endWinkel); 		
		
		for(double a = startWinkel ; a <= endWinkel; a += 0.5) {
			addCir(a,radius);
			
			System.out.println(a+ " hinzugefügt");
			
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
	
	/**
	 * 
	 * @param winkelDifferenz
	 */
	private static void update(double winkelDifferenz) {
		intervalle = winkelDifferenz*2-1; 
        PauseTransition pause = new PauseTransition(Duration.seconds(0.02));
        pause.setOnFinished(event ->{
        	if(stueck < intervalle) {
        		stueck++;
        	
        		updateCirs();
                pause.play();
        	}  
        });
        pause.play();
    }
	
//	/** Diese Überprüfung ob die Koordinate auf dem Kreis liegt
//	 * 
//	 * @param x Zielkoordinate der Kreisbewegung
//	 * @param y Zielkoordinate der Kreisbewegung
//	 * @param CenterX Mittelpunkt des Kreises
//	 * @param CenterY Mittelpunkt des Kreises
//	 * @return
//	 */
//	public static boolean checkKreisBogen(double x, double y, double CenterX, double CenterY, double radius) {
//		System.out.println(CenterX);
//		boolean wert;
//		for(double a = 90; a <= 270; a+= 0.5) {
//			xtemp = (CenterX + radius*Math.cos(Math.toRadians(a)));
//			ytemp = (CenterY + radius*Math.sin(Math.toRadians(a)));
//			xtemp = Math.round(xtemp);
//			ytemp = Math.round(ytemp);
//			
//			if(xtemp == x && ytemp == y) {
//				
//				wert = true;
//				return wert;
//			}
//		}
//		return false;
//		
//		
//	}
}
