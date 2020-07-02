package cnc;


public class Main {
	double posX = 0;
	double posY = 0;
	double homePosX = 0;
	double homePosY = 0;
	double geschwind_schnell = 8;
	double geschwin_langsam = 2;
	double geschind_fahrt;
	double werkzeugDurchmesser;
	boolean spindelAktiv;
	boolean kuehlungAktiv;
	boolean spindelRechtslaufAktiv;
	
	public static void main(String[]args) {
		Main fraese = new Main();
		GUI.launch(args);
	}


	public double getHomePosX() {
		return homePosX;
	}


	public void setHomePosX(double homePosX) {
		this.homePosX = homePosX;
	}


	public double getHomePosY() {
		return homePosY;
	}


	public void setHomePosY(double homePosY) {
		this.homePosY = homePosY;
	}


	public double getGeschwind_schnell() {
		return geschwind_schnell;
	}


	public void setGeschwind_schnell(double geschwind_schnell) {
		this.geschwind_schnell = geschwind_schnell;
	}


	public double getGeschwin_langsam() {
		return geschwin_langsam;
	}


	public void setGeschwin_langsam(double geschwin_langsam) {
		this.geschwin_langsam = geschwin_langsam;
	}


	public double getGeschind_fahrt() {
		return geschind_fahrt;
	}


	public void setGeschind_fahrt(double geschind_fahrt) {
		this.geschind_fahrt = geschind_fahrt;
	}


	public double getWerkzeugDurchmesser() {
		return werkzeugDurchmesser;
	}


	public void setWerkzeugDurchmesser(double werkzeugDurchmesser) {
		this.werkzeugDurchmesser = werkzeugDurchmesser;
	}


	public boolean isSpindelAktiv() {
		return spindelAktiv;
	}


	public void setSpindelAktiv(boolean spindelAktiv) {
		this.spindelAktiv = spindelAktiv;
	}


	public boolean isKuehlungAktiv() {
		return kuehlungAktiv;
	}


	public void setKuehlungAktiv(boolean kuehlungAktiv) {
		this.kuehlungAktiv = kuehlungAktiv;
	}


	public boolean isSpindelRechtslaufAktiv() {
		return spindelRechtslaufAktiv;
	}


	public void setSpindelRechtslaufAktiv(boolean spindelRechtslaufAktiv) {
		this.spindelRechtslaufAktiv = spindelRechtslaufAktiv;
	}

}
