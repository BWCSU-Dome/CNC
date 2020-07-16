package cnc;


public class CodeVerarbeitung extends Codes implements Runnable {
	
	private static String[] befehlEingangDo;
	private static String[] befehl = new String[5];
	private static double[] parameter = new double[4];
	private static boolean weiter;
	
	public void run() {
		
		while(true) {
			if(queueIsEmpty()) {
				System.out.println("Die Warteschlange ist leer.");
				Thread.currentThread().interrupt();
				return;
				}
			
			befehlEingangDo = queue.get(0).split(" ");
			
			System.arraycopy(befehlEingangDo, 0, befehl, 0, befehlEingangDo.length);
			
			// Es wird zur reibungslosen Weiterverarbeitung der Parameter ein Double-Array erzeugt
			parameter = new double[befehl.length-1];
			
			for(int i=0; i < parameter.length; i++) {
				weiter = false;
				parameter[i] = Double.parseDouble(befehl[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
				}
				
				//Unterscheidung, ob M- oder G-Code vorliegt (entschieden an mitgegebenem ersten Buchstaben)
				switch(befehl[0].charAt(0)) {
				
				case 'M':
					doMCodes(befehl);
					break;
				case 'G':
					doGCodes(false, befehl, parameter);		
					break;
				default:
					System.out.println("Das lief nicht gut. Fehler im Switch-Case-Block in der Klasse RegEx");
				}
			
				ausgefuehrteCodes++;
				
			while(!weiter) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(ausgefuehrteCodes == parameter.length) {
					return;
				}
			}
			
			
			Codes.exportCode(queue.get(0));
			queue.remove(0);
		}
	}
	
	
	public static void setBoolWeiter(Boolean wert) {
		weiter = wert;
	}
}
