package cnc;
import java.util.regex.Pattern;
import java.lang.String;

public class RegularExpression {
	

//	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String text = "Hier ein kleiner Text der ein biscchen einödig ist.";
//		String patternString = "ein";
//		Pattern pattern = Pattern.compile(patternString);
//		Matcher matcher = pattern.matcher(text);
//		
//		while(matcher.find()) {
//			
//			System.out.println( matcher.start() + " - " + matcher.end()); 
//		
		
		
		public static String[] entryListe;					//Array variabler Länge für Eingangsdaten
		public static String[] liste = new String[5];		//Array fester Länge, in dem Befehl und Argumente nach festem Raster gespeichert werden sollen (0: Befehl, 1: X-Parameter, 2: Y, 3: I, 4: J)
		public static int[] paramList;						//Array mit Datentyp int, das benutzt wird, um die Parameter in Brauchbarer Form an die Bewegungsmethoden weiterzureichen

		static MCodes M = new MCodes();
		
		//in MCodes Methoden static machen!
		
		
		public static void main ( String [] args )
		
		{
			//Unser Einganstext, der den Code der auzuführenden Funktion (ggf mit Argumenten) enthält
			String text = "G00 Y10";
			
			//Syntax Prüfung mit Regular Expression
			//Krit1: richtiger G oder M Code
			//Krit2: valide Argumente vorhanden: Kein Unsinn, nur erlaubte Buchstaben (als Parameter)
			//Wenn nicht, wird die Methode hier abgebrochen
			if(!Pattern.matches("((M00|m00|M02|m02|M03|m03|M04|m04|M05|m05|M08|m08|M13|m13|M14|m14)|(G00|g00|G01|g01|G02|g02|G03|g03|G28|g28))\\s*[XxYyJjIi0-9 ]*$",text)) {
				System.out.println("Befehl nicht vorhanden, oder Syntax falsch.");
				return;
			}
			
			entryListe = text.split(" ");
											//Vllt 6 wegen zusätzlicher Befehlsnummerierung
			if(entryListe.length > 5) {		//Check auf maximal mögliche Anzahl der Argumente. Wenn zu viele Argumente mitgegeben wurden, wird die Methode hier abgebrochen
				System.out.println("Es wurden zu viele Argumente eingegeben.");
				return;
			}
			
			//Kopiert die Eingangsliste auf ein vollwertiges Array mit der Größe 5. Nicht angegebene Argumente werden im Array mit 00 gespeichert. 
			System.arraycopy(entryListe, 0, liste, 0, entryListe.length);
			
			for(int i = 0; i < liste.length; i++) {
				if(liste[i] == null) {
					liste[i] = "00";				//Wenn "Schublade" leer, auffüllen mit 00
				}
			}
			
			
			
			// Falls Parameter zum Code mitgegeben werden, wird vorsorglich ein Int-Array, das zur reibungslosen Weiterverarbeitung diesen Datentyp trägt.
			if(liste.length > 1) {
				paramList = new int[liste.length-1];
				String zwischenspeicher = "";
			
				for(int i = 1; i < liste.length; i++) {
					
			//Sortierung der Funktionsargumente in folgende Reihenfolge: X, Y, I, J (falls vorhanden)
			switch (liste[i].charAt(0)) {
			case 'X':
				zwischenspeicher = liste[1];
				liste[1] = liste[i];
				liste[i] = zwischenspeicher;
				break;
			case 'Y':
				zwischenspeicher = liste[2];
				liste[2] = liste[i];
				liste[i] = zwischenspeicher;
				break;
			case 'I':
				zwischenspeicher = liste[3];
				liste[3] = liste[i];
				liste[i] = zwischenspeicher;
				break;
			case 'J':
				zwischenspeicher = liste[4];
				liste[4] = liste[i];				
				liste[i] = zwischenspeicher;
				break;
			}
				}
				//Abspeicherung in int-Array, sodass die Werte besser in G-Methoden verwendet werden können.
				
				for(int i=0; i < paramList.length; i++) {
					paramList[i] = Integer.parseInt(liste[i+1].substring(1));		//Die Buchstaben der Argumente werden hier weggeschnitten, da dank der festen Reihenfolge, die das Array nun hat, anhand der Position klar ist, um welches Argument es sich handelt.
					System.out.println(paramList[i]);
			}
				
			}
			
			
			for(int a = 0; a < liste.length; a++) {
				System.out.println(a+":" + liste[a]);
			}
			
				//Unterscheidung, ob M- oder G-Code vorliegt (entschieden an mitgegebenem ersten Buchstaben)
				switch(liste[0].charAt(0)) {
				case 'M':
					doMCodes(liste);
					break;
				case 'G':
					doGCodes(liste);
					break;
				default:
					System.out.println("Das lief nicht gut. Fehler im Switch-Case-Block in der Klasse RegEx");
				}
			

		
		}
		

		
		private static void doGCodes(String[] code) {
				
			//Aufruf des gewünschten Codes nach eingegebener, gewünschter Funktion
			switch(code[0]) {
			
			case "G00":
			GCodes.fahrenEilgang(paramList);
				break;
				
			case "G01":
			GCodes.fahrenGerade(paramList);
				break;
				
			case "G02":
			GCodes.fahrenKreisImUhrzeigersinn(paramList);
				break;
			
			case "G03":
			GCodes.fahrenKreisGegenUhrzeigersinn(paramList);
				break;
				
			case "G28":
			GCodes.fahrenZuHome();
				break;
				
			default:
			System.out.println("GCode-Fehler"); //Fehler sollte hier gar nicht mehr auftreten können. Bitte Meldung, falls das der Fall sein sollte.
			
		
		}

	}
			
		
		private static void doMCodes(String[]code) {
			
			//Aufruf des gewünschten Codes nach eingegebener, gewünschter Funktion
			switch(code[0]) {
			
			case "M00":
			MCodes.haltMaschine();
				break;
			
			case "M02":
			MCodes.beendeProgramm();
				break;
			
			case "M03":
			MCodes.schalteSpindelAn(true);
				break;
			
			case "M04":
			MCodes.schalteSpindelAn(false);
				break;
			
			case "M05":
			MCodes.schalteSpindelAus();
				break;
		
			case "M08":
			MCodes.schalteKuelmittel(true);
				break;
			
			case "M09":
			MCodes.schalteKuelmittel(false);
				break;
			
			//
			case "M13":
			MCodes.schalteSpindelAn(true);
			MCodes.schalteKuelmittel(true);
				break;
				
			case "M14":
			MCodes.schalteSpindelAn(false);
			MCodes.schalteKuelmittel(true);
				break;
				
			default:
				System.out.println("MCode-Fehler");	//Fehler sollte hier gar nicht mehr auftreten können. Bitte Meldung, falls das der Fall sein sollte.
			}
			
		}
		
		
}
