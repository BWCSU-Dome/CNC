package cnc;


public class  Main {
	private static double posX = 0;
	private static double posY = 0;
	private static double homePosX = 0;
	private static double homePosY = 0;
	private static double geschwind_schnell = 3000/60; //3
	private static double geschwind_langsam = 2000/60 *5; //2
	private static double geschwind_fahrt = 4000/60;   //4
	private static double aktGeschwin = geschwind_langsam;
	private static double werkzeugDurchmesser = 15;
	private static boolean spindelAktiv;
	private static boolean kuehlungAktiv;
	private static boolean spindelRechtslaufAktiv;
	public static Thread codeRun = new Thread(new CodeVerarbeitung());

	public static void main(String[]args) {
		
		
		
		GUI.main(args);
	
		
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
				codeRun.sleep((long) 5000.00);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}
