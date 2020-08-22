package cnc;

import java.io.File;

public class  Main {
	private static double posX = 0;
	private static double posY = 0;
	private static double homePosX = 0;
	private static double homePosY = 1050;
	private static double geschwind_schnell = 3000/60; //3000 cm pro 60 sek
	private static double geschwind_langsam = 2000/60; //2
	private static double geschwind_fahrt = 4000/60;   //4
	private static double aktGeschwin = geschwind_langsam;
	private static double werkzeugDurchmesser = 15;
	private static boolean spindelAktiv = false;
	private static boolean kuehlungAktiv;
	private static boolean spindelRechtslaufAktiv;
	public static Thread codeRun = new Thread(new CodeVerarbeitung());
	private static String colorHomePos = "green"; 
	private static String colorBohrer = "red";
	private static String colorArbeitsflaeche = "grey";
	

	public static void main(String[]args) {
		GUI.main(args);
	}
	
	public static double getRadius() {
		return werkzeugDurchmesser/2;
	}

	public static double getHomePosX() {
		return homePosX;
	}

	public static void setHomePosX(double new_homePosX) {
		homePosX = new_homePosX;
	}

	public static double getHomePosY() {
		return homePosY;
	}

	public static void setHomePosY(double new_homePosY) {
		homePosY = new_homePosY;
	}

	public static double getAktGeschw() {
		return aktGeschwin;
	}
	public static void setGeschwind_fahrt(double geschwindfahrt) {
		geschwind_fahrt = geschwindfahrt;		
	}
	
	public static void setGeschwind_langsam(double geschwindlangsam) {
		geschwind_langsam = geschwindlangsam;
		
	}
	public static void setGeschwind_schnell(double geschwindschnell) {
		geschwind_schnell = geschwindschnell;	
	}

		public static void setAktGeschwind_langsam() {
			aktGeschwin = geschwind_langsam;
			GUI.setGeschwindigkeit();
		}
	
		public static void setAktGeschwind_fahrt() {
			aktGeschwin = geschwind_fahrt;
			GUI.setGeschwindigkeit();
		}
		
		public static void setAktGeschwind_schnell() {
			aktGeschwin = geschwind_schnell;
			GUI.setGeschwindigkeit();
		}

	public static double getWerkzeugDurchmesser() {
		return werkzeugDurchmesser;
	}

	public static boolean isSpindelAktiv() {
		return spindelAktiv;
	}

	public static void setSpindelAktiv(boolean new_spindelAktiv) {
		spindelAktiv = new_spindelAktiv;
		GUI.refreshSpindel();
	}


	public static boolean isKuehlungAktiv() {
		return kuehlungAktiv;
	}
	
	/** Gibt den Wert von KuehlungAktiv als Text
	 * @return "aktiviert" oder "deaktiviert"
	 */
	public static String getKuehlungAktiv() {
		
		if(kuehlungAktiv) {
			return "aktiviert";
		}else {
			return "deaktiviert";
		}
	}
	

	/**Diese Methode verändert den Wert der Kühlung in der Main / GUI 
	 * und setzt die aktuelle Geschwindigkeit auf schnell/langsam;
	 * @param new_kuehlungAktiv 
	 */
	public static void setKuehlungAktiv(boolean new_kuehlungAktiv) {
		kuehlungAktiv = new_kuehlungAktiv;
		GUI.setKuehlung(kuehlungAktiv);
		if(new_kuehlungAktiv) {
			setAktGeschwind_schnell();
		} else {
			setAktGeschwind_langsam();
		}
	}


	public static boolean isSpindelRechtslaufAktiv() {
		return spindelRechtslaufAktiv;
	}


	public static void setSpindelRechtslaufAktiv(boolean new_spindelRechtslaufAktiv) {
		spindelRechtslaufAktiv = new_spindelRechtslaufAktiv;
		GUI.refreshDrehung();
	}

	public static double getPosX() {
		return posX;
	}

	public static void setPosX(double posX) {
		Main.posX = posX;
	}

	public static double getPosY() {
		return posY;
	}

	public static void setPosY(double posY) {
		Main.posY = posY;
	}
	
	public static void launchCodeRun() {
		codeRun.start();
	}
	
	public static void stopCodeRun() {

			try {
				codeRun.sleep((long) 500000.00);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public static void initializeThreadVerarbeitung() {
		codeRun = new Thread(new CodeVerarbeitung());
	}
	
	
	
	public static double getGeschwind_schnell() {
		return geschwind_schnell;
	}

	public static double getGeschwind_langsam() {
		return geschwind_langsam;
	}

	public static double getGeschwind_fahrt() {
		return geschwind_fahrt;
	}

	public static double getAktGeschwin() {
		return aktGeschwin;
	}

	public static Thread getCodeRun() {
		return codeRun;
	}

	public static String getColorHomePos() {
		return colorHomePos;
	}

	public static String getColorBohrer() {
		return colorBohrer;
	}

	public static String getColorArbeitsflaeche() {
		return colorArbeitsflaeche;
	}

	public static void assignSettings(double new_homePosX,double new_homePosY, double geschwindschnell, 
									  double geschwindlangsam, double geschwindfahrt, String farbeBohrer, 
									  String farbeHomePos, String farbeArbeitsflaeche) {
		
		colorArbeitsflaeche = farbeArbeitsflaeche;
		colorBohrer = farbeBohrer;
		colorHomePos = farbeHomePos;

		setHomePosX(new_homePosX);
		setHomePosY(1050-new_homePosY);
		GUI.loadHomePos();
		
		setGeschwind_schnell(geschwindschnell);
		setGeschwind_langsam(geschwindlangsam);
		setGeschwind_fahrt(geschwindfahrt);
		  setAktGeschwind_langsam();
		GUI.setColorBohrer(farbeBohrer);
		GUI.setColorHomePos(farbeHomePos);
		GUI.setColorArbeitsflaeche(farbeArbeitsflaeche);
	}

}
