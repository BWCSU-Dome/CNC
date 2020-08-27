package cnc;

public class MissingParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingParameterException() {
		super("Für die erfolgreiche Ausführung des Codes fehlen Parameter");
		GUI.setTXTOutputConsole("*Fehlende Parameter für Code*");
	}
	
}
