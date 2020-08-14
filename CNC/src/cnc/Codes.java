package cnc;

import java.util.ArrayList;
import java.util.regex.Pattern;


/** Dient zur Prüfung von eingegebenen Codes. Hier werden sie, sofern die Eingabe syntaktisch korrekt war, weiter auf Möglichkeit der Ausführung geprüft. Falls erfolgreich, landen die Befehle in der Queue.
 *  Hier wird auch der Thread zur Code-Ausführung aufgerufen, sowie gegebenenfalls gestoppt.
 * @author Jonas Heckerodt
 * 
 */
public class Codes {
	
	public static ArrayList<String> queue = new ArrayList<String>(); //Die Code-Warteschlange
	private static String[] befehlEingangEnqueue;
	private static String[] befehlEingangDo;
	private static String[] befehl = new String[5];
	protected static int ausgefuehrteCodes = 0;
	protected static int enqueuedGCodes = 0;
	private static boolean doRunning = false;
	private static ArrayList<String> doneCodes = new ArrayList<String>();
	private static int initialQueueSize = 0;
	private static boolean enqueueingSuccessful = false;
	
	
	
	//Startmethode für Ausführungs-Thread (siehe Klasse CodeVerarbeitung)
	public static void startVerarbeitung() {
		Main.launchCodeRun();
	}
	
	//Stopmethode für Ausführungs-Thread (siehe Klasse CodeVerarbeitung)
	public static void stopVerarbeitung() {
		Main.stopCodeRun();
	}	
	
	/**Wird aufgerufen, wenn über die GUI bzw XML-Input Codes hinzugefügt werden
	 * 
	 * @param String-Array, das alle eingegebenen Befehle beinhaltet
	 */
	public static void neubildenQueue(String[] neueBefehle) {
	
		while(true) {
			
			if(!IsDoRunning()) { //Warte, bis die Ausführung gestoppt ist.
				
					enqueueingSuccessful = true;	//Variable, die bestimmt, ob alle bisher verarbeiteten Codes fehlerfrei und somit ausführbereit sind.
				
					queue = new ArrayList<String>();	//Neuinitalisierung der Warteschlange
					
					GCodes.resetAlreadyCheckedCodes();	
					
					GCodes.clearZukuenftigePosNachSchritt(); //Neuinitialisierung des in GCodes befindlichen Arrays, dass die zukünftigen Positionen aufnimmt, um auf dessen Basis weitere zukünftige Positionen berechnet.
					
					enqueuedGCodes = 0;
					
					RegularExpression.checkCodeFormatierung(neueBefehle); //Aufrufen der Syntaxüberprüfung für alle Codes
					
					if(!enqueueingSuccessful) 
						return;					//Falls Verarbeitung nicht erfolgreich, findet hier der Rücksprung in die GUI statt
					
					initialQueueSize = queue.size();
					ausgefuehrteCodes = 0;
					Codes.addStringToOutput("Codes erfolgreich hinzugefügt.");
					GUI.setCodeVerarbeitungStartenTrue();
					break;
			}
		}
		
	}
	
	/**
	 * Rollt alle Operationen zurück, die durch letzten Code-Hinzufüge-Vorgang, vor dem fehlerhaften Code, hinzugefügt wurden.
	 * 
	 */
	public static void abbrechenEnqueueing() { 
		queue = new ArrayList<String>();
		enqueueingSuccessful = false;
		return;
	}
	
	/**
	 * Überprüft Code und fügt diese, sofern ausführbar, in Queue ein
	 * @param stringEingang -> Code, der zur Queue hinzugefügt werden soll
	 */
	
