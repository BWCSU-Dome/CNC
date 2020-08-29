package cnc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleAnimation extends Animation {

	private static ArrayList<Circle> circles = new ArrayList<>();
	private static Circle tempColor;
	private static double xtemp, ytemp, xtempCenter, ytempCenter;
	private static PauseTransition pauseTrans;
	private static double xEnd, yEnd;

	/**
	 * 
	 * @param x X-Wert bis zu der Stelle, wo gefräst werden soll.
	 * @param y Y-Wert bis zu der Stelle, wo gefräst werden soll.
	 * @param i X-Wert bis zum Mittelpunkt
	 * @param j Y-Wert bis zum Mittelpunkt
	 */
	public static void kreis(double xEnde, double yEnde, double i, double j) {
		Platform.runLater(() -> {
			Main.setSpindelAktiv(true);
			GUI.refreshSpindel();
			GUI.setPaustransAktiv(true);
			xEnd = xEnde;
			yEnd = yEnde;
			double radius = Math.sqrt(i * i + j * j);

			xtempCenter = GUI.getKopfX() - (-i);
			ytempCenter = GUI.getKopfY() - (j);

			System.out.println("XtempCenter: " + xtempCenter);
			System.out.println("YtempCenter: " + ytempCenter);
			System.out.println("1050 - Ytemp: " + (1050 - ytempCenter));

			Circle test = new Circle();
			test.setCenterX(xtempCenter);
			test.setCenterY(ytempCenter);
			test.setRadius(10);
			GUI.arbeitsF.getChildren().add(test);

			double startWinkel = berechneWinkelKreis(radius, j, -i);
			System.out.println("Start" + startWinkel);

			double endWinkel;
			if(GUI.getKopfY() == yEnde) {
			endWinkel = berechneWinkelKreis(radius, 0, xEnde - xtempCenter);
			}else {
			endWinkel = berechneWinkelKreis(radius,  -1050+yEnde + ytempCenter, xEnde - xtempCenter);
			}
			System.out.println("Endwinkel" + endWinkel);
			double differenz;
			
			
			if (endWinkel > startWinkel) {
				differenz = 360+startWinkel-endWinkel;
				System.out.println(differenz+ "diff Ende gröser");
				for (double a = 0; a <= Math.abs(differenz) ; a += 0.5) {
				
				addCir(startWinkel, radius);
				startWinkel += 0.5;
				
				}
			} else {
				differenz = startWinkel-endWinkel;
				System.out.println(differenz+ "diff");
				for (double a = 0; a <= differenz ; a += 0.5) {
					
					addCir(startWinkel, radius);
					startWinkel += 0.5;
				
				}
			}
			if(!(GUI.getTemp() == null)) {
				GUI.getTemp().setVisible(false);
			}
			update(radius);
			
			System.out.println("update ausgeführt");
			GUI.setPaustransAktiv(false);
			System.out.println(circles.size());

		});
		
	}
	/** Diese Methode erzeugt viele Punkte die in der aktuellen Geschwindigkeit nacheinander sichtbar werden. 
	 * 
	 * @param x X-Wert bis zu der Stelle, wo gefräst werden soll.
	 * @param y Y-Wert bis zu der Stelle, wo gefräst werden soll.
	 * @param i X-Wert bis zum Mittelpunkt
	 * @param j Y-Wert bis zum Mittelpunkt
	 */
	public static void kreisGegenUhrzeiger(double xEnde, double yEnde, double i, double j)  {
		Platform.runLater(() -> {
			Main.setSpindelAktiv(true);
			GUI.refreshSpindel();
			GUI.setPaustransAktiv(true);
			xEnd = xEnde;
			yEnd = yEnde;
			double radius = Math.sqrt(i * i + j * j);

			xtempCenter = GUI.getKopfX() - (-i);
			ytempCenter = GUI.getKopfY() - (j);

			System.out.println("XtempCenter: " + xtempCenter);
			System.out.println("YtempCenter: " + ytempCenter);
			System.out.println("1050 - Ytemp: " + (1050 - ytempCenter));

			Circle test = new Circle();
			test.setCenterX(xtempCenter);
			test.setCenterY(ytempCenter);
			test.setRadius(10);
			GUI.arbeitsF.getChildren().add(test);

			double startWinkel = berechneWinkelKreis(radius, j, -i);
			System.out.println("Start" + startWinkel);

			double endWinkel;
			if(GUI.getKopfY() == yEnde) {
			endWinkel = berechneWinkelKreis(radius, 0, xEnde - xtempCenter);
			}else {
			endWinkel = berechneWinkelKreis(radius,  -1050+yEnde + ytempCenter, xEnde - xtempCenter);
			}
			System.out.println("Endwinkel" + endWinkel);
			double differenz;
			
			
			if (endWinkel > startWinkel) {
				differenz = startWinkel-endWinkel;
				System.out.println(differenz+ "diff end grosser");
					for (double a = 0; a <= Math.abs(differenz) ; a += 0.5) {
					
					addCir(startWinkel, radius);
					startWinkel -= 0.5;
				
				}
			} else {
				differenz = 360-startWinkel+endWinkel;
				System.out.println(differenz+ "diff");
				for (double a = 0; a <= differenz ; a += 0.5) {
					addCir(startWinkel, radius);
					startWinkel -= 0.5;
				
				}
			}
			if(!(GUI.getTemp() == null)) {
				GUI.getTemp().setVisible(false);
			}
			update(radius);
			
			System.out.println("update ausgeführt");
			GUI.setPaustransAktiv(false);
			System.out.println(circles.size());

		});
		
	}
	

	public static double berechneWinkelKreis(double radius, double yEntfernung, double xEntfernung)  {
		System.out.println("yEntfernung"+yEntfernung + "xEntfernung" + xEntfernung);
		if (xEntfernung < 0 && 0 < yEntfernung) { // 2
			return 180 - Math.toDegrees(Math.asin(yEntfernung / radius));
		} else if (0 < xEntfernung && yEntfernung < 0) {// 1
			return 360 + Math.toDegrees(Math.asin(yEntfernung / radius));
		} else if (0 < xEntfernung && 0 < yEntfernung) {// 3
			return Math.toDegrees(Math.asin(yEntfernung / radius));
		} else if (xEntfernung < 0 && yEntfernung < 0) {// 4
			return Math.toDegrees(Math.asin(Math.abs(yEntfernung) / radius)) + 180;
		} else if (xEntfernung == 0 && yEntfernung < 0) {
			return 270;
		} else if (xEntfernung == 0 && 0 < yEntfernung) {
			return 90;
		} else if (xEntfernung < 0 && yEntfernung == 0) {
			return 180;
		} else if (0 < xEntfernung && yEntfernung == 0) {
			return 0;
		} else {
			return 10000;
		}
	}

	private static void addCir(double winkel, double radius) {

		xtemp = (xtempCenter + radius * Math.cos(Math.toRadians(winkel)));
		ytemp = (ytempCenter + radius * Math.sin(Math.toRadians(winkel)));
		Circle cir = new Circle(xtemp, ytemp, 7.5);
		cir.setFill(GUI.getKopfFill());
		cir.setVisible(false);
		circles.add(cir);
		GUI.arbeitsF.getChildren().add(cir);
	}

	private static int stueck = 0;

	private static void updateCirs() {

		if (!(tempColor == null)) {
			tempColor.setFill(Color.BLACK);
		}
			
		Circle temp = circles.get(stueck);
		temp.setVisible(true);
		GUI.setKopfX(temp.getCenterX());
		GUI.setKopfY(1050-temp.getCenterY());
		GUI.refreshKoordinaten();
		circles.set(stueck, temp);
		tempColor = temp;
	}

	/**
	 * 
	 * @param winkelDifferenz
	 */
	private static void update(double radius) {

		double umfang = Math.PI*radius*circles.size()/360;
		System.out.println("Umfang "+ umfang);
		double dauer = umfang/Main.getAktGeschw();
		System.out.println("Dauer"+dauer);
		pauseTrans = new PauseTransition(Duration.seconds(dauer/circles.size()));
		
		pauseTrans.setOnFinished(event -> {
			if(stueck < circles.size()-1) {
				stueck++;
				updateCirs();
				pauseTrans.play();
			}else {
				circles.clear();
				stueck = 0;
				GUI.setKopfX(xEnd);
				GUI.setKopfY(1050-yEnd);
				GUI.setTemp(circles.get(stueck-1));
				CodeVerarbeitung.setBoolWeiter(true);
				return;
			}
		});
		pauseTrans.play();
	}

	public static void pausePauseTrans() {
		pauseTrans.pause();
	}

	public static void playPauseTrans() {
		pauseTrans.play();
	}
	public static Boolean PauseTransIsNotNull() {
		if(pauseTrans !=null) {
			return true;
		}return false;
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
