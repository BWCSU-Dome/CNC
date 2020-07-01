package cnc;

public class GCodes extends Codes {

	
	//Repräsentation von Code G00.
	static public void fahrenEilgang(int[] param) {
		
		int x = param[0];
		int y = param[1];
		
		try {
			
			pruefeMissingEingabeparameter(true, param[0], param[1]);
			
		} catch (MissingParameterException e) {
			
		switch(e.getStelle()) {
		
		case 0:
			System.out.println("Da ein Parameter weggelassen wurde, wird auf einer Achse horizontal/vertikal gefahren.");
		
			
			
		case -1:
			System.out.println("Es kann keine Gerade ohne Parameter gefahren werden.");
			return;
		}
			
	}
		System.out.println(x + " " + y);
		
}
		
	
	//Repräsentation von Code G01.
	static public void fahrenGerade(int[] param) {
		int x = param[0];
		int y = param[1];
		
		try {
			
			pruefeMissingEingabeparameter(true, param[0], param[1]);
			
		}
			catch (MissingParameterException e) {
				
				switch(e.getStelle()) {
				
				default:
					break;
					
				case -1:
					System.out.println("Es kann keine Gerade ohne Parameter gezeichnet werden.");
					return;
				}
				
			}
		
		System.out.println(x + " " + y);
		
		
		}
	
	//Repräsentation von Code G02.
	static public void fahrenKreisImUhrzeigersinn(int[] param) {
		int x = param[0];
		int y = param[1];
		int i = param[2];
		int j = param[3];
		
//		try {
//			pruefeFahrbewegung();
//		
//		} catch(OutOfAreaException e) {
//		e.printStackTrace();
//		return;
//		}
		
		
		
		
		
	}
	
	//Repräsentation von Code G03.
	static public void fahrenKreisGegenUhrzeigersinn(int[] param) {
		int x = param[0];
		int y = param[1];
		int i = param[2];
		int j = param[3];
		
//		try {
//			pruefeFahrbewegung();
//		
//		} catch(OutOfAreaException e) {
//		e.printStackTrace();
//		return;
//		}
		
		
	}
	
	//Repräsentation von Code G28.
	static public void fahrenZuHome() {
		int[] stub = new int[2];
		stub[0] = 0;
		stub[1] = 0;
		fahrenEilgang(stub);		//Rufe die fahrenEilgang-Methode auf mit den Koordinaten des Homepunkts.
	}
	
	//Prüft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfläche stattfinden.
	static public void pruefeFahrbewegung(int[] positionX, int[] positionY) throws OutOfAreaException {
		int boundX = 1500;
		int boundY = 1040; 
		
		int[] stubX = new int[10];
		int[] stubY = new int[10];
		
		for(int i = 0; i < stubX.length; i++) {
				if(stubX[i] > boundX || stubY[i] > boundY) {
					throw new OutOfAreaException();
			}
		}
		
		
		
		
	}
	
	
	static public void pruefeMissingEingabeparameter(boolean isGerade, int... stellen) throws MissingParameterException {
		
		if(isGerade) {
			if(stellen[0] == 0 && stellen[1] == 0)
				throw new MissingParameterException(-1);
		}
		
		if(isGerade) {
			for(int i = 0; i < stellen.length; i++) {
				if(stellen[i] == 0) {
					throw new MissingParameterException(i);
				}
			}
		}
		
	}
	
}
