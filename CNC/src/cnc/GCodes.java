package cnc;

public class GCodes extends Codes {

	
	//Repr�sentation von Code G00.
	static public void fahrenEilgang(int[] param) {
		
		try {
			pruefeMissingEingabeparameter(0, 1);
		} catch (MissingParameterException e) {
			e.printStackTrace();
			
		}
		
		
		}
		
	
	//Repr�sentation von Code G01.
	static public void fahrenGerade(int[] param) {
		
	}
	
	//Repr�sentation von Code G02.
	static public void fahrenKreisImUhrzeigersinn(int[] param) {
		
	}
	
	//Repr�sentation von Code G03.
	static public void fahrenKreisGegenUhrzeigersinn(int[] param) {
		
	}
	
	//Repr�sentation von Code G28.
	static public void fahrenZuHome() {
		
	}
	
	//Pr�ft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfl�che stattfinden.
	static public void pruefeFahrbewegung(int[][] positionen) {
		
	}
	
	
	static public void pruefeMissingEingabeparameter(int... stellen) throws MissingParameterException {
		
		for(int i = 0; i < stellen.length; i++) {
			if(RegularExpression.getParamListLength() < i || RegularExpression.getParamListLength() == 0) {
				throw new MissingParameterException();
			}
		}
	}
	
}
