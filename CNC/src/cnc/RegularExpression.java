package cnc;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
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
		
		
		public static String[] liste;
<<<<<<< HEAD
		static int[] paramList;
		MCodes M = new MCodes();
=======
		static MCodes M = new MCodes();
		//in MCodes Methoden static machen!
>>>>>>> branch 'master' of https://github.com/BWCSU-Dome/CNC.git
		
		
		public static void main ( String [] args )
		{
			
			String text = "G00";
			//Syntax Prüfung mit Regular Expressions
			//richtiger G oder M Code
			//Kein Unsinn, nur erlaubte Buchstaben (als Parameter)
			System.out.println(Pattern.matches("((M00|m00|M02|m02|M03|m03|M04|m04|M05|m05|M08]|m08|M13|m13|M14|m14)|(G00|g00|G01|g01|G02|g02|G03|g03|G28|g28))\\s*[XxYyJjIi0-9 ]*$",text));
			
			
			
			liste = text.split(" ");
			
			
			
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
			
				for(int i=0; i < paramList.length; i++) {
					paramList[i] = Integer.parseInt(liste[i+1].substring(1));
					System.out.println(paramList[i]);
			}
				
			}
			
			
			for(int a = 0; a < liste.length; a++) {
				System.out.println(a+":" + liste[a]);
			}
			
				
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
		

<<<<<<< HEAD

		private static void doGCodes(String[] code) {
=======
			switch(code) {
			case "G00":
				//Prüfencode OutofFeld -> x,y ziehen?
				//M.fahrenEilgang(455, 50);
>>>>>>> branch 'master' of https://github.com/BWCSU-Dome/CNC.git
				
			switch(code[0]) {
			
			case "G00":
			GCodes.fahrenEilgang(paramList);
				break;
				
			case "G01":
			GCodes.fahrenEilgang(paramList);
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
					System.out.println("GCode-Fehler");
				//throws XXX
		
		}
<<<<<<< HEAD
	}
		
		
		private static void doMCodes(String[]code) {
			
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
				System.out.println("MCode-Fehler");
			}
			
		}
		
		public static int getParamList(int position) {
			return paramList[position];
		}
		
		public static int getParamListLength() {
			return paramList.length;
		}
		

		public static void setParamList(int[] paramList) {
			RegularExpression.paramList = paramList;
		}
		
=======
		}
>>>>>>> branch 'master' of https://github.com/BWCSU-Dome/CNC.git
}