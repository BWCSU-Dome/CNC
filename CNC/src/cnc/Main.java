package cnc;


public class  Main {
	double posX = 0;
	double posY = 0;
	double homePosX = 0;
	double homePosY = 0;
	private double geschwind_schnell = 8;
	private double geschwin_langsam = 2;
	private double geschind_fahrt;
	private double aktGeschwin;
	double werkzeugDurchmesser;
	boolean spindelAktiv;
	boolean kuehlungAktiv;
	boolean spindelRechtslaufAktiv;
	
	public static void main(String[]args) {
		Main fraese = new Main();
		GUI.main(args);
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


	


	public void setGeschwind_schnell(double geschwind_schnell) {
		this.geschwind_schnell = geschwind_schnell;
	}


	


	public void setGeschwin_langsam(double geschwin_langsam) {
		this.geschwin_langsam = geschwin_langsam;
	}

	public double getAktGeschw() {
		return aktGeschwin;
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
