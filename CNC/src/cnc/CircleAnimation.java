package cnc;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/*
 * @author Dominik Riepl
 */

public class CircleAnimation extends Animation {

	private static ArrayList<Circle> circles = new ArrayList<>();
	private static Circle tempColor;
	private static double xtemp, ytemp, xtempCenter, ytempCenter;
	private static PauseTransition pauseTrans;
	private static double xEnd, yEnd;

	/*
	 * Diese Methode erzeugt viele Punkte die in der aktuellen Geschwindigkeit
	 * nacheinander sichtbar werden. Dient zur Darstellung für G-Code G02
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

			double startWinkel = berechneWinkelKreis(radius, j, -i);

			double endWinkel;
			if (GUI.getKopfY() == yEnde) {
				endWinkel = berechneWinkelKreis(radius, 0, xEnde - xtempCenter);
			} else {
				endWinkel = berechneWinkelKreis(radius, -1050 + yEnde + ytempCenter, xEnde - xtempCenter);
			}

			double differenz;

			if (endWinkel > startWinkel) {
				differenz = 360 + startWinkel - endWinkel;

				for (double a = 0; a <= Math.abs(differenz); a += 0.5) {

					addCir(startWinkel, radius);
					startWinkel += 0.5;

				}
				
			} else if(startWinkel == endWinkel){
				for (double a = 0; a <= 360; a += 0.5) {

					addCir(startWinkel, radius);
					startWinkel += 0.5;

				}
			}	
			else {
				differenz = startWinkel - endWinkel;

				for (double a = 0; a <= differenz; a += 0.5) {

					addCir(startWinkel, radius);
					startWinkel += 0.5;

				}
			}
			if (!(GUI.getTemp() == null)) {
				GUI.getTemp().setVisible(false);
			}
			update(radius);
			
		});

	}

	/**
	 * Diese Methode erzeugt viele Punkte die in der aktuellen Geschwindigkeit
	 * nacheinander sichtbar werden. Dient zur Darstellung für G-Code G03
	 * 
	 * @param x X-Wert bis zu der Stelle, wo gefräst werden soll.
	 * @param y Y-Wert bis zu der Stelle, wo gefräst werden soll.
	 * @param i X-Wert bis zum Mittelpunkt
	 * @param j Y-Wert bis zum Mittelpunkt
	 */
	public static void kreisGegenUhrzeiger(double xEnde, double yEnde, double i, double j) {
		Platform.runLater(() -> {
			Main.setSpindelAktiv(true);
			GUI.refreshSpindel();
			GUI.setPaustransAktiv(true);
			xEnd = xEnde;
			yEnd = yEnde;
			double radius = Math.sqrt(i * i + j * j);

			xtempCenter = GUI.getKopfX() - (-i);
			ytempCenter = GUI.getKopfY() - (j);

			double startWinkel = berechneWinkelKreis(radius, j, -i);
			System.out.println(startWinkel + "start windl");
			double endWinkel;
			if (GUI.getKopfY() == yEnde) {
				endWinkel = berechneWinkelKreis(radius, 0, xEnde - xtempCenter);
			} else {
				endWinkel = berechneWinkelKreis(radius, -1050 + yEnde + ytempCenter, xEnde - xtempCenter);
			}
			System.out.println(endWinkel + " ene");
			double differenz;
			

			if (endWinkel > startWinkel) {
				differenz = startWinkel - endWinkel;
				for (double a = 0; a <= Math.abs(differenz); a += 0.5) {

					addCir(startWinkel, radius);
					startWinkel -= 0.5;

				}
			} else if(startWinkel == endWinkel){
				System.out.println("start = ende");
				for (double a = 0; a <= 360; a += 0.5) {
					
					addCir(startWinkel, radius);
					startWinkel -= 0.5;

				}
			}
			else {
				differenz = 360 - startWinkel + endWinkel;
				for (double a = 0; a <= differenz; a += 0.5) {
					addCir(startWinkel, radius);
					startWinkel -= 0.5;

				}
			}
			if (!(GUI.getTemp() == null)) {
				GUI.getTemp().setVisible(false);
			}
			update(radius);
			

		});

	}

