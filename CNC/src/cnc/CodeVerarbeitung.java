package cnc;

import java.util.regex.Pattern;
import javafx.application.Platform;

/**
 * Enthält Thread, der zur Ausführung der Codes verwendet wird
 * @author Jonas Heckerodt
 *
 */
public class CodeVerarbeitung extends Codes implements Runnable {
	
	private static String[] befehlEingangDo;
	private static String[] befehl = new String[5];
	private static double[] parameter = new double[4];
	private static boolean weiter =true;
	private static boolean ende =false ; 
	private static boolean endeMcode = false;
	
	public void run() {
			
		while(true) {
			
//			if(!queueIsEmpty()&&queue.size() != 1) {
			ende = false;
//			}
			if(queueIsEmpty()) {
				while(!ende && !endeMcode) {		//Warteschleife: So lang fortgeführt, bis der letzte Code fertig ausgeführt ist
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						
						System.out.println("Der Thread CodeVerarbeitung wurde unterbrochen");
					}
				}
				Codes.addStringToOutput("Warteschlange erfolgreich abgearbeitet"); //Wenn die Queue leer ist (entweder kein Code eingegeben, oder alle ausgeführt) kommt eine kurze Meldung.
				
				GUI.clearTimelines();
				GUI.setCodeVerarbeitungStartenTrue();
				Platform.runLater(()->{
				GUI.startBtn.setText("Start");
				});
				break;
				}
			endeMcode = false;
			befehlEingangDo = queue.get(0).split(" ");
			
			System.arraycopy(befehlEingangDo, 0, befehl, 0, befehlEingangDo.length);
			
			// Es wird zur reibungslosen Weiterverarbeitung der Parameter ein Double-Array erzeugt
			parameter = new double[befehl.length-1];
			weiter = false;
		
			for(int i=0; i < parameter.length; i++) {
				
				parameter[i] = Double.parseDouble(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
				}
				
			//Unterscheidung, ob M- oder G-Code vorliegt (entschieden an mitgegebenem ersten Buchstaben)
			switch(befehl[0].charAt(0)) {
				
				case 'M':
					doMCodes(befehl);
					break;
				case 'G':
					doGCodes(befehl, parameter);		
					break;
				}
			
			Codes.incAusgefuehrteCodes();
			
			while(!weiter) {		//Warteschleife: So lang fortgeführt, bis aktueller Code fertig ausgeführt
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					
					System.out.println("Der Thread CodeVerarbeitung wurde unterbrochen");
				}
				
				if(ausgefuehrteCodes == Codes.getInitialQueueSize()) {
					break;
				}
			}
			
			
			String doneBefehl="";

//			Wenn ein Parameter leer war (-10001) dann umformen in leer
			for(int i = 0; i < befehl.length; i++) {
				if(Pattern.matches(".?-10001", befehl[i])) {
					befehl[i] = null;
					continue;
				}
				//Bau des ausgeführten Befehls
				if(doneBefehl == "") {
					doneBefehl = befehl[i];
					continue;
				}
				
				 doneBefehl += " " + befehl[i];	//verketten von Befehl mit nicht leeren Parametern
			}
			Codes.addStringToOutput("Done: " + doneBefehl); //Ausgabe, dass Befehl erfolgreich durchgeführt werde
			Codes.addToDoneCodes(doneBefehl);				//Speicherung in Array für spätere Ausgabe in XML
			queue.remove(0);								//Entfernung des eben bearbeiteten Befehls
		}
		
		XML.save(Codes.getDoneCodes());				//Abspeicherung der ausgeführten Codes in XML
		Main.initializeThreadVerarbeitung();		//Setzt den Thread auf seinen Ursprungszustand zurück, sodass er wieder über die jeweilige Methode gestartet werden kann
	}

	public static void setBoolWeiter(Boolean wert) {
		weiter = wert;
		
	}
	public static void setEndetrue() {
		ende = true;
	}
	public static void setEndeMCodetrue() {
		endeMcode = true;
	}
	
}
