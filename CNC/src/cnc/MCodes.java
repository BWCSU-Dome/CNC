package cnc;

/**
 * 
 * @author Jonas Heckerodt
 *
 */

public class MCodes extends Codes {

	
	//Repräsentation von Code M00
	public static void haltMaschine() {
		//Main.setAktGeschw(0);
		schalteSpindelAus();
		schalteKuelmittel(false);
		System.out.println("Fuck you");
	}
	
	//Repräsentation von Code M02
	public static void beendeProgramm() {
		
	}
	
	//Repräsentation von Code M03 und M04 --> Mitgegeben wird die Richtung (Linksdrehung: 0, Rechtsdrehung: 1)
	public static void schalteSpindelAn(boolean richtung) {
		Main.setSpindelAktiv(true);
		Main.setSpindelRechtslaufAktiv(richtung);
		System.out.println("M03");
	}
	
	//Repräsentation von Code M05
	public static void schalteSpindelAus() {
		Main.setSpindelAktiv(false);
		System.out.println("M05");
	}
	
	//Repräsentation von Code M08/M09 --> Mitgegeben wird gewünschter Status (An/Aus)
	public static void schalteKuelmittel(boolean onoff) {
		Main.setKuehlungAktiv(onoff);
		System.out.println("M08/09");
	}
	
}
