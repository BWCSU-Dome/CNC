package cnc;

import java.util.ArrayList;

public class Codes {
	
	public static ArrayList<String> queue = new ArrayList<String>();
	private static String[] befehlEingang;
	private static String[] befehl = new String[5];
	private static double[] parameter = new double[4];
	
	
	
	public static void enqueueBefehl(String befehlEingang) {
		queue.add(befehlEingang);
		
	}
	
	
	
	public static void doBefehl() {
		
		if(queueIsEmpty()) {
			System.out.println("Die Warteschlange ist leer.");
			return;
		}
		
		befehlEingang = queue.get(0).split(" ");
		
		System.arraycopy(befehlEingang, 0, befehl, 0, befehlEingang.length);
		
		if(queue.get(0).split(" ").length != 1) {
		
		
		queue.remove(0);
		
		for(int i = 0; i < befehl.length; i++) {
			if(befehl[i] == null) {
				befehl[i] = "00";				//Wenn "Schublade" leer, auffüllen mit 00
			}
		}
		
		// Es wird vorsorglich ein Int-Array erzeugt, das zur reibungslosen Weiterverarbeitung diesen Datentyp trägt.
			
		parameter = new double[befehl.length-1];
			String zwischenspeicher = "";
		
			for(int i = 1; i < befehl.length; i++) {
				
		//Sortierung der Funktionsargumente in folgende Reihenfolge: X, Y, I, J (falls vorhanden)
		switch (befehl[i].charAt(0)) {
		case 'X':
			zwischenspeicher = befehl[1];
			befehl[1] = befehl[i];
			befehl[i] = zwischenspeicher;
			break;
		case 'Y':
			zwischenspeicher = befehl[2];
			befehl[2] = befehl[i];
			befehl[i] = zwischenspeicher;
			break;
		case 'I':
			zwischenspeicher = befehl[3];
			befehl[3] = befehl[i];
			befehl[i] = zwischenspeicher;
			break;
		case 'J':
			zwischenspeicher = befehl[4];
			befehl[4] = befehl[i];				
			befehl[i] = zwischenspeicher;
			break;
		}
			}
			//Abspeicherung in int-Array, sodass die Werte besser in G-Methoden verwendet werden können.
		
		
		for(int i=0; i < parameter.length; i++) {
			parameter[i] = Integer.parseInt(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
			}
	
			} 
	
		
	
	for(int a = 0; a < befehl.length; a++) {
		System.out.println(a+":" + befehl[a]);
	}
			//Unterscheidung, ob M- oder G-Code vorliegt (entschieden an mitgegebenem ersten Buchstaben)
			switch(befehl[0].charAt(0)) {
			case 'M':
				doMCodes(befehl);
				break;
			case 'G':
				doGCodes(befehl);
				break;
			default:
				System.out.println("Das lief nicht gut. Fehler im Switch-Case-Block in der Klasse RegEx");
			}
		
		
	
	}
	
	

	
	
	private static void doGCodes(String[] code) {
		
		//Aufruf des gewünschten Codes nach eingegebener, gewünschter Funktion
		switch(code[0]) {
		
		case "G00":
		GCodes.fahrenEilgang(parameter);
			break;
			
		case "G01":
		GCodes.fahrenGerade(parameter);
			break;
			
		case "G02":
		GCodes.fahrenKreisImUhrzeigersinn(parameter);
			break;
		
		case "G03":
		GCodes.fahrenKreisGegenUhrzeigersinn(parameter);
			break;
			
		case "G28":
		GCodes.fahrenZuHome();
			break;
			
		default:
		System.out.println("GCode-Fehler"); //Fehler sollte hier gar nicht mehr auftreten können. Bitte Meldung, falls das der Fall sein sollte.
		
	
	}
		return;

}
		
	
	private static void doMCodes(String[]code) {
		
		//Aufruf des gewünschten Codes nach eingegebener, gewünschter Funktion
		switch(code[0]) {
		
		case "M00":
		MCodes.haltMaschine();
			break;
		
		case "M02":
		MCodes.beendeProgramm();
			break;
		
		case "M03":
		MCodes.schalteSpindelAn(true);
			break;
		
		case "M04":
		MCodes.schalteSpindelAn(false);
			break;
		
		case "M05":
		MCodes.schalteSpindelAus();
			break;
	
		case "M08":
		MCodes.schalteKuelmittel(true);
			break;
		
		case "M09":
		MCodes.schalteKuelmittel(false);
			break;
		
		//
		case "M13":
		MCodes.schalteSpindelAn(true);
		MCodes.schalteKuelmittel(true);
			break;
			
		case "M14":
		MCodes.schalteSpindelAn(false);
		MCodes.schalteKuelmittel(true);
			break;
			
		default:
			System.out.println("MCode-Fehler");	//Fehler sollte hier gar nicht mehr auftreten können. Bitte Meldung, falls das der Fall sein sollte.
		}
}
	
	public static boolean queueIsEmpty() {
		return queue.isEmpty();
	}
	
}
