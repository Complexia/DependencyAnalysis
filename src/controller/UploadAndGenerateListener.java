package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;

import exceptions.ServiceException;
import factory.Factory;
import model.SimpleService;
import model.UploadFile;

/**
 * Encapsulates the upload and generate L0 functionality.
 * It is primarily responsible for formatting the data to be displayed on screen
 * along with prompting the user for an input file to be parsed with {@link UploadFile}
 *
 */
public class UploadAndGenerateListener implements ActionListener {
	
	private Object[][] tableData;
	private Object[] columnNames;
	private ArrayList<Service> elementaryServices;
	ArrayList<SimpleService> lstData1;
	List<model.Service> lstData = null;
	
	File selectedFile = null;
	
	public File getSelectedFile(){
		return selectedFile;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(Factory.getMainWindow());
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    Factory.setSelectedFile(selectedFile);
		    try {
				
				lstData = UploadFile.readFromXML(selectedFile);
				Factory.setLstData(lstData);
				Factory.setL0Generated(false);
				Factory.setL1Generated(false);
				Factory.setL2Generated(false);
				Factory.setChkElemServGenerated(false);
				Factory.setSCSGenerated(false);
			}

			catch (Exception e1)// catch any of the general exceptions
			{
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		    
		}
		else{
			System.out.println("File was not read");
		}
		
       
		if(Factory.getL0Generated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff
		elementaryServices = new ArrayList<Service>();
		columnNames = new Object[] { "Name of Service", "Input Data", "Output Data", "Name of Variable",
				"Input Variable", "Output Variable" };

		tableData = new Object[20][columnNames.length];
		int rowIndex = 0;

		int index = 0;
		lstData1 = new ArrayList<SimpleService>();
		HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();
		/*
		 * commented out for experimental testing for(int i=0;
		 * i<UploadFile.getHashMaps().size(); i++) {
		 * 
		 * 
		 * String nameOfService = (String) hashes.get(i).get("service_name");
		 * String inputData = (String) hashes.get(i).get("input_service");
		 * String outputData = (String) hashes.get(i).get("outputService");
		 * String nameOfVariable = (String) hashes.get(i).get("nameOfVariable");
		 * String inputVariable = (String) hashes.get(i).get("inputVariable");
		 * String outputVariable = (String) hashes.get(i).get("outputVariable");
		 * 
		 * SimpleService service1 = new SimpleService(nameOfService, inputData,
		 * outputData, nameOfVariable, inputVariable, outputVariable); //HERE
		 * System.out.println(UploadFile.getHashMaps().get(i).get("service_name"
		 * ));
		 * 
		 * 
		 * 
		 * }
		 */
		int size, count;
		for (SimpleService eachService : hashes.values()) {

			index = 0;
			String string = "";

			tableData[rowIndex][index++] = eachService.getName();
			size = eachService.getInputService().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getInputService()) 
			{
				if(str == null) continue;
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";// gotta clean the variable out between uses or you get
						// the previous data as well
			size = eachService.getOutputService().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getOutputService()) 
			{
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";
			size = eachService.getNameOfVariable().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getNameOfVariable()) 
			{
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";
			size = eachService.getInputVariable().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getInputVariable()) 
			{
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";
			size = eachService.getOutputVariable().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getOutputVariable()) 
			{
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";

			rowIndex++;

		}
		Factory.displayResult(tableData, columnNames);
		Factory.setL0Generated(true);
	}

	// **************************************************************************************************************
	// Create method for removing duplicates?
	/**
	 * Removes any duplicate entries in the given list
	 * @param list the list to be cleaned out
	 */
	public static void removeDuplicateWithOrder(ArrayList<String> list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (java.util.Iterator<String> iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element)) {
				newList.add(element);
			}
		}
		list.clear();
		list.addAll(newList);
		
		
		
		
	}
	
	

}
