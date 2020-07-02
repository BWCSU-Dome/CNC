package cnc;

public class GCodes extends Codes {

	
	//Repräsentation von Code G00.
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

		case -1:
			System.out.println("Es kann keine Gerade ohne Parameter gefahren werden.");
			return;
		}
			
	}
		System.out.println(x + " " + y);
		
}
		
	
	//Repräsentation von Code G01.
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
					
				case -1:
					System.out.println("Es kann keine Gerade ohne Parameter gezeichnet werden.");
					return;
				}
				
			}
		
		Animation.line(x, y);
		
		
		}
	
	//Repräsentation von Code G02.
	static public void fahrenKreisImUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		try {
			pruefeMissingEingabeparameter(false, param[0], param[1], param[2], param[3]);
			
		} catch(MissingParameterException m) {
			System.out.println("Es fehlen Parameter. Für einen Kreisbogen werden x, y, i und j benötigt.");
			return;
		}
		
		
		
		try {
			pruefeFahrbewegung();
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return;
		}
		
		Animation.kreis(x, y, i, j);
		
	}
	
	//Repräsentation von Code G03.
	static public void fahrenKreisGegenUhrzeigersinn(double[] param) {
		double x = param[0];
		double y = param[1];
		double i = param[2];
		double j = param[3];
		
		try {
			pruefeMissingEingabeparameter(false, param[0], param[1], param[2], param[3]);
			
		} catch(MissingParameterException m) {
			System.out.println("Es fehlen Parameter. Für einen Kreisbogen werden x, y, i und j benötigt.");
			return;
		}
		
		
		
		try {
			pruefeFahrbewegung();
		} catch(OutOfAreaException e) {
		e.printStackTrace();
		return;
		}
		
		Animation.kreis(x, y, i, j);
		
	}
	
	//Repräsentation von Code G28.
	static public void fahrenZuHome() {
		
		fahrenEilgang(Main.getHomePosX(), Main.getHomePosY());		//Rufe die fahrenEilgang-Methode auf mit den Koordinaten des Homepunkts.
	
	}
	
	//Prüft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfläche stattfinden.
	static public void pruefeFahrbewegung() throws OutOfAreaException {
		double boundX = 1500;
		double boundY = 1040; 
		
		double[] stubX = new double[10];
		double[] stubY = new double[10];
		
		for(int i = 0; i < stubX.length; i++) {
				if(stubX[i] > boundX || stubY[i] > boundY) {
					throw new OutOfAreaException();
			}
		}
		
		
		
		
	}
	
	//Prüft, ob eine hinreichende Zahl an Eingabedaten vorhanden sind. Gibt auch aus, welche Koordinaten fehlen, um ggf trotzdem die Fahrbewegung durchzuführen.
	static public void pruefeMissingEingabeparameter(boolean isGerade, double... stellen) throws MissingParameterException {
		
		if(isGerade) {
			if(stellen[0] == 0 && stellen[1] == 0)
				throw new MissingParameterException(-1);
		}
		
		
			for(int i = 0; i < stellen.length; i++) {
				if(stellen[i] == 0) {
					throw new MissingParameterException(i);
				}
			
		}
		
	}
	
}
