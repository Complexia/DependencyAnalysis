package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.InvalidIOVariableReferenceException;
import exceptions.ServiceException;
import exceptions.ServiceIOException;

/**
 * Used to read through input files, extracting all relevant information into
 * {@link SimpleService}. currently only works with xml files.
 * 
 * @author Simon Miller s3353879
 * @author Roman Lobanov s3399752
 * @author Leslie Pang s3390257
 * @author Jordan Mason s3423620
 *
 */
public class UploadFile {

	/**
	 * Stores the parsed xml {@link Document} object.
	 */
	private static Document doc1;

	/**
	 * Stores the string data for the services. currently unused.
	 */
	private static ArrayList<String> variables = new ArrayList();
	/**
	 * Stores all {@link SimpleService} objects using their names as a key.
	 */
	private static HashMap<String, SimpleService> variablesMap = new HashMap<>();
	/**
	 * Stores a list of variablesMaps. currently unused.
	 */
	private static ArrayList<HashMap> hashMaps = new ArrayList<>();
	/**
	 * Stores a list of Services.
	 * 
	 * @deprecated replaced with variablesMap
	 */
	private static ArrayList<Service> elements = new ArrayList();

	/**
	 * The default getter for doc1
	 * 
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
	 * @param elements
	 *            the elements to set
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
	 * @param variables
	 *            the variables to set
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
	 * @param variablesMap
	 *            the variablesMap to set
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
	 * @param hashMaps
	 *            the hashMaps to set
	 */
	public static void setHashMaps(ArrayList<HashMap> hashMaps) {
		UploadFile.hashMaps = hashMaps;
	}

	/**
	 * @param doc1
	 *            the document to set
	 */
	public static void setDoc1(Document doc1) {
		UploadFile.doc1 = doc1;
	}

