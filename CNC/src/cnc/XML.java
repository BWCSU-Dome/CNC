package cnc;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XML {

	// OUTPUT
	public static void save(ArrayList<String> listeBefehle) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("Befehl");
			int aktuellerCode = 0;
			for (String befehl : listeBefehle) {
				Element temp = doc.createElement("N" + (aktuellerCode++));
				temp.appendChild(doc.createTextNode(befehl));
				rootElement.appendChild(temp);
			}
			doc.appendChild(rootElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("files/ressources/CNC-LogDatei.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readCodes(File file) throws Exception {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			int index = 0;

			NodeList befehleNL = doc.getElementsByTagName("Befehl");
			Element befehl = (Element) befehleNL.item(index);
			
			String befehlEingabe = "";
			while (befehl.getElementsByTagName("N" + index).item(0) != null) {
				befehlEingabe = befehlEingabe + befehl.getElementsByTagName("N" + index).item(0).getTextContent()+"\n";
				index++;
			}
			return befehlEingabe;
	}
	
	public static void readSettings() throws Exception {
		
		double new_homePosX, new_homePosY, geschwindschnell, geschwindlangsam, geschwindfahrt; 
		String farbeBohrer, farbeHomePos, farbeArbeitsflaeche;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File("files/ressources/CnCsettings.xml"));
		int index = 0;

		NodeList settingsNodelist = doc.getElementsByTagName("CnC-Fraese");
		Element settings = (Element) settingsNodelist.item(index);
		
		new_homePosX = Double.parseDouble(settings.getElementsByTagName("HomePosX").item(0).getTextContent());
		new_homePosY = Double.parseDouble(settings.getElementsByTagName("HomePosY").item(0).getTextContent());
		geschwindschnell = Double.parseDouble(settings.getElementsByTagName("geschwind_schnell").item(0).getTextContent())*1000/60;
		geschwindlangsam = Double.parseDouble(settings.getElementsByTagName("geschwind_langsam").item(0).getTextContent())*1000/60;
		geschwindfahrt = Double.parseDouble(settings.getElementsByTagName("geschwind_fahrt").item(0).getTextContent())*1000/60;
		farbeBohrer = settings.getElementsByTagName("FarbeBohrer").item(0).getTextContent();
		farbeHomePos = settings.getElementsByTagName("FarbeHomePos").item(0).getTextContent();
		farbeArbeitsflaeche = settings.getElementsByTagName("FarbeArbeitsflaeche").item(0).getTextContent();
	
		Main.assignSettings(new_homePosX, new_homePosY, geschwindschnell, geschwindlangsam, 
							geschwindfahrt, farbeBohrer, farbeHomePos, farbeArbeitsflaeche);
	}
	
	
}
