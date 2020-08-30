package cnc;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.xml.sax.SAXException;


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
			StreamResult result = new StreamResult(new File(getXMLPath() + " CNC-LogDatei.xml"));
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
	
	public static void readSettings() throws falscheWerteXMLException, ParserConfigurationException, SAXException, IOException  {
		
		double new_homePosX, new_homePosY, geschwindschnell, geschwindlangsam, geschwindfahrt; 
		String farbeBohrer, farbeHomePos, farbeArbeitsflaeche;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(getXMLPath() + " CnCsettings.xml"));
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
		if(!(0<=new_homePosX && new_homePosX<= 1400)) {
			System.out.println(new_homePosX);
			throw new falscheWerteXMLException("HomePosX");
		}
		if(!(0<=new_homePosY && new_homePosY<= 1050)) {
			throw new falscheWerteXMLException("HomePosY");
		}
		if(!(0<geschwindschnell && geschwindschnell<= (5*1000/60))) {
			throw new falscheWerteXMLException("Geschwindigkeit_schnell");
		}
		if(!(0<geschwindlangsam && geschwindlangsam<= (4*1000/60))) {
			throw new falscheWerteXMLException("Geschwindigkeit_langsam");
		}
		if(!(0<geschwindfahrt && geschwindfahrt<= (8*1000/60))) {
			throw new falscheWerteXMLException("Geschwindigkeit_fahrt");
		}
		checkColor(farbeArbeitsflaeche);
		checkColor(farbeHomePos);
		checkColor(farbeBohrer);
		
		Main.assignSettings(new_homePosX, new_homePosY, geschwindschnell, geschwindlangsam, 
							geschwindfahrt, farbeBohrer, farbeHomePos, farbeArbeitsflaeche);
	}
	
	private static void checkColor(String color) throws falscheWerteXMLException {
		switch (color) {
		case "grey": 
			break;
		case "black": 
			break;
		case "white": 
			break;
		case "red": 
			break;
		case "green": 
			break;
		case "yellow": 
			break;
		case "blue": 
			break;
		default:
			throw new falscheWerteXMLException("ungültige Farbe");
		}
	}
	
	private static String getXMLPath(){
		String decodedPath=null;
		String path = XML.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return decodedPath;
		
	}
	
	}
	
	

