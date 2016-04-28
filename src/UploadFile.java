
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
	private static HashMap<String, SimpleService> variablesMap = new HashMap<>();
	private static ArrayList<HashMap> hashMaps = new ArrayList<>();

	public static Document getDoc() {
		return doc1;
	}

	public static ArrayList<Service> getElements() {
		return elements;
	}

	public static HashMap getVariablesMap() {
		return variablesMap;
	}
	
	public static ArrayList<HashMap> getHashMaps(){
		return hashMaps;
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

				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;//TODO: refactor code to hold services and variables in arrays as there are multiple of them, do after friday meeting as this will
					// affect many parts of the codebase and cause them not to work until changes are complete
					//experimental build starts here---------------------------------------------------------------------------------------
					SimpleService tmpSrvce = new SimpleService();

					tmpSrvce.setName(eElement.getElementsByTagName("service_name").item(0).getTextContent());

					tmpSrvce.setInputService(new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("input_service").item(0).getTextContent().split(",")))); //okay lots of explaining here,
																																										//lets go inside to out
					tmpSrvce.setOutputService(new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("output_service").item(0).getTextContent().split(","))));//first the eElement gets the data from
																																										//the area in the xml denoted by the tag name
					tmpSrvce.setNameOfVariable(new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("nameofvariable").item(0).getTextContent().split(","))));//then the text is extracted
																																										//and split by commas into an array of strings
					tmpSrvce.setInputVariable(new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("input_variable").item(0).getTextContent().split(","))));//the array is then turned into an arrayList
																																										//and the list is put into the service variable
					tmpSrvce.setOutputVariable(new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("output_variable").item(0).getTextContent().split(","))));
					
					variablesMap.put(tmpSrvce.getName(), tmpSrvce);
					
					//experimental build ends here-----------------------------------------------------------------------------------

					/*
					 { 
					  System.out.println("Name of Service : "
							+ eElement.getElementsByTagName("service_name").item(0).getTextContent());

					variablesMap.put("service_name",
							eElement.getElementsByTagName("service_name").item(0).getTextContent());

					System.out.println("Input Service : "
							+ eElement.getElementsByTagName("input_service").item(0).getTextContent());

					variablesMap.put("input_service",
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
							}*/
					
					hashMaps.add(variablesMap);
					
					//System.out.println("KJJSNNNJSJJ" + UploadFile.getHashMaps().get(0).get("service_name"));
					

				}

			}

			doc1 = doc;
		} 
		catch (Exception e) 
		{
			System.out.println("an unexpected erroroccured during operation");
			e.printStackTrace();
		}

	}

}
