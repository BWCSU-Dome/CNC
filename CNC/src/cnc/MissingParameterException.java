package cnc;

public class MissingParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingParameterException() {
		super("F�r die erfolgreiche Ausf�hrung des Codes fehlen Parameter");
		GUI.setTXTOutputConsole("*Fehlende Parameter f�r Code*");
	}
	
}