	/**
	 * @param file
	 *            the file object to be parsed
	 * @return A list of Services. The value is currently unused.
	 */
	public static List<Service> readFromXML(File file) {
		List<Service> listOfService = null;
		try {
			UploadFile.uploadL0(file);
			Document doc = UploadFile.getDoc();
			listOfService = UploadFile.getElements();

		}

		catch (ServiceException e) {// TODO make the exception message more user
									// friendly, possibly a popup or something
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return listOfService;
	}

	/**
	 * @param file
	 *            The file to be parsed Works in conjuction with readFromXml to
	 *            parse the file and extract the SimpleService data
	 * @throws {@link
	 *             ServiceIOException}
	 */
	public static void uploadL0(File file) throws ServiceIOException, InvalidIOVariableReferenceException {

		try {
			File fXmlFile = file;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			// throws an exception if the specified tag isn't found, is handled
			// by readFromXML.
			if (doc.getElementsByTagName("service").getLength() == 0)
				throw new ServiceIOException("no service tag found in file: " + fXmlFile.getName());
			NodeList nList = doc.getElementsByTagName("service");

			HashMap<String, IOVariable> IOHash = new HashMap<String, IOVariable>(constructIOVariables(doc, fXmlFile));

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					// experimental build starts
					// here---------------------------------------------------------------------------------------
					SimpleService tmpSrvce = new SimpleService();

					tmpSrvce.setName(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					ArrayList<IOVariable> retypedData = new ArrayList<IOVariable>();
					if (eElement.getElementsByTagName("input_service").getLength() == 0)
						throw new ServiceIOException("no input_service tag found in file: " + fXmlFile.getName());
					for (String io : new ArrayList<String>(Arrays.asList(
							eElement.getElementsByTagName("input_service").item(0).getTextContent().split(",")))) {
						IOVariable newio = IOHash.get(io);
						retypedData.add(newio);
					}

					tmpSrvce.setInputService(retypedData);

					retypedData = new ArrayList<IOVariable>();
					if (eElement.getElementsByTagName("output_service").getLength() == 0)
						throw new ServiceIOException("no output_service tag found in file: " + fXmlFile.getName());
					for (String io : new ArrayList<String>(Arrays.asList(
							eElement.getElementsByTagName("output_service").item(0).getTextContent().split(",")))) {
						IOVariable newio = IOHash.get(io);
						retypedData.add(newio);
					}
					tmpSrvce.setOutputService(retypedData);

					retypedData = new ArrayList<IOVariable>();
					if (eElement.getElementsByTagName("nameofvariable").getLength() == 0)
						throw new ServiceIOException("no nameofvariable tag found in file: " + fXmlFile.getName());
					for (String io : new ArrayList<String>(Arrays.asList(
							eElement.getElementsByTagName("nameofvariable").item(0).getTextContent().split(",")))) {
						IOVariable newio = IOHash.get(io);
						retypedData.add(newio);
					}
					tmpSrvce.setNameOfVariable(retypedData);

					retypedData = new ArrayList<IOVariable>();
					if (eElement.getElementsByTagName("input_variable").getLength() == 0)
						throw new ServiceIOException("no input_variable tag found in file: " + fXmlFile.getName());
					for (String io : new ArrayList<String>(Arrays.asList(
							eElement.getElementsByTagName("input_variable").item(0).getTextContent().split(",")))) {
						IOVariable newio = IOHash.get(io);
						retypedData.add(newio);
					}
					tmpSrvce.setInputVariable(retypedData);

					retypedData = new ArrayList<IOVariable>();
					if (eElement.getElementsByTagName("output_variable").getLength() == 0)
						throw new ServiceIOException("no output_variable tag found in file: " + fXmlFile.getName());
					for (String io : new ArrayList<String>(Arrays.asList(
							eElement.getElementsByTagName("output_variable").item(0).getTextContent().split(",")))) {
						IOVariable newio = IOHash.get(io);
						retypedData.add(newio);
					}
					tmpSrvce.setOutputVariable(retypedData);

					variablesMap.put(tmpSrvce.getName(), tmpSrvce);

					// experimental build ends
					// here-----------------------------------------------------------------------------------

					/*
					 * { System.out.println("Name of Service : " +
					 * eElement.getElementsByTagName("service_name").item(0).
					 * getTextContent());
					 * 
					 * variablesMap.put("service_name",
					 * eElement.getElementsByTagName("service_name").item(0).
					 * getTextContent());
					 * 
					 * System.out.println("Input Service : " +
					 * eElement.getElementsByTagName("input_service").item(0).
					 * getTextContent());
					 * 
					 * variablesMap.put("input_service",
					 * eElement.getElementsByTagName("input_service").item(0).
					 * getTextContent());
					 * 
					 * System.out.println("Output Service : " +
					 * eElement.getElementsByTagName("output_service").item(0).
					 * getTextContent());
					 * 
					 * variablesMap.put("outputService",
					 * eElement.getElementsByTagName("output_service").item(0).
					 * getTextContent());
					 * 
					 * System.out.println("Name of Variable : " +
					 * eElement.getElementsByTagName("nameofvariable").item(0).
					 * getTextContent());
					 * 
					 * variablesMap.put("nameOfVariable",
					 * eElement.getElementsByTagName("nameofvariable").item(0).
					 * getTextContent());
					 * 
					 * System.out.println("Input Variable : " +
					 * eElement.getElementsByTagName("input_variable").item(0).
					 * getTextContent());
					 * 
					 * variablesMap.put("inputVariable",
					 * eElement.getElementsByTagName("input_variable").item(0).
					 * getTextContent());
					 * 
					 * System.out.println("Output Variable : " +
					 * eElement.getElementsByTagName("output_variable").item(0).
					 * getTextContent());
					 * 
					 * variablesMap.put("outputVariable",
					 * eElement.getElementsByTagName("output_variable").item(0).
					 * getTextContent()); }
					 */

					hashMaps.add(variablesMap);

					// System.out.println("KJJSNNNJSJJ" +
					// UploadFile.getHashMaps().get(0).get("service_name"));

				}

			}

			doc1 = doc;
		} catch (ParserConfigurationException PCEx) {
			System.out.println(PCEx.toString());
		}

		catch (IOException IO) {
			System.out.println(IO.toString());
		}

		catch (SAXException SAX) {
			System.out.println(SAX.toString());
		}
	}

