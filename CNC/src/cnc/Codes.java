package cnc;

import java.util.ArrayList;

public class Codes {
	
	public static ArrayList<String> queue = new ArrayList<String>();
	private static String[] befehlEingangEnqueue;
	private static String[] befehlEingangDo;
	private static String[] befehl = new String[5];
	private static double[] parameter = new double[4];
	protected static int ausgefuehrteCodes = 0;
	protected static int enqueuedCodes = 0;
	private static boolean doRunning = false;
	
	
	public static void main(String [] args) {
		enqueueBefehl("G00 X11 Y11");
		enqueueBefehl("G00 X12 Y11");
		enqueueBefehl("G00 Y13");
		doBefehl(false, 0);
		doBefehl(false, 0);
		doBefehl(false, 0);
	}
	
	
	public static void enqueueBefehl(String stringEingang) {
		
		befehlEingangEnqueue = stringEingang.split(" ");
		
		befehl = new String[5];
		
		System.arraycopy(befehlEingangEnqueue, 0, befehl, 0, befehlEingangEnqueue.length);
		
		
		for(int i = 0; i < befehl.length; i++) {
			if(befehl[i] == null) {
				befehl[i] = "-10001";				//Wenn "Schublade" leer, auffüllen mit 00
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
			parameter[i] = Double.parseDouble(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
			}
		
		for(int i = 0; i < befehl.length; i++) {
			System.out.println(befehl[i]);				//Testweise Ausgabe des Array-Inhalts
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
		
			if(successful) {
				queue.add(befehlToString(befehl));
				enqueuedCodes++;
			} else {
				System.out.println("fehlerhafter Befehl: " + stringEingang);
			}
			
			
			System.out.println("Shit worked, me boi :)");
			
	}
	
	public static void doBefehl(boolean simulation, int befehlNr) {
		
		
		if(simulation) {
		befehlEingangDo = befehlEingangEnqueue;
		} else {
			if(queueIsEmpty()) {
				System.out.println("Die Warteschlange ist leer.");
				return;
			}
		befehlEingangDo = queue.get(befehlNr).split(" ");
		}
		
		System.arraycopy(befehlEingangDo, 0, befehl, 0, befehlEingangDo.length);
		
		// Es wird zur reibungslosen Weiterverarbeitung der Parameter ein Double-Array erzeugt
		parameter = new double[befehl.length-1];
				
		
		for(int i=0; i < parameter.length; i++) {
			parameter[i] = Double.parseDouble(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
			}
			
			//Unterscheidung, ob M- oder G-Code vorliegt (entschieden an mitgegebenem ersten Buchstaben)
			switch(befehl[0].charAt(0)) {
			case 'M':
				doMCodes(befehl);
				break;
			case 'G':
				doGCodes(simulation, befehl);		
				break;
			default:
				System.out.println("Das lief nicht gut. Fehler im Switch-Case-Block in der Klasse RegEx");
			}
			
		if(!simulation) {
		ausgefuehrteCodes++;
		queue.remove(0);
		}
	}
	
	
	protected static void doGCodes(boolean simulation, String[] code) {
		
		doRunning = true;
		//Aufruf des gewünschten Codes nach eingegebener, gewünschter Funktion
		switch(code[0]) {
		
		case "G00":
		GCodes.fahrenEilgang(simulation, parameter);
			break;
			
		case "G01":
		GCodes.fahrenGerade(simulation, parameter);
			break;
			
		case "G02":
		GCodes.fahrenKreisImUhrzeigersinn(simulation, parameter);
			break;
		
		case "G03":
		GCodes.fahrenKreisGegenUhrzeigersinn(simulation, parameter);
			break;
			
		case "G28":
		GCodes.fahrenZuHome();
			break;
			
		default:
		System.out.println("GCode-Fehler"); //Fehler sollte hier gar nicht mehr auftreten können. Bitte Meldung, falls das der Fall sein sollte.
		
	
	}
		if(!simulation)
		System.out.println(code[0] + " " + code[1] + " " + code[2] + " " + code[3] + " " + code[4] + " " + "ausgeführt");
		
		doRunning = false;
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
		return true;
			
		
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
	
	public static int getQueueSize() {
		return queue.size();
	}
	
	
	public static void emptyQueue() {
		queue = new ArrayList<String>();
	}
	
	public static int getAusgefuehrteCodes() {
		return ausgefuehrteCodes;
	}
	
	public static boolean IsDoRunning() {
		return doRunning;
	}
	
	public static int getEnqueuedCodes() {
		return enqueuedCodes;
	}
	
	public static String getQueue() {
		for(int i = 0; i < queue.size(); i++) {
			return queue.get(i);
		}
		return null;
	}
	
	public static void correctBefehlEingangEnqueue(String valueToBeAdded, String argArt) {
		
		String[] temp = new String[3];
		
		System.arraycopy(befehlEingangEnqueue, 0, temp, 0, befehlEingangEnqueue.length);
		
		if(argArt == "X") {
			temp[2] = temp[1];
			temp[1] = argArt + valueToBeAdded;
		}
		if(argArt == "Y") {
			temp[2] = argArt + valueToBeAdded;
		}
		befehlEingangEnqueue = new String[3];
		
		System.arraycopy(temp, 0, befehlEingangEnqueue, 0, temp.length);
	}
	
	
	
	
}
