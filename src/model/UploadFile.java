package model;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Used to read through input files, extracting all relevant information into {@link SimpleService}.
 * currently only works with xml files.
 * @author Simon Miller s3353879
 * @author Roman Lobanov s3399752
 * @author Leslie Pang s3390257
 * @author Jordan Mason s3423620
 *
 */
public class UploadFile {

	private static Document doc1;
	
	private static ArrayList<String> variables = new ArrayList();
	private static HashMap<String, SimpleService> variablesMap = new HashMap<>();
	private static ArrayList<HashMap> hashMaps = new ArrayList<>();
	private static ArrayList<Service> elements = new ArrayList();

	/**
	 * The default getter for doc1
	 * @return the parsed xml document
	 */
	public static Document getDoc() {
		return doc1;
	}
	
	/**
	 * @return the elements
	 */
	public static ArrayList<Service> getElements() {
		return elements;
	}



	/**
	 * @param elements the elements to set
	 */
	public static void setElements(ArrayList<Service> elements) {
		UploadFile.elements = elements;
	}

	

	/**
	 * @return the variables
	 */
	public static ArrayList<String> getVariables() {
		return variables;
	}



	/**
	 * @param variables the variables to set
	 */
	public static void setVariables(ArrayList<String> variables) {
		UploadFile.variables = variables;
	}



	/**
	 * @return the variablesMap
	 */
	public static HashMap<String, SimpleService> getVariablesMap() {
		return variablesMap;
	}



	/**
	 * @param variablesMap the variablesMap to set
	 */
	public static void setVariablesMap(HashMap<String, SimpleService> variablesMap) {
		UploadFile.variablesMap = variablesMap;
	}



	/**
	 * @return the hashMaps
	 */
	public static ArrayList<HashMap> getHashMaps() {
		return hashMaps;
	}



	/**
	 * @param hashMaps the hashMaps to set
	 */
	public static void setHashMaps(ArrayList<HashMap> hashMaps) {
		UploadFile.hashMaps = hashMaps;
	}



	/**
	 * @param doc1 the document to set
	 */
	public static void setDoc1(Document doc1) {
		UploadFile.doc1 = doc1;
	}
	
	public static List<Service> readFromXML(File file) {
		List<Service> listOfService = null;
		try {
			UploadFile.uploadL0(file);
			Document doc = UploadFile.getDoc();
			listOfService = UploadFile.getElements();

		}

		catch (Exception e) {
			System.out.println("error occured during conversion of xml to service list");
			e.printStackTrace();
		}

		return listOfService;
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
			NodeList nList = doc.getElementsByTagName("service");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					//experimental build starts here---------------------------------------------------------------------------------------
					SimpleService tmpSrvce = new SimpleService();

					tmpSrvce.setName(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					//dirty workaround, needs to be changed once the xml files are changed
					ArrayList<IOVariable> retypedData = new ArrayList<IOVariable>();
					for(String io : new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("input_service").item(0).getTextContent().split(","))))
					{
						IOVariable newio = new IOVariable();
						newio.name = io;
						retypedData.add(newio);
					}

					
					tmpSrvce.setInputService(retypedData); //okay lots of explaining here,
					
					retypedData = new ArrayList<IOVariable>();
					for(String io : new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("output_service").item(0).getTextContent().split(","))))
					{
						IOVariable newio = new IOVariable();
						newio.name = io;
						retypedData.add(newio);
					}
																																										//lets go inside to out
					tmpSrvce.setOutputService(retypedData);//first the eElement gets the data from
					
					retypedData = new ArrayList<IOVariable>();
					for(String io : new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("nameofvariable").item(0).getTextContent().split(","))))
					{
						IOVariable newio = new IOVariable();
						newio.name = io;
						retypedData.add(newio);
					}
																																										//the area in the xml denoted by the tag name
					tmpSrvce.setNameOfVariable(retypedData);//then the text is extracted
					
					retypedData = new ArrayList<IOVariable>();
					for(String io : new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("input_variable").item(0).getTextContent().split(","))))
					{
						IOVariable newio = new IOVariable();
						newio.name = io;
						retypedData.add(newio);
					}
																																										//and split by commas into an array of strings
					tmpSrvce.setInputVariable(retypedData);//the array is then turned into an arrayList
					
					retypedData = new ArrayList<IOVariable>();
					for(String io : new ArrayList<String>(Arrays.asList(eElement.getElementsByTagName("output_variable").item(0).getTextContent().split(","))))
					{
						IOVariable newio = new IOVariable();
						newio.name = io;
						retypedData.add(newio);
					}
																																										//and the list is put into the service variable
					tmpSrvce.setOutputVariable(retypedData);
					
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
