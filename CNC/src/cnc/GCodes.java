package cnc;

import java.util.ArrayList;

public class GCodes extends Codes {
	
	private static double aenderungKoorX;
	private static double aenderungKoorY;
	private static ArrayList<String> zukuenftigePosNachSchritt = new ArrayList<String>();
	static double aktuellePosX;
	static double aktuellePosY;
	private static int alreadyCheckedGCodes = 0;

	
	static public boolean checkFahrenEilgang(double... param) {
		double x = param[0];
		double y = param[1];
		
		try {
			
			pruefeMissingEingabeparameter(true, param[0], param[1]);
			
		} catch (MissingParameterException e) {
			
		switch(e.getStelle()) {
		
		case 0:
			System.out.println("X-Koordinate fehlt.");
			System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gefahren.");
			x = 0;
			correctBefehlEingangEnqueue(Double.toString(x), "X");
			break;
		
		case 1:
			System.out.println("Y-Koordinate fehlt.");
			System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gefahren.");
			y = 0;
			correctBefehlEingangEnqueue(Double.toString(y), "Y");
			break;

			//Kein Parameter wurde mitgegeben --> Keine Fahrt möglich
		case -1:
			System.out.println("Es kann keine Gerade ohne Parameter gefahren werden.");
			return false;
		}
			
	}
		try {
			pruefeFahrbewegung(x, y);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return false;
		}
		
		return true;
		
	
}
	
	
	
	//Repräsentation von Code G00.
	static public void fahrenEilgang(boolean simulation, double... param) {
		
		double x = param[0];
		double y = param[1];
		
		if(!simulation) {
		System.out.println("Fahren Eilgang: " + x + " " + y);
		} else {
			aenderungKoorX = x;
			aenderungKoorY = y;
		}
		
}
		
	
	static public boolean checkFahrenGerade(double[] param) {
		
		double x = param[0];
		double y = param[1];
		
		try {
			
			pruefeMissingEingabeparameter(true, param[0], param[1]);
			
		}
			catch (MissingParameterException e) {
				
				switch(e.getStelle()) {
				
				case 0:
					System.out.println("X-Koordinate fehlt.");
					System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gezeichnet.");
					x = 0;
					break;
				
				case 1:
					System.out.println("Y-Koordinate fehlt.");
					System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gezeichnet.");
					y = 0;
					break;
					
					//Kein Parameter wurde mitgegeben --> Keine Fahrt möglich
				case -1:
					System.out.println("Es kann keine Gerade ohne Parameter gezeichnet werden.");
					return false;
				}
				
			}
		
		try {
			pruefeFahrbewegung(x, y);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return false;
		}
		return true;
		
	}
	
	
	
	//Repräsentation von Code G01.
	static public void fahrenGerade(boolean simulation, double[] param) {
		double x = param[0];
		double y = param[1];
		
		if(!simulation) {
		LineAnimation.line(x, y);
		} else {
			aenderungKoorX = x;
			aenderungKoorY = y;
		}
		
		
		}
	
	
	
	
	static public boolean checkFahrenKreisImUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		try {
			pruefeMissingEingabeparameter(false, param[0], param[1], param[2], param[3]);
			
		} catch(MissingParameterException m) {
			System.out.println("Es fehlen Parameter. Für einen Kreisbogen werden x, y, i und j benötigt.");
			return false;
		}
		
