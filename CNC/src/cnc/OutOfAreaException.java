package cnc;

public class OutOfAreaException extends Exception {

	   public OutOfAreaException()
	   {
		   super("Die Fräse befände sich während der Ausführung des Befehls außerhalb des zulässigen Bereichs");
	   }


}
