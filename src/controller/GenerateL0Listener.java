package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import factory.Factory;
import model.SimpleService;
import model.UploadFile;

public class GenerateL0Listener implements ActionListener {
	
	private Object[][] tableData;
	private Object[] columnNames;
	private ArrayList<Service> elementaryServices;
	ArrayList<SimpleService> lstData1;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
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
		for (SimpleService eachService : hashes.values()) {

			index = 0;
			String string = "";

			tableData[rowIndex][index++] = eachService.getName();
			for (model.IOVariable str : eachService.getInputService()) {
				string += str.name + ",";
			}
			tableData[rowIndex][index++] = string;
			string = "";// gotta clean the variable out between uses or you get
						// the previous data as well
			for (model.IOVariable str : eachService.getOutputService()) {
				string += str.name + ",";
			}
			tableData[rowIndex][index++] = string;
			string = "";
			for (model.IOVariable str : eachService.getNameOfVariable()) {
				string += str.name + ",";
			}
			tableData[rowIndex][index++] = string;
			string = "";
			for (model.IOVariable str : eachService.getInputVariable()) {
				string += str.name + ",";
			}
			tableData[rowIndex][index++] = string;
			string = "";
			for (model.IOVariable str : eachService.getOutputVariable()) {
				string += str.name + ",";
			}
			tableData[rowIndex][index++] = string;
			string = "";

			rowIndex++;

		}
		Factory.displayResult(tableData, columnNames);
	}

	// **************************************************************************************************************
	// Create method for removing duplicates?
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
