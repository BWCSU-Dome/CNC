package cnc;
import java.util.regex.Pattern;
import java.io.IOException;
import java.lang.String;

/**Klasse, die lediglich dazu da ist, eingegebene Codes auf syntaktische Richtigkeit zu �berpr�fen. Dies geschieht durch Regular Expressions.
 * 
 * @author Jonas Heckerodt
 *
 */

public class RegularExpression {	
	
	public static void checkCodeFormatierung(String[] codes) {
			//Unser Einganstext, der den Code der auzuf�hrenden Funktion (ggf mit Argumenten) enth�lt
			
		
		
			//Syntax Pr�fung mit Regular Expression
			//Krit1: richtiger G oder M Code
			//Krit2: valide Argumente vorhanden: Kein Unsinn, nur erlaubte Buchstaben (als Parameter)
			//Wenn nicht, wird die Methode hier abgebrochen
		for(int i = 0; i < codes.length; i++) {
			if(!Codes.getLastCodeSuccessful())		//wenn letzter Code in irgendeiner Weise fehlerhaft (Syntax falsch, nicht fahrbar, fehlende Parameter)
				return;
			
		String code = codes[i];
		
			try {
				
				//�berpr�fung, ob ein valider Code eingegeben wurde, sowie g�ltige Parameter (mit I, J, X, Y, sowie Zahl)
			if(!Pattern.matches("((M00|m00|M02|m02|M03|m03|M04|m04|M05|m05|M08|m08|M09|m09|M13|m13|M14|m14)|(G00|g00|G01|g01|G02|g02|G03|g03|G28|g28))\\s*[XxYyJjIi0-9  -]*$",code)) {
				throw new IOException();
				
			}
				if(code.split(" ").length > 5) {		//Check auf maximal m�gliche Anzahl der Argumente. Wenn zu viele Argumente mitgegeben wurden, wird die Methode hier abgebrochen
					throw new IOException();
				}
			
			} catch(IOException e) {		//Wenn Exception geworfen, wird ein Rollback des gesamten Enqueueing-Prozesses durchgef�hrt, sowie eine Textausgabe in die GUI vorgenommen
				Codes.abbrechenEnqueueing();
				GUI.setTXTOutputConsole("*Syntax falsch: " + codes[i]+"*");
				return;						//Zur�ckspringen in Codes.neubildenQueue
			}
								
			//Aufruf zum Hinzuf�gen zur Befehlswarteschlange
			Codes.enqueueBefehl(code);
		}
		//Wenn alle Codes fehlerfrei sind, dann R�cksprung in die Codes.neubildenQueue 
		return;
		
		}
		
	}