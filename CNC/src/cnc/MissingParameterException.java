package cnc;

public class MissingParameterException extends Exception {

	public MissingParameterException() {
		super("Die eingegebenen Parameter sind unvollständig. Bitte Eingabe wiederholen.");
	}
}
