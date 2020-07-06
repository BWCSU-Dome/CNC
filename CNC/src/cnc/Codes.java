package cnc;

import java.util.ArrayList;

public class Codes {
	
	public static ArrayList<String> queue = new ArrayList<String>();
	private static String[] befehlEingang;
	private static String[] befehl = new String[5];
	private static double[] parameter = new double[4];
	
	public static void main(String [] args) {
		
		enqueueBefehl("M00");
		doBefehl(false);
	}
	
	public static void enqueueBefehl(String stringEingang) {
		
		
		befehlEingang = stringEingang.split(" ");
		
		System.arraycopy(befehlEingang, 0, befehl, 0, befehlEingang.length);
		
		
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
		
		for(int i=0; i < befehl.length; i++) {
			switch(i) {
			case 0:
				if(befehl[i].startsWith("G") || befehl[i].startsWith("M"))
					continue;
				
				System.out.println("G oder M Befehl switch case failed.");
				break;
				
			case 1:
				if(befehl[i].startsWith("X"))
					continue;
				
				befehl[i] = "X" + befehl[i];
				break;
				
			case 2:
				if(befehl[i].startsWith("Y"))
					continue;
				
				befehl[i] = "Y" + befehl[i];
				break;
				
			case 3:
				if(befehl[i].startsWith("I"))
					continue;
				
				befehl[i] = "I" + befehl[i];
				break;
				
			case 4:
				if(befehl[i].startsWith("J"))
					continue;
				
				befehl[i] = "J" + befehl[i];
				break;
				
			}
		}
		
		for(int i=0; i < parameter.length; i++) {
			parameter[i] = Integer.parseInt(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
			}
	
	
	boolean successful = false;
			//Unterscheidung, ob M- oder G-Code vorliegt (entschieden an mitgegebenem ersten Buchstaben)
			switch(befehl[0].charAt(0)) {
			case 'M':
				successful = true;
				break;
			case 'G':
				successful = checkGCodes(befehl);
				break;
			default:
				System.out.println("Das lief nicht gut. Fehler im Switch-Case-Block in der Klasse RegEx");
			}
		
			if(successful)
			queue.add(befehlToString(befehl));
			
			System.out.println("Shit worked, me boi :)");
			
	}
	
	public static void doBefehl(boolean simulation) {
		
		if(queueIsEmpty()) {
			System.out.println("Die Warteschlange ist leer.");
			return;
		}
		
		
		befehlEingang = queue.get(0).split(" ");
		
		System.arraycopy(befehlEingang, 0, befehl, 0, befehlEingang.length);
		
		// Es wird zur reibungslosen Weiterverarbeitung der Parameter ein Double-Array erzeugt
		parameter = new double[befehl.length-1];
				
		
		for(int i=0; i < parameter.length; i++) {
			parameter[i] = Integer.parseInt(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
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
			
		if(!simulation)
		queue.remove(0);
	
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
	
	public static boolean checkGCodes(String[] code) {
	
		switch(code[0]) {
		case "G00":
		return(GCodes.checkFahrenEilgang(parameter));
			
		case "G01":
		return(GCodes.checkFahrenGerade(parameter));
			
			
		case "G02":
		return(GCodes.checkFahrenKreisImUhrzeigersinn(parameter));
			
		
		case "G03":
		return(GCodes.checkFahrenKreisGegenUhrzeigersinn(parameter));
			
			
		case "G28":
		return(GCodes.checkFahrenEilgang(Main.getHomePosX(), Main.getHomePosY()));
			
		default:
		System.out.println("GCode-Fehler");
		return false;
	}
	
}
	/*
	 * Methode, die lediglich dazu da ist, ein Array (insbesondere das Array "Befehl" in einen String umzuformen
	 */
	public static String befehlToString(String[] befehl) {
		String befehlsString = "";
		
		for(int i = 0; i < befehl.length; i++) {
			if(befehlsString == "") {
				befehlsString = befehl[i];
			} else {
				befehlsString = befehlsString + " " + befehl[i];
			}
			
		}
		
		return befehlsString;
	}
}
