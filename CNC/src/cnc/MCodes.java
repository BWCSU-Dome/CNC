package cnc;

/** @author Jonas Heckerodt
 * Dient zur Ausführung der gewünschten M-Codes. Ruft die zugehörigen Methoden der Klasse Main auf
 */

public class MCodes extends Codes {

	
	//Repräsentation von Code M00
	public static void haltMaschine() {
		schalteSpindelAus();
		schalteKuelmittel(false);
		CodeVerarbeitung.setBoolWeiter(true);
	}
	
	//Repräsentation von Code M02
	public static void beendeProgramm() {
		GUI.closeCNC();
		CodeVerarbeitung.setBoolWeiter(true);
	}
	
	//
	/*Repräsentation von Code M03 und M04
	 * @param richtung Die Drehrichtung der Spindel (Linksdrehung: 0, Rechtsdrehung: 1)
	 */
	public static void schalteSpindelAn(boolean richtung) {
		Main.setSpindelAktiv(true);
		Main.setSpindelRechtslaufAktiv(richtung);
		CodeVerarbeitung.setBoolWeiter(true);
	}
	
	//Repräsentation von Code M05
	public static void schalteSpindelAus() {
		Main.setSpindelAktiv(false);
		CodeVerarbeitung.setBoolWeiter(true);
	}
	
	//Repräsentation von Code M08/M09 --> Mitgegeben wird gewünschter Status (An/Aus)
	public static void schalteKuelmittel(boolean onoff) {
		Main.setKuehlungAktiv(onoff);
		CodeVerarbeitung.setBoolWeiter(true);
	}
	
}
