package cnc;

/**
 * 
 * @author Jonas Heckerodt
 * Dient zur Überprüfung auf Ausführbarkeit (richtige Anzahl an Argumenten, Bewegung innerhalb der Arbeitsfläche) und der letztendlichen Ausführung der Codes
 *
 */

public class GCodes extends Codes {
	

	static double aktuellePosX;
	static double aktuellePosY;
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
	static public void fahrenEilgang(double... param) {
		
		eilgang = true;
		double x = param[0];
		double y = param[1];
		
			LineAnimation.lineJustKreis(x,y);	//Aufruf der Animation
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			eilgang = false;

}
	
	//Repräsentation von Code G01.
	static public void fahrenGerade(double[] param) {
		double x = param[0];
		double y = param[1];
		
		LineAnimation.line(x, y); //Aufruf der Animation

	}

	
	/* Überprüft Ausführbarkeit geplanter Kreisfahrbewegung
	 *  @param param Array, das die jeweiligen Parameter für die Kreisbogenberechnung mit gibt (X, Y: Zielpunkt, I, J: Mittelpunkt)
	 */
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
	static public void fahrenKreisImUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
			CircleAnimation.kreis(x, y, i, j);  //Hier wird die Methode zur Kreisfahrt gerufen

		
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
	static public void fahrenKreisGegenUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
			CircleAnimation.kreis(x, y, i, j);  //Hier wird die Methode zur Kreisfahrt gerufen

		
	}
	
	//Repräsentation von Code G28.
	static public void fahrenZuHome() {
		fahrenEilgang(Main.getHomePosX(), 1050-Main.getHomePosY());		//Rufe die fahrenEilgang-Methode auf mit den Koordinaten des Homepunkts.
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
				
				if(xKoor > boundX || xKoor < 0 ||  yKoor > boundY || yKoor < 0) {
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
	
	public static boolean getEilgang() {
		return eilgang;
	}
	
}
