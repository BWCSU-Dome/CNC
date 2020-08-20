package cnc;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
			StreamResult result = new StreamResult(new File("files/ressources/XMLtestCNC.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readCodes() {

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("files/ressources/XMLtestCNC.xml"));
			int index = 0;

			NodeList befehleNL = doc.getElementsByTagName("Befehl");
			Element befehl = (Element) befehleNL.item(index);
			
			String befehlEingabe = "";
			while (befehl.getElementsByTagName("N" + index).item(0) != null) {
				befehlEingabe = befehlEingabe + befehl.getElementsByTagName("N" + index).item(0).getTextContent()+"\n";
				index++;
			}
			System.out.println(befehlEingabe);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
