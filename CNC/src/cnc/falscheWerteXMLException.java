package cnc;
/*
 * @author Dominik Riepl
 */
public class falscheWerteXMLException extends Exception {

	private static final long serialVersionUID = 1L;

	/*
	 * Diese Exception soll geworfen werden, wenn ungültige Werte in der
	 * SettingsDatei eingetragen sind
	 */

	public falscheWerteXMLException(String fehler) {
		GUI.setTXTOutputConsole("*Falsche Werte in der XML-Setting Datei*\nFehler: " + fehler);
	}

}
