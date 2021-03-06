package cnc;

import java.util.ArrayList;


/** @author Jonas Heckerodt
 * Dient zur Pr�fung von eingegebenen Codes. Hier werden sie, sofern die Eingabe syntaktisch korrekt war, weiter auf M�glichkeit der Ausf�hrung gepr�ft. Falls erfolgreich, landen die Befehle in der Queue.
 *  Hier wird auch der Thread zur Code-Ausf�hrung aufgerufen, sowie gegebenenfalls gestoppt.
 */
public class Codes {
	
	public static ArrayList<String> queue = new ArrayList<String>(); //Die Code-Warteschlange
	private static String[] befehlEingangEnqueue;
	private static String[] befehl = new String[5];
	protected static int ausgefuehrteCodes = 0;
	protected static int enqueuedGCodes = 0;
	private static boolean doRunning = false;
	private static ArrayList<String> doneCodes = new ArrayList<String>();
	private static int initialQueueSize = 0;
	private static boolean enqueueingSuccessful = false;
	
	
	//Startmethode f�r Ausf�hrungs-Thread (siehe Klasse CodeVerarbeitung)
	public static void startVerarbeitung() {
		Main.launchCodeRun();
	}
	
	//Stopmethode f�r Ausf�hrungs-Thread (siehe Klasse CodeVerarbeitung)
	public static void stopVerarbeitung() {
		Main.stopCodeRun();
	}	
	
	/**Wird aufgerufen, wenn �ber die GUI bzw XML-Input Codes hinzugef�gt werden
	 * 
	 * @param String-Array, das alle eingegebenen Befehle beinhaltet
	 */
	public static void neubildenQueue(String[] neueBefehle) {
	
		while(true) {
			
			if(!IsDoRunning()) { //Warte, bis die Ausf�hrung gestoppt ist.
				
					enqueueingSuccessful = true;	//Variable, die bestimmt, ob alle bisher verarbeiteten Codes fehlerfrei und somit ausf�hrbereit sind.
				
					queue = new ArrayList<String>();	//Neuinitalisierung der Warteschlange
					
					enqueuedGCodes = 0;
					
					RegularExpression.checkCodeFormatierung(neueBefehle); //Aufrufen der Syntax�berpr�fung f�r alle Codes
					
					if(!enqueueingSuccessful) 
						return;					//Falls Verarbeitung nicht erfolgreich, findet hier der R�cksprung in die GUI statt
					
					initialQueueSize = queue.size();
					ausgefuehrteCodes = 0;
					Codes.addStringToOutput("Codes erfolgreich hinzugef�gt.");
					GUI.setCodeVerarbeitungStartenTrue();
					break;
			}
		}
		
	}
	
	/**
	 * Rollt alle Operationen zur�ck, die durch letzten Code-Hinzuf�ge-Vorgang, vor dem fehlerhaften Code, hinzugef�gt wurden.
	 * 
	 */
	public static void abbrechenEnqueueing() { 
		queue = new ArrayList<String>();
		enqueueingSuccessful = false;
		return;
	}
	
	/**
	 * �berpr�ft Code und f�gt diese, sofern ausf�hrbar, in Queue ein
	 * @param stringEingang -> Code, der zur Queue hinzugef�gt werden soll
	 */
	