	/**
	 * uses the information in the xml file to generate the IOVariables and
	 * their references to each other. this might be extended later on to allow
	 * parsing of non xml file input
	 * 
	 * @param doc
	 *            the input xml document
	 * @throws NullPointerException
	 * @throws ServiceIOException
	 */
	private static HashMap<String, IOVariable> constructIOVariables(Document doc, File fXmlFile)
			throws ServiceIOException, InvalidIOVariableReferenceException {
		NodeList IOVars = doc.getElementsByTagName("IOVariable");
		HashMap<String, IOVariable> IOHash = new HashMap<String, IOVariable>();
		// make the io variables first so we can form the connections later
		for (int i = 0; i < IOVars.getLength(); i++) {
			Node IOV = IOVars.item(i);
			if (IOV.getNodeType() == Node.ELEMENT_NODE) {
				Element IOElem = (Element) IOV;
				if (IOElem.getElementsByTagName("IO_variable_name").getLength() == 0)
					throw new ServiceIOException("no IO_variable_name tag found in file: " + fXmlFile.getName());
				String name = IOElem.getElementsByTagName("IO_variable_name").item(0).getTextContent();
				IOHash.put(name, new IOVariable(name));
			}
		}
		// a lot of this code is going to be a carbon copy of the above loop
		// because they are both accessing the same data,
		// above was only getting the names and making the empty objects
		// whereas below is taking the inputs and outputs strings and making the
		// appropriate connections
		for (int i = 0; i < IOVars.getLength(); i++) {
			Node IOV = IOVars.item(i);
			if (IOV.getNodeType() == Node.ELEMENT_NODE) {
				Element IOElem = (Element) IOV;
				if (IOElem.getElementsByTagName("IO_variable_name").getLength() == 0)
					throw new ServiceIOException("no IO_variable_name tag found in file: " + fXmlFile.getName());
				String name = IOElem.getElementsByTagName("IO_variable_name").item(0).getTextContent();
				name = name.trim();// strings are immutable, so you have to
									// store the new string reference into the
									// variable
				IOVariable currVar = IOHash.get(name);
				// get a list of all inputs for the variable and put the
				// IOVariable objects associated with that name into the inputs
				// list of the object itself
				if (IOElem.getElementsByTagName("inputs").getLength() == 0)
					throw new ServiceIOException("no inputs tag found in file: " + fXmlFile.getName() + "\nfor IOVariable:" + currVar.name);
				for (String in : IOElem.getElementsByTagName("inputs").item(0).getTextContent().split(",")) {
					in = in.trim();
					if (in != "") {
						IOVariable tmp = IOHash.get(in);

						if (tmp == null)
						{
							throw new InvalidIOVariableReferenceException("Input variable name not found in list of defined IOVariables: " + in);
						}
						else
						{
							currVar.inputs.add(tmp);
						}
					}
				}
				// same as above for the outputs
				if (IOElem.getElementsByTagName("outputs").getLength() == 0)
					throw new ServiceIOException("no outputs tag found in file: " + fXmlFile.getName());
				for (String out : IOElem.getElementsByTagName("outputs").item(0).getTextContent().split(",")) {
					out = out.trim();
					if (out != "") {
						IOVariable tmp = IOHash.get(out);

						if (tmp == null)
							throw new InvalidIOVariableReferenceException(
									"Output variable name not found in list of defined IOVariables: " + out);
						else
							currVar.outputs.add(tmp);
					}
				}
			}
		}
		return IOHash;

	}

}
