package cnc;

public class falscheWerteXMLException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public falscheWerteXMLException(String fehler)
	   {
		   GUI.setTXTOutputConsole("*Falsche Werte in der XML-Setting Datei*\nFehler: " + fehler);
	   }

	

}
