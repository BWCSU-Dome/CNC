package cnc;

public class OutOfAreaException extends Exception {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfAreaException()
	   {
		   super("Die Fr�se bef�nde sich w�hrend der Ausf�hrung des Befehls au�erhalb des zul�ssigen Bereichs");
		   GUI.setTXTOutputConsole("*Out Of Area f�r Code*");
	   }


}