	public static void enqueueBefehl(String stringEingang) {
		
		double[] parameter = new double[4];
		
		befehlEingangEnqueue = stringEingang.split(" ");		//Bildung von Array aus String
		
		befehl = new String[5];
		
		System.arraycopy(befehlEingangEnqueue, 0, befehl, 0, befehlEingangEnqueue.length);   //Kopieren in Array statischer L�nge
		
		for(int i = 0; i < befehl.length; i++) {
			if(befehl[i] == null) {
				befehl[i] = "-10001";				//Wenn Array-Stelle null, auff�llen mit -10001
			}
		}
		
		// Es wird vorsorglich ein Double-Array erzeugt, das zur reibungslosen Weiterverarbeitung diesen Datentyp tr�gt. Enth�lt nur X-,Y-,I-,J-Parameter
			
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
			
		
		for(int i=0; i < parameter.length; i++) {
			parameter[i] = Double.parseDouble(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, in der sich die Parameter nun befinden, klar ist, um welchen Parameter es sich handelt.
			if(parameter[i] == 10001)
				parameter[i] *= -1;
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
				queue.add(befehlToString(befehl));			//Wenn Befehl erfolgreich getestet, Einf�gen in Array
			} else {
				addStringToOutput("Fehlerhafter Code: " + stringEingang);	//Sonst Textausgabe in GUI und das Zur�ckrollen der vorher gepr�ften Codes anstarten
				abbrechenEnqueueing();
			}
	}
	
	
	/*
	 * Ruft zugeh�rige Methode zur Pr�fung/Ausf�hrung in der Klasse GCodes auf
	 * @param simulation -> ob eine simulierte oder echte Ausf�hrung des Codes gew�nscht
	 * @param code -> enth�lt auszuf�hrenden Code
	 * @param parameter -> enth�lt zugeh�rige Paramter, um mit diesen die jeweilige Methode auzurufen
	 */
	
	protected static void doGCodes(String[] code, double[] parameter) {
		
		doRunning = true;
		//Aufruf des gew�nschten Codes nach eingegebener, gew�nschter Funktion
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
		GCodes.fahrenZuHome(); //Braucht keine Parameter: Zieht sich die Home-Position aus Gettern der Main-Klasse.
			break;
	}
		
		
		doRunning = false;
		return;

}
		
	/**Ruft zugeh�rige Methode zur Ausf�hrung in der Klasse MCodes auf
	 * 
	 * @param code -> enth�lt auszuf�hrenden Code
	 */
	
	protected static void doMCodes(String[]code) {
		
		doRunning = true;
		//Aufruf des gew�nschten Codes nach eingegebener, gew�nschter Funktion
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
		Main.setSpindelRechtslaufAktiv(true);
		MCodes.schalteKuelmittel(true);
			break;
			
		case "M14":
		MCodes.schalteSpindelAn(true);
		Main.setSpindelRechtslaufAktiv(false);
		MCodes.schalteKuelmittel(true);
			break;
		}
		
		doRunning = false;
}
	
	/**
	 * Eine Methode, die zu Code zugeh�rige Pr�fmethode aufruft
	 * @param code -> enth�lt zu pr�fenden Code
	 * @param parameter -> enth�lt zugeh�rige Paramter, um mit diese die jeweilige Methode auzurufen
	 * @return Gibt zur�ck, ob Simulation erfolgreich verlaufen, oder nicht (Stichwort: OutOfAreaException, MissingParameterException)
	 */
	
	public static boolean checkGCodes(String[] code, double[] parameter) {
	
		switch(code[0]) {
		case "G00":
		return(GCodes.checkFahrenEilgang(parameter));
		
			
		case "G01":
		return(GCodes.checkFahrenEilgang(parameter));
			
			
		case "G02":
		return(GCodes.checkFahrenKreisImUhrzeigersinn(parameter));
			
		
		case "G03":
		return(GCodes.checkFahrenKreisGegenUhrzeigersinn(parameter));
			
			
		case "G28":
		return true; //Hier kann kein Fehler auftreten, da die Home-Koordinaten bereits auf Richtigkeit gepr�ft wurden
			
		default:
		return false;
	}
	
}
	/*
	 * Methode, die ein Array in einen String umformt
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
	
	/*
	 * F�gt String in GUI-Textausgabe ein
	 */
	public static void addStringToOutput(String text) {
		String temp = GUI.getOutputConsole();
		
		GUI.setTXTOutputConsole(temp + "\n" + text);
	}
	
	
	//Im Folgenden sind nur noch Methoden, die Getter und Setter 
	
	public static boolean queueIsEmpty() {
		return queue.isEmpty();
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
	
	public static int getInitialQueueSize() {
		return initialQueueSize;
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
	public static ArrayList<String> getDoneCodes(){
		return doneCodes;
	}
	
	
	
}
