
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UploadFile {

	private static Document doc1;
	private static ArrayList<Service> elements = new ArrayList();
	private static ArrayList<String> variables = new ArrayList();
	private static HashMap<String, String> variablesMap = new HashMap<>();

	public static Document getDoc() {
		return doc1;
	}

	public static ArrayList<Service> getElements() {
		return elements;
	}

	public static HashMap getVariablesMap() {
		return variablesMap;
	}

	public static void uploadL0(File file) {
		try {

			File fXmlFile = file;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("service");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					System.out.println("Name of Service : "
							+ eElement.getElementsByTagName("service_name").item(0).getTextContent());

					variablesMap.put("nameOfService",
							eElement.getElementsByTagName("service_name").item(0).getTextContent());

					System.out.println("Input Service : "
							+ eElement.getElementsByTagName("input_service").item(0).getTextContent());

					variablesMap.put("inputService",
							eElement.getElementsByTagName("input_service").item(0).getTextContent());

					System.out.println("Output Service : "
							+ eElement.getElementsByTagName("output_service").item(0).getTextContent());

					variablesMap.put("outputService",
							eElement.getElementsByTagName("output_service").item(0).getTextContent());

					System.out.println("Name of Variable : "
							+ eElement.getElementsByTagName("nameofvariable").item(0).getTextContent());

					variablesMap.put("nameOfVariable",
							eElement.getElementsByTagName("nameofvariable").item(0).getTextContent());

					System.out.println("Input Variable : "
							+ eElement.getElementsByTagName("input_variable").item(0).getTextContent());

					variablesMap.put("inputVariable",
							eElement.getElementsByTagName("input_variable").item(0).getTextContent());

					System.out.println("Output Variable : "
							+ eElement.getElementsByTagName("output_variable").item(0).getTextContent());

					variablesMap.put("outputVariable",
							eElement.getElementsByTagName("output_variable").item(0).getTextContent());

				}

			}

			doc1 = doc;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