	/*
	 * Diese Methode berechnet den Winkel eines Vektors auf dem Kreis und wird
	 * genutzt, um den Start/Endwinkel zu berechnen
	 * 
	 */
	public static double berechneWinkelKreis(double radius, double yEntfernung, double xEntfernung) {

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

	/*
	 * Diese Methode fügt der Arbeitsfläche einen Kreis hinzu in abhänigkeit des
	 * aktuellen Winkels zum Mittelpunkt der Kreisbewegung
	 * 
	 */
	private static void addCir(double winkel, double radius) {

		xtemp = (xtempCenter + radius * Math.cos(Math.toRadians(winkel)));
		ytemp = (ytempCenter + radius * Math.sin(Math.toRadians(winkel)));
		Circle cir = new Circle(xtemp, ytemp, 7);
		cir.setFill(GUI.getKopfFill());
		cir.setVisible(false);
		circles.add(cir);
		GUI.arbeitsF.getChildren().add(cir);
	}

	private static int stueck = 0;

	/*
	 * Diese Methode setzt abhängig von stueck die bei jedem aufruf den vorherigen
	 * Kreis auf die Color "black" und fügt macht den aktuellen Kreis sichtbar
	 */
	private static void updateCirs() {

		if (!(tempColor == null)) {
			tempColor.setFill(Color.BLACK);
		}

		Circle temp = circles.get(stueck);
		temp.setVisible(true);
		GUI.setKopfX(temp.getCenterX());
		GUI.setKopfY(1050 - temp.getCenterY());
		GUI.refreshKoordinaten();
		circles.set(stueck, temp);
		tempColor = temp;
	}

	/**
	 * Diese Methode startet die Animation von der Kreisbewegung die Kreise mit
	 * zeitlich angepassten Pausen sichtbar werden
	 * 
	 * @param Radius
	 */
	private static void update(double radius) {

		double umfang = Math.PI * radius * circles.size() / 360;

		double dauer = umfang / Main.getAktGeschw();

		pauseTrans = new PauseTransition(Duration.seconds(dauer / circles.size()));

		pauseTrans.setOnFinished(event -> {
			if (stueck < circles.size() - 1) {
 				stueck++;
				updateCirs();
				pauseTrans.play();
			} else {
				GUI.setTemp(circles.get(stueck));
				circles.clear();
				stueck = 0;
				GUI.setKopfX(xEnd);
				GUI.setKopfY(1050 - yEnd);
				GUI.setPaustransAktiv(false);
				pauseTrans = null;
				CodeVerarbeitung.setBoolWeiter(true);
				CodeVerarbeitung.setEndetrue();
				return;
			}
		});
		pauseTrans.play();
	}

	/*
	 * Diese Methode dient dazu die Kreisbewegung pausieren zu können
	 * 
	 */
	public static void pausePauseTrans() {
		pauseTrans.pause();
	}

	/*
	 * Diese Methode dient dazu die Kreisbewegung pausieren zu können
	 * 
	 */
	public static void playPauseTrans() {
		pauseTrans.play();
	}

	/*
	 * Diese Methode wird von der GUI genutzt um zu Überprüfen, ob die PauseTrans
	 * aktualisiert ist. bzw. ob sie gestartet oder beendet werden kann.
	 * 
	 */
	public static Boolean PauseTransIsNotNull() {
		if (pauseTrans != null) {
			return true;
		}
		return false;
	}

	/** Diese Überprüfung ob die Koordinate auf dem Kreis liegt im UhrzeigerSinn
	 *  Die Winkel werden dabei mit Modulo umgerechnet 
	 * @param x Zielkoordinate der Kreisbewegung
	 * @param y Zielkoordinate der Kreisbewegung
	 * @throws IOException 
	 * @throws OutOfAreaException 
	 */
	public static void checkKreisBogen(double xEnde, double yEnde, double aktuelX, double aktuelY, double i, double j) throws IOException, OutOfAreaException {
		
		double radius = Math.sqrt(i * i + j * j);

		double xtempCen = aktuelX - (-i);
		double ytempCen = aktuelY - (j);

		double startWinkel = berechneWinkelKreis(radius, j, -i);
		System.out.println(startWinkel+ "start");
		
		System.out.println(xtempCen + "xtem");
		System.out.println(ytempCen + "ytem");
		double endWinkel;
		if (aktuelY == yEnde) {
			endWinkel = berechneWinkelKreis(radius, 0, xEnde - xtempCen);
		} else {
			endWinkel = berechneWinkelKreis(radius, -1050 + yEnde + ytempCen, xEnde - xtempCen);
		}
		System.out.println(endWinkel);
		
		if(startWinkel != 0 && startWinkel != 90 && startWinkel != 180 && startWinkel != 270) {
			throw new IOException();
		}
		if(endWinkel != 0 && endWinkel != 90 && endWinkel != 180 && endWinkel != 270) {
			throw new IOException();
		}
		double differenz;
		
		if (endWinkel > startWinkel) {
			differenz = startWinkel - endWinkel;
		}else {
			differenz = 360 - startWinkel + endWinkel;
		}
		for(int a = 0 ; a <= differenz ; a+=90) {
			int b = (int)(startWinkel/90)%4;
			switch (b) {
				case 0:
					if(1400 < xtempCen+radius) {
						throw new OutOfAreaException();
					}
					break;
				case 1:
					if(1400 < xtempCen+radius) {
						throw new OutOfAreaException();
					}
					break;
				case 2:
					if(xtempCen-radius < 0) {
						throw new OutOfAreaException();
					}
					break;
				case 3:
					if(ytempCen-radius < 0) {
						throw new OutOfAreaException();
					}
					break;
			}
			startWinkel +=90;
		}

	}
}
