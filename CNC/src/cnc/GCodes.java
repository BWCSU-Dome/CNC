package cnc;

public class GCodes extends Codes {

	
	//Repräsentation von Code G00.
	static public void fahrenEilgang(int[] param) {
		
		try {
			pruefeMissingEingabeparameter(0, 1);
		} catch (MissingParameterException e) {
			e.printStackTrace();
			
		}
		
		
		}
		
	
	//Repräsentation von Code G01.
	static public void fahrenGerade(int[] param) {
		
	}
	
	//Repräsentation von Code G02.
	static public void fahrenKreisImUhrzeigersinn(int[] param) {
		
	}
	
	//Repräsentation von Code G03.
	static public void fahrenKreisGegenUhrzeigersinn(int[] param) {
		
	}
	
	//Repräsentation von Code G28.
	static public void fahrenZuHome() {
		
	}
	
	//Prüft, ob Fahrbewegungen innerhalb der vorgesehenen Arbeitsfläche stattfinden.
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
