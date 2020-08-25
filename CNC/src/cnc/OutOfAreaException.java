package cnc;

public class OutOfAreaException extends Exception {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfAreaException()
	   {
		   super("Die Fräse befände sich während der Ausführung des Befehls außerhalb des zulässigen Bereichs");
		   Codes.addStringToOutput("*Out Of Area für Code*");
	   }


}