		try {
			pruefeFahrbewegung(false);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return false;
		}
		return true;
	}
	
	/* Repräsentation von Code G02.
	 * Methode zur Kreisbogenfahrt (im Uhrzeigersinn)
	 * @param param Array, das die jeweiligen Parameter für die Kreisbogenberechnung mit gibt (X, Y: Zielpunkt, I, J: Mittelpunkt)
	 */
	static public void fahrenKreisImUhrzeigersinn(boolean simulation, double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		if(!simulation) {
			CircleAnimation.kreis(x, y, i, j);  //Hier wird die Methode zur Kreisfahrt gerufen
			} else {
				aenderungKoorX = x;
				aenderungKoorY = y;
			}
		
	}
	
	static public boolean checkFahrenKreisGegenUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		try {
			pruefeMissingEingabeparameter(false, x, y, i, j);
			
		} catch(MissingParameterException m) {
			System.out.println("Es fehlen Parameter. Für einen Kreisbogen werden x, y, i und j benötigt.");
			return false;
		}
		
		
		
		try {
			pruefeFahrbewegung(false);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return false;
		}
		
		return true;
		
	}
	
	
	/** Repräsentation von Code G03.
	 *  Methode zur Kreisbogenfahrt (gegen Uhrzeigersinn)
	 * @param param Array, das die jeweiligen Parameter für die Kreisbogenberechnung mit gibt (X, Y: Zielpunkt, I, J: Mittelpunkt)
	 */
	static public void fahrenKreisGegenUhrzeigersinn(boolean simulation, double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		if(!simulation) {
			CircleAnimation.kreis(x, y, i, j);  //Hier wird die Methode zur Kreisfahrt gerufen
			} else {
				aenderungKoorX = x;
				aenderungKoorY = y;
			}
		
	}
	
	//Repräsentation von Code G28.
	static public void fahrenZuHome() {
		
		fahrenEilgang(false, Main.getHomePosX(), Main.getHomePosY());		//Rufe die fahrenEilgang-Methode auf mit den Koordinaten des Homepunkts.
																	//Check von Machbarkeit erfolgt in der Methode zum Fahren im Eilgang
	}
	
	/**Prüft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfläche stattfinden. Diese Methode richtet sich ausschließlich an Geradenfahrbewegungen.
	 * 
	 * @param xKoordinaten alle X-Punkte, die theoretisch angefahren werden sollen
	 * @param yKoordianten alle Y-Punkte, die theoretisch angefahren werden sollen
	 * @throws OutOfAreaException Wenn sich Punkt außerhalb der Arbeitsfläche befinden
	 */
	
	static public void pruefeFahrbewegung(double xKoor, double yKoor) throws OutOfAreaException {

		String[] koordinaten;
		
		double boundX = GUI.getWidth();
		double boundY = GUI.getHeight();
		
		getZukuenftigePos(Codes.getQueueSize());
		
		if(Codes.getQueueSize() == 0 && Codes.getEnqueuedGCodes() == 0) {
			koordinaten = new String[2];
			koordinaten[0] = Double.toString(Main.getHomePosX());
			koordinaten[1] = Double.toString(Main.getHomePosX());
		} else {
			koordinaten = zukuenftigePosNachSchritt.get(Codes.getEnqueuedGCodes()).split(" ");
		}
		
		double newXKoor = Double.parseDouble(koordinaten[0]) + xKoor;
		double newYKoor = Double.parseDouble(koordinaten[1]) + yKoor;
		
		
				if(newXKoor > boundX || boundX+newXKoor < 0 ||  newYKoor > boundY || boundY+newYKoor < 0) {
					alreadyCheckedGCodes--;
					zukuenftigePosNachSchritt.remove(zukuenftigePosNachSchritt.size()-1);
					throw new OutOfAreaException();
			}
		
		
	}
	
	
	/*
	 * Prüft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfläche stattfinden. Diese Methode richtet sich ausschließlich an Kreisfahrtbewegungen.
	 */
	static public void pruefeFahrbewegung(boolean isGerade) throws OutOfAreaException {
		
			//Hier findet die Überprüfung der Kreispunkte statt
		
	}
	
	
	/**Prüft, ob eine hinreichende Zahl an Eingabedaten vorhanden sind. Gibt auch aus, welche Koordinaten fehlen, um ggf trotzdem die Fahrbewegung durchzuführen.
	 * 
	 * @param isGerade zeigt an, ob es sich bei der zu überprüfenden Fahrbewegung um eine Gerade handelt
	 * @param stellen bekommt die Parameter, mit denen der Code ausgeführt werden soll: Das ist der Hauptprüfungsgegenstand
	 * @throws MissingParameterException Wenn die Anzahl der mitgegebenen Parameter nicht ausreicht, wird diese Exception geworfen
	 */
	static public void pruefeMissingEingabeparameter(boolean isGerade, double... stellen) throws MissingParameterException {
		
		if(isGerade) {
			if(stellen[0] == -10001 && stellen[1] == -10001)
				throw new MissingParameterException(-1);		//Wenn eine Gerade gefahren werden soll und beide Parameter (X, Y) fehlen, wird Exception mit Parameter -1 geworfen --> teilt aufrufender Methode mit, dass kein Kreis ohne Parameter gezeichnet werden kann
		}
		
		
			for(int i = 0; i < stellen.length; i++) {
				if(stellen[i] == -10001) {
					throw new MissingParameterException(i);		//Wenn einer von den benötigten Parametern fehlt, wird direkt eine Exception unter Angabe der fehlenden Stelle geworfen. Kann von den Catch-Blöcken der aufrufenden Methoden trotzdem zu einem validen Fahrbefehl umgeformt werden.
				}
			
		}
		
	}
	
	
	static public void getZukuenftigePos(int stelleInArray) {
		
		if(Codes.getEnqueuedGCodes() == 0 && stelleInArray == 0) {
			aktuellePosX = Main.getPosX();
			aktuellePosY = Main.getPosY();
			zukuenftigePosNachSchritt.add(String.valueOf(Main.getPosX()) + " " + String.valueOf(Main.getPosY()));
		}
		
		if((Codes.getEnqueuedGCodes() != 0 && stelleInArray == 0) || zukuenftigePosNachSchritt.size() == 0) {
			
			while(true) {
				if(!Codes.IsDoRunning()) {
					aktuellePosX = Main.getPosX();
					aktuellePosY = Main.getPosY();
					zukuenftigePosNachSchritt.add(String.valueOf(Main.getPosX()) + " " + String.valueOf(Main.getPosY()));
					break;
				}
							
			}
			
		}
		
		String[] koordinaten = zukuenftigePosNachSchritt.get(alreadyCheckedGCodes).split(" ");
		alreadyCheckedGCodes++;
		
		aktuellePosX = Double.parseDouble(koordinaten[0]);
		aktuellePosY = Double.parseDouble(koordinaten[1]);
		
		 Codes.doBefehl(true, stelleInArray - 1);
		 aktuellePosX += aenderungKoorX;
		 aktuellePosY += aenderungKoorY;
		 zukuenftigePosNachSchritt.add(aktuellePosX + " " + aktuellePosY);
		 System.out.println("Aktuelle Pos X:" + aktuellePosX + " Y:" + aktuellePosY);
		 
	}
	
	static public void clearZukuenftigePosNachSchritt() {
		for(int i = 0; i < zukuenftigePosNachSchritt.size(); i++) {
			zukuenftigePosNachSchritt.remove(0);
		}
	}
	
	static public void resetAlreadyCheckedCodes() {
		alreadyCheckedGCodes = 0;
	}
	
}
