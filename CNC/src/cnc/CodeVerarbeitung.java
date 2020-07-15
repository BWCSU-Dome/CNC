package cnc;


public class CodeVerarbeitung extends Codes implements Runnable {
	
	private static String[] befehlEingangDo;
	private static String[] befehl = new String[5];
	private static double[] parameter = new double[4];
	
	public void run() {
		
		while(true) {
			if(queueIsEmpty()) {
				System.out.println("Die Warteschlange ist leer.");
				return;
				}
			
			befehlEingangDo = queue.get(0).split(" ");
			
			System.arraycopy(befehlEingangDo, 0, befehl, 0, befehlEingangDo.length);
			
			// Es wird zur reibungslosen Weiterverarbeitung der Parameter ein Double-Array erzeugt
			parameter = new double[befehl.length-1];
			
			for(int i=0; i < parameter.length; i++) {
				parameter[i] = Double.parseDouble(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
				}
				
				//Unterscheidung, ob M- oder G-Code vorliegt (entschieden an mitgegebenem ersten Buchstaben)
				switch(befehl[0].charAt(0)) {
				
				case 'M':
					doMCodes(befehl);
					break;
				case 'G':
					doGCodes(false, befehl);		
					break;
				default:
					System.out.println("Das lief nicht gut. Fehler im Switch-Case-Block in der Klasse RegEx");
				}
				
			ausgefuehrteCodes++;
			queue.remove(0);
		}
	}

}
