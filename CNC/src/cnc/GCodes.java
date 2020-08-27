package cnc;

import java.util.ArrayList;

/**
 * 
 * @author Jonas Heckerodt
 *
 */

public class GCodes extends Codes {
	
	private static double aenderungKoorX;
	private static double aenderungKoorY;
	private static ArrayList<String> zukuenftigePosNachSchritt = new ArrayList<String>();
	static double aktuellePosX;
	static double aktuellePosY;
	private static int alreadyCheckedGCodes = 0;
	private static boolean erfolgreich = false;
	private static boolean eilgang = false;

	//Überprüft die geplante Fahrbewegung des Codes G00
	static public boolean checkFahrenEilgang(double[] param) {
		double x = param[0];
		double y = param[1];
		
		try {
			
			pruefeMissingEingabeparameter(true, x, y);
			
		} catch (MissingParameterException e) {

			//Kein Parameter wurde mitgegeben --> Keine Fahrt möglich
			erfolgreich = false;
			return erfolgreich;
		}
			
	
		try {
			pruefeFahrbewegung(x, y);
		} catch(OutOfAreaException e) {
		erfolgreich = false;
		return erfolgreich;
		}
		erfolgreich = true;
		return erfolgreich;
		
	}
	
	
	
	//Repräsentation von Code G00.
	static public void fahrenEilgang(boolean simulation, double... param) {
		
		eilgang = true;
		double x = param[0];
		double y = param[1];
		
		if(!simulation) {
			LineAnimation.lineJustKreis(x,y);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			eilgang = false;
		} else {
			aenderungKoorX = x;
			aenderungKoorY = y;
		}

		
}
		
	
//	static public boolean checkFahrenGerade(double[] param) {
//		
//		double x = param[0];
//		double y = param[1];
//		
//		try {
//			
//			pruefeMissingEingabeparameter(true, x, y);
//			
//		}
//			catch (MissingParameterException e) {
//			
//				erfolgreich = false;
//				return erfolgreich;
//			}
//	
//		
//		try {
//			pruefeFahrbewegung(x, y);
//		} catch(OutOfAreaException e) {
//		erfolgreich = false;
//		return erfolgreich;
//		}
//		erfolgreich = true;
//		return erfolgreich;
//		
//	}
	
	
	
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
			pruefeMissingEingabeparameter(false, x, y, i, j);
			
		} catch(MissingParameterException m) {
			return false;
		}
		
		try {
			pruefeFahrbewegung(false);
		} catch(OutOfAreaException e) {
		erfolgreich = false;
		return erfolgreich;
		}
		erfolgreich = true;
		return erfolgreich;
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
			erfolgreich = false;
			return erfolgreich;
		}
		
		try {
			pruefeFahrbewegung(false);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		erfolgreich = false;
		return erfolgreich;
		}
		erfolgreich = true;
		return erfolgreich;
		
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
		fahrenEilgang(false, Main.getHomePosX(), 1050-Main.getHomePosY());		//Rufe die fahrenEilgang-Methode auf mit den Koordinaten des Homepunkts.
																	//Check von Machbarkeit erfolgt in der Methode zum Fahren im Eilgang
	}
	
	/**Prüft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfläche stattfinden. Diese Methode richtet sich ausschließlich an Geradenfahrbewegungen.
	 * 
	 * @param xKoordinaten alle X-Punkte, die theoretisch angefahren werden sollen
	 * @param yKoordianten alle Y-Punkte, die theoretisch angefahren werden sollen
	 * @throws OutOfAreaException Wenn sich Punkt außerhalb der Arbeitsfläche befinden
	 */
	
	static public void pruefeFahrbewegung(double xKoor, double yKoor) throws OutOfAreaException {

		
		double boundX = GUI.getWidth();
		double boundY = GUI.getHeight();
		
//		getZukuenftigePos(Codes.getQueueSize());
		
//		double newXKoor = xKoor;
//		double newYKoor = yKoor;
		
		
				if(xKoor > boundX || xKoor < 0 ||  yKoor > boundY || yKoor < 0) {
					alreadyCheckedGCodes--;
					zukuenftigePosNachSchritt.remove(zukuenftigePosNachSchritt.size()-1);
					erfolgreich = false;
					throw new OutOfAreaException();
			}
				
				erfolgreich = true;
				
		
		
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
		
			for(int i = 0; i < stellen.length; i++) {
				if(stellen[i] == -10001) {
					throw new MissingParameterException();		//Wenn einer von den benötigten Parametern fehlt, wird direkt eine Exception unter Angabe der fehlenden Stelle geworfen. Kann von den Catch-Blöcken der aufrufenden Methoden trotzdem zu einem validen Fahrbefehl umgeformt werden.
				}
			
		}
		
	}
	
//	
//	static public void getZukuenftigePos(int stelleInArray) {
//		
//		if(Codes.getEnqueuedGCodes() == 0 && stelleInArray == 0) {
//			aktuellePosX = Main.getPosX();
//			aktuellePosY = Main.getPosY();
//			zukuenftigePosNachSchritt.add(String.valueOf(Main.getPosX()) + " " + String.valueOf(Main.getPosY()));
//		}
//		
//		if((Codes.getEnqueuedGCodes() != 0 && stelleInArray == 0) || zukuenftigePosNachSchritt.size() == 0) {
//			
//			while(true) {
//				if(!Codes.IsDoRunning()) {
//					aktuellePosX = Main.getPosX();
//					aktuellePosY = Main.getPosY();
//					zukuenftigePosNachSchritt.add(String.valueOf(Main.getPosX()) + " " + String.valueOf(Main.getPosY()));
//					break;
//				}
//			}
//		}
//		
//		String[] koordinaten = zukuenftigePosNachSchritt.get(alreadyCheckedGCodes).split(" ");
//		alreadyCheckedGCodes++;
//		
//		aktuellePosX = Double.parseDouble(koordinaten[0]);
//		aktuellePosY = Double.parseDouble(koordinaten[1]);
//		
//		 Codes.simuliereBefehl( stelleInArray - 1);
//		 double neuePosX = aenderungKoorX;
//		 double neuePosY = aenderungKoorY;
//		 zukuenftigePosNachSchritt.add(neuePosX + " " + neuePosY);
//		 
//	}
	
	static public void clearZukuenftigePosNachSchritt() {
		
			zukuenftigePosNachSchritt = new ArrayList<String>();
		
	}
	
	static public void resetAlreadyCheckedCodes() {
		alreadyCheckedGCodes = 0;
	}
	
	public static boolean getEilgang() {
		return eilgang;
	}
	
}
