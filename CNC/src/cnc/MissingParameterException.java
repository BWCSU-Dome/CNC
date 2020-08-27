package cnc;

public class MissingParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingParameterException() {
		GUI.setTXTOutputConsole("*Fehlende Parameter für Code*");
	}
	
}
