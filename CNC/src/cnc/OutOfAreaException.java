package cnc;

public class OutOfAreaException extends Exception {

	   public OutOfAreaException()
	   {
		   super("Die Fr�se bef�nde sich w�hrend der Ausf�hrung des Befehls au�erhalb des zul�ssigen Bereichs");
	   }


}
