package cnc;

public class MissingParameterException extends Exception {

	public MissingParameterException() {
		super("Die eingegebenen Parameter sind unvollst�ndig. Bitte Eingabe wiederholen.");
	}
}
