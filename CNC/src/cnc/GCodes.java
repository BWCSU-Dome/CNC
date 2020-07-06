package cnc;

public class GCodes extends Codes {

	
	//Repr�sentation von Code G00.
	static public void fahrenEilgang(double... param) {
		
		double x = param[0];
		double y = param[1];
		
		try {
			
			pruefeMissingEingabeparameter(true, param[0], param[1]);
			
		} catch (MissingParameterException e) {
			
		switch(e.getStelle()) {
		
		case 0:
			System.out.println("X-Koordinate fehlt.");
			System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gefahren.");
			break;
		
		case 1:
			System.out.println("Y-Koordinate fehlt.");
			System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gefahren.");
			break;

			//Kein Parameter wurde mitgegeben --> Keine Fahrt m�glich
		case -1:
			System.out.println("Es kann keine Gerade ohne Parameter gefahren werden.");
			return;
		}
			
	}
		
		
		try {
			pruefeFahrbewegung(param[0], param[1]);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return;
		}
		
		System.out.println(x + " " + y);
		
}
		
	
	//Repr�sentation von Code G01.
	static public void fahrenGerade(double[] param) {
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
					break;
				
				case 1:
					System.out.println("Y-Koordinate fehlt.");
					System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gezeichnet.");
					break;
					
					//Kein Parameter wurde mitgegeben --> Keine Fahrt m�glich
				case -1:
					System.out.println("Es kann keine Gerade ohne Parameter gezeichnet werden.");
					return;
				}
				
			}
		
		try {
			pruefeFahrbewegung(param[0], param[1]);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return;
		}
		
		LineAnimation.line(x, y);
		
		
		}
	
	/* Repr�sentation von Code G02.
	 * Methode zur Kreisbogenfahrt (im Uhrzeigersinn)
	 * @param param Array, das die jeweiligen Parameter f�r die Kreisbogenberechnung mit gibt (X, Y: Zielpunkt, I, J: Mittelpunkt)
	 */
	static public void fahrenKreisImUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		try {
			pruefeMissingEingabeparameter(false, param[0], param[1], param[2], param[3]);
			
		} catch(MissingParameterException m) {
			System.out.println("Es fehlen Parameter. F�r einen Kreisbogen werden x, y, i und j ben�tigt.");
			return;
		}
		
		
		
		try {
			pruefeFahrbewegung(false);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return;
		}
		
		CircleAnimation.kreis(x, y, i, j);
		
	}
	
	/** Repr�sentation von Code G03.
	 *  Methode zur Kreisbogenfahrt (gegen Uhrzeigersinn)
	 * @param param Array, das die jeweiligen Parameter f�r die Kreisbogenberechnung mit gibt (X, Y: Zielpunkt, I, J: Mittelpunkt)
	 */
	static public void fahrenKreisGegenUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		try {
			pruefeMissingEingabeparameter(false, param[0], param[1], param[2], param[3]);
			
		} catch(MissingParameterException m) {
			System.out.println("Es fehlen Parameter. F�r einen Kreisbogen werden x, y, i und j ben�tigt.");
			return;
		}
		
		
		
		try {
			pruefeFahrbewegung(false);
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return;
		}
		
		CircleAnimation.kreis(x, y, i, j);
		
	}
	
	//Repr�sentation von Code G28.
	static public void fahrenZuHome() {
		
		fahrenEilgang(Main.getHomePosX(), Main.getHomePosY());		//Rufe die fahrenEilgang-Methode auf mit den Koordinaten des Homepunkts.
	
	}
	
	
	
	/**Pr�ft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfl�che stattfinden. Diese Methode richtet sich ausschlie�lich an Geradenfahrbewegungen.
	 * 
	 * @param xKoordinaten alle X-Punkte, die theoretisch angefahren werden sollen
	 * @param yKoordianten alle Y-Punkte, die theoretisch angefahren werden sollen
	 * @throws OutOfAreaException Wenn sich Punkt au�erhalb der Arbeitsfl�che befinden
	 */
	
	static public void pruefeFahrbewegung(double xKoor, double yKoor) throws OutOfAreaException {
		
		double boundX = GUI.getWidth();
		double boundY = GUI.getHeight();
		
		double newXKoor = Main.getHomePosX();
		double newYKoor = Main.getHomePosY();
		
				if(newXKoor > boundX || boundX+newXKoor < 0 ||  newYKoor > boundY || boundY+newYKoor < 0) {
					throw new OutOfAreaException();
			}
		
		
	}
	
	
	/*
	 * Pr�ft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfl�che stattfinden. Diese Methode richtet sich ausschlie�lich an Kreisfahrtbewegungen.
	 */
	static public void pruefeFahrbewegung(boolean isGerade) throws OutOfAreaException {
		
			//Hier findet die �berpr�fung der Kreispunkte statt
		
	}
	
	
	/**Pr�ft, ob eine hinreichende Zahl an Eingabedaten vorhanden sind. Gibt auch aus, welche Koordinaten fehlen, um ggf trotzdem die Fahrbewegung durchzuf�hren.
	 * 
	 * @param isGerade zeigt an, ob es sich bei der zu �berpr�fenden Fahrbewegung um eine Gerade handelt
	 * @param stellen bekommt die Parameter, mit denen der Code ausgef�hrt werden soll: Das ist der Hauptpr�fungsgegenstand
	 * @throws MissingParameterException Wenn die Anzahl der mitgegebenen Parameter nicht ausreicht, wird diese Exception geworfen
	 */
	static public void pruefeMissingEingabeparameter(boolean isGerade, double... stellen) throws MissingParameterException {
		
		if(isGerade) {
			if(stellen[0] == 0 && stellen[1] == 0)
				throw new MissingParameterException(-1);		//Wenn eine Gerade gefahren werden soll und beide Parameter (X, Y) fehlen, wird Exception mit Parameter -1 geworfen --> teilt aufrufender Methode mit, dass kein Kreis ohne Parameter gezeichnet werden kann
		}
		
		
			for(int i = 0; i < stellen.length; i++) {
				if(stellen[i] == 0) {
					throw new MissingParameterException(i);		//Wenn einer von den ben�tigten Parametern fehlt, wird direkt eine Exception unter Angabe der fehlenden Stelle geworfen. Kann von den Catch-Bl�cken der aufrufenden Methoden trotzdem zu einem validen Fahrbefehl umgeformt werden.
				}
			
		}
		
	}
	
	
	static public void getZukuenftigePos(double xKoor, double yKoor) {
		
	}
	
}