	public static void enqueueBefehl(String stringEingang) {
		
		double[] parameter = new double[4];
		
		befehlEingangEnqueue = stringEingang.split(" ");		//Bildung von Array aus String
		
		befehl = new String[5];
		
		System.arraycopy(befehlEingangEnqueue, 0, befehl, 0, befehlEingangEnqueue.length);   //Kopieren in Array statischer Länge
		
		
		for(int i = 0; i < befehl.length; i++) {
			if(befehl[i] == null) {
				befehl[i] = "-10001";				//Wenn Array-Stelle null, auffüllen mit -10001
			}
		}
		
		// Es wird vorsorglich ein Double-Array erzeugt, das zur reibungslosen Weiterverarbeitung diesen Datentyp trägt. Enthält nur X-,Y-,I-,J-Parameter
			
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
			
			//fügt gegegebenfalls noch Parameterbezeichnung vor Wert ein -> zu Testzwecken
//		for(int i=0; i < befehl.length; i++) {
//			switch(i) {
//			case 0:
//				if(befehl[i].startsWith("G") || befehl[i].startsWith("M"))
//					continue;
//				
//				break;
//				
//			case 1:
//				if(befehl[i].startsWith("X"))
//					continue;
//				
//				befehl[i] = "X" + befehl[i];
//				break;
//				
//			case 2:
//				if(befehl[i].startsWith("Y"))
//					continue;
//				
//				befehl[i] = "Y" + befehl[i];
//				break;
//				
//			case 3:
//				if(befehl[i].startsWith("I"))
//					continue;
//				
//				befehl[i] = "I" + befehl[i];
//				break;
//				
//			case 4:
//				if(befehl[i].startsWith("J"))
//					continue;
//				
//				befehl[i] = "J" + befehl[i];
//				break;
//				
//			}
//		}
		
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
				successful = checkGCodes(befehl, parameter);
				if(successful)
					enqueuedGCodes++;
				break;
			}
			
		
			if(successful) {
				queue.add(befehlToString(befehl));			//Wenn Befehl erfolgreich getestet, Einfügen in Array
			} else {
				addStringToOutput("Fehlerhafter Code: " + stringEingang);	//Sonst Textausgabe in GUI und das Zurückrollen der vorher geprüften Codes anstarten
				abbrechenEnqueueing();
			}
			
			
	}
	
	/**
	 * Ruft Methoden auf mit gesetztem Simulationsparameter, sodass die zukünftige Position bestimmt werden kann
	 * @param befehlNr -> für welchen Befehl die Prüfung erfolgen soll
	 */
	
	protected static void simuliereBefehl(int befehlNr) {
		
		double[] parameter = new double[4];
		
		befehlEingangDo = befehlEingangEnqueue;
		
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
				doGCodes(true, befehl, parameter);		
				break;
			}
			
	}
	
	
	/*
	 * Ruft zugehörige Methode zur Prüfung/Ausführung in der Klasse GCodes auf
	 * @param simulation -> ob eine simulierte oder echte Ausführung des Codes gewünscht
	 * @param code -> enthält auszuführenden Code
	 * @param parameter -> enthält zugehörige Paramter, um mit diesen die jeweilige Methode auzurufen
	 */
	
	protected static void doGCodes(boolean simulation, String[] code, double[] parameter) {
		
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
		GCodes.fahrenZuHome(); //Braucht keine Parameter: Zieht sich die Home-Position aus Gettern der Main-Klasse.
			break;
	}
		
		if(!simulation)
		System.out.println(code[0] + " " + code[1] + " " + code[2] + " " + code[3] + " " + code[4] + " " + "ausgeführt");
		
		doRunning = false;
		return;

}
		
	/**Ruft zugehörige Methode zur Ausführung in der Klasse MCodes auf
	 * 
	 * @param code -> enthält auszuführenden Code
	 */
	
	protected static void doMCodes(String[]code) {
		
		doRunning = true;
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
		
		doRunning = false;
}
	
	public static boolean queueIsEmpty() {
		return queue.isEmpty();
	}
	
	/**
	 * 
	 * @param code -> enthält zu prüfenden Code
	 * @param parameter -> enthält zugehörige Paramter, um mit diese die jeweilige Methode auzurufen
	 * @return Gibt zurück, ob Simulation erfolgreich verlaufen, oder nicht (Stichwort: OutOfAreaException, MissingParameterException)
	 */
	
	public static boolean checkGCodes(String[] code, double[] parameter) {
	
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
	 * Methode, die lediglich dazu da ist, ein Array (insbesondere das Array "Befehl") in einen String umzuformen
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
	
	public static int getEnqueuedGCodes() {
		return enqueuedGCodes;
	}
	
	public static ArrayList<String> getQueue() {
		return queue;
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
	
	public static int getInitialQueueSize() {
		return initialQueueSize;
	}
	
	
	public static void addStringToOutput(String text) {
		String temp = GUI.getOutputConsole();
		
		GUI.setTXTOutputConsole(temp + "\n" + text);
		
	}
	
	
	protected static boolean getLastCodeSuccessful() {
		return enqueueingSuccessful;
	}

	protected static void addToDoneCodes(String code) {
		doneCodes.add(code);
	}
	
	protected static void incAusgefuehrteCodes() {
		ausgefuehrteCodes++;
	}
	
	
	
	
}
