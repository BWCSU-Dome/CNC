package cnc;


public class  Main {
	static double posX = 0;
	static double posY = 0;
	static double homePosX = 0;
	static double homePosY = 0;
	final static private double geschwind_schnell = 8;
	final static private double geschwin_langsam = 2;
	static private double geschwind_fahrt;
	static private double aktGeschwin;
	final static double werkzeugDurchmesser = 15;
	static boolean spindelAktiv;
	static boolean kuehlungAktiv;
	static boolean spindelRechtslaufAktiv;
	
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
	
	public static void setAktGeschw(double new_aktGeschwin) {
		aktGeschwin = new_aktGeschwin;
	}
	

	public static void setGeschwind_fahrt(double new_geschwind_fahrt) {
		geschwind_fahrt = new_geschwind_fahrt;
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


	public static void setKuehlungAktiv(boolean new_kuehlungAktiv) {
		kuehlungAktiv = new_kuehlungAktiv;
	}


	public static boolean isSpindelRechtslaufAktiv() {
		return spindelRechtslaufAktiv;
	}


	public static void setSpindelRechtslaufAktiv(boolean new_spindelRechtslaufAktiv) {
		spindelRechtslaufAktiv = new_spindelRechtslaufAktiv;
	}

}
