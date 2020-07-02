package cnc;

public class MCodes extends Codes {

	
	//Repr�sentation von Code M00
	public static void haltMaschine() {
		//Main.setAktGeschw(0);
	}
	
	//Repr�sentation von Code M02
	public static void beendeProgramm() {
		
	}
	
	//Repr�sentation von Code M03 und M04 --> Mitgegeben wird die Richtung (Linksdrehung: 0, Rechtsdrehung: 1)
	public static void schalteSpindelAn(boolean richtung) {
		Main.setSpindelAktiv(true);
		Main.setSpindelRechtslaufAktiv(richtung);
	}
	
	//Repr�sentation von Code M05
	public static void schalteSpindelAus() {
		Main.setSpindelAktiv(false);
	}
	
	//Repr�sentation von Code M08/M09 --> Mitgegeben wird gew�nschter Status (An/Aus)
	public static void schalteKuelmittel(boolean onoff) {
		Main.setKuehlungAktiv(onoff);
	}
	
}
