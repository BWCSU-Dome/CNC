package cnc;

public class MissingParameterException extends Exception {
	int stelle;

	public MissingParameterException(int stelle) {
		super("Die eingegebenen Parameter sind unvollständig.");
		this.stelle = stelle;
	}

	public int getStelle() {
		return stelle;
	}
	
}
