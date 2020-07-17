package cnc;

public class MissingParameterException extends Exception {

	public MissingParameterException() {
		Codes.addStringToOutput("*Fehlende Parameter für Code*");
	}
	
}
