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
		
		public static void main ( String [] args )
		{
			
			MCodes M = new MCodes();
			
			
			
			String text = "G01 X455 Y50";

			//Syntax Prüfung mit Regular Expressions
			
			liste = text.split(" ");

			for(int a = 0; a < liste.length; a++) {
				System.out.println(a+":" + liste[a]);
			}
			
			for(int a = 0; a < liste.length; a++) {
				
				switch(liste[a].charAt(0)) {
				case 'M':
					System.out.println("Hallo ich bin das H!");
					break;
//				case 'T':
//					System.out.println("Hallo ich bin das T!");
					//break;
				case 'G':
					doGCodes(liste[a]);
					break;
				default:
					//System.out.println("Ich bin unwichtig");
				}
			}

		
		}
		private static void doGCodes(String code) {

			switch(code) {
			case "G00":
				//Prüfencode OutofFeld -> x,y ziehen?
				
				
				//System.out.println("Ich bin GCode 01.");
				//g01;
				break;
			case "G02":
				System.out.println("GCode 02");
				break;
				default:
					System.out.println("Fehlerhafter GCode");
				//throws XXX
			}
		}
	}
//	